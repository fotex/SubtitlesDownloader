import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OSClient {

    private final String API_URL = "https://api.opensubtitles.org:443/xml-rpc";
    private final String API_USERAGENT = "fotexsubtitles";

    private String loginToken;
    private final String RESULT_LIMIT = "30";
    private final int MAX_RETRIES = 3;

    private XmlRpcClientConfigImpl xmlrcpConfig;
    private XmlRpcClient xmlrpcClient;

    private SubtitlesBlocker subtitlesBlocker;

    protected final Logger log = Logger.getLogger(getClass().getName());

    public OSClient() {
        xmlrcpConfig = new XmlRpcClientConfigImpl();

        try {
            xmlrcpConfig.setServerURL(new URL(API_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        xmlrpcClient = new XmlRpcClient();
        xmlrpcClient.setTransportFactory(new XmlRpcCommonsTransportFactory(xmlrpcClient));
        xmlrpcClient.setConfig(xmlrcpConfig);

        subtitlesBlocker = new SubtitlesBlocker();
    }

    public boolean login(String username, String password) {
        Object[] parameters = new Object[]{username, password, "eng", API_USERAGENT};
        HashMap<?, ?> result;
        try {
            result = execute("LogIn", parameters);
            loginToken = (String) result.get("token");
            String status = (String) result.get("status");

            return ((!username.isEmpty() && !password.isEmpty()) || !status.equals("200 OK")) && status.equals("200 OK");

        } catch (OSException e) {
            e.printStackTrace();
        }

        return false;
    }

    public SubtitlesInfo searchSubtitles(String imdbID, String seasonNumber, String episodeNumber, String language, String extension, boolean searchExtended) throws OSException, XmlRpcException {
        HashMap<?, ?> result;
        HashMap<String, Object> firstParameters = new HashMap<>();
        HashMap<String, Object> secondParameters = new HashMap<>();
        HashMap<String, String> data;

        if (seasonNumber != null && episodeNumber != null) {
            firstParameters.put("season", seasonNumber);
            firstParameters.put("episode", episodeNumber);
        }

        firstParameters.put("imdbid", imdbID);
        firstParameters.put("sublanguageid", language);
        secondParameters.put("limit", RESULT_LIMIT);

        Object[] parametersArray = new Object[]{loginToken, new Object[]{firstParameters}, secondParameters};

        result = execute("SearchSubtitles", parametersArray);

        Object[] resultData = (Object[]) result.get("data");

        for (Object aResultData : resultData) {
            data = (HashMap<String, String>) aResultData;
            if (data.get("SubFormat").equals(extension) &&
                    !subtitlesBlocker.isSubtitleBlocked(data.get("IDSubtitleFile"))) {
                SubtitlesInfo subtitlesInfo = new SubtitlesInfo((HashMap<String, String>) aResultData);

                if (searchExtended) {
                    if (isExtended(subtitlesInfo.getSubtitleFileName())) {
                        subtitlesInfo.setExtended(true);
                        return subtitlesInfo;
                    }
                } else {
                    return subtitlesInfo;
                }
            }
        }

        //If subtitles extension don't match then return first available extension.
        if (resultData.length > 0) {
            return new SubtitlesInfo((HashMap<String, String>) resultData[0]);
        } else {
            throw new OSException("OpenSubtitles: Subtitle not found.");
        }
    }

    public void logout() {
        Object[] parameters = new Object[]{loginToken};

        try {
            execute("LogOut", parameters);
        } catch (OSException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSession() {
        HashMap<?, ?> result;

        Object[] parameters = new Object[]{loginToken};

        String sessionStatus = "";

        try {
            result = execute("NoOperation", parameters);
            sessionStatus = (String) result.get("status");
        } catch (OSException e) {
            e.printStackTrace();
        }

        return sessionStatus.equals("200 OK");
    }

    public HashMap<?, ?> execute(String methodName, Object[] parameters) throws OSException {
        HashMap<?, ?> result = new HashMap<>();

        boolean success = false;
        int count = 0;
        while (!success && (count++ < MAX_RETRIES)) {
            try {
                Thread.sleep(500);
                result = (HashMap) xmlrpcClient.execute(methodName, parameters);

                success = true;
            } catch (XmlRpcException | InterruptedException e) {
                log.log(Level.INFO, "OS: Too Many Request or Server is busy. Trying again " + count + "/3");
            }
        }

        if (!success) {
            throw new OSException("OS: Too Many Request or Server is busy");
        }

        return result;
    }

    private boolean isExtended(String subtitleName) {
        String nameWithSeason = "(Extended|extended|EXTENDED)";
        Pattern r = Pattern.compile(nameWithSeason);
        Matcher m = r.matcher(subtitleName);
        return m.find();
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLoginToken() {
        return loginToken;
    }

}
