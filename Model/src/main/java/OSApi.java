import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OSApi {

    private final String API_URL = "https://rest.opensubtitles.org/search/";
    private final String API_USERAGENT = "fotexsubtitles";

    private final String API_SEARCH_EPISODE = "episode-";
    private final String API_SEARCH_SEASON = "season-";
    private final String API_SEARCH_LANGUAGE = "sublanguageid-";

    private SubtitlesBlocker subtitlesBlocker;

    public OSApi() {
        subtitlesBlocker = new SubtitlesBlocker();
    }

    public SubtitlesInfo searchSubtitle(String imdbID, String seasonNumber, String episodeNumber, String language, String extension, boolean searchExtended) throws IOException, OSException {

        URL url = buildSearchUrl(imdbID, seasonNumber, episodeNumber, language);

        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestProperty("User-Agent", API_USERAGENT);
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));

        return getBestSubtitle(root.getAsJsonArray(), extension, searchExtended);
    }

    private SubtitlesInfo getBestSubtitle(JsonArray subtitles, String extension, boolean extended) throws OSException {

        if(subtitles.size() == 0) {
            throw new OSException("Subtitle not found.");
        }

        for(int i = 0; i < subtitles.size(); i++) {

            JsonObject data = subtitles.get(i).getAsJsonObject();

            if(data.get("SubFormat").getAsString().equals(extension) &&
               !subtitlesBlocker.isSubtitleBlocked(data.get("IDSubtitleFile").getAsString())) {
                SubtitlesInfo subtitlesInfo = new SubtitlesInfo(data);

                if (extended) {
                    if (isExtended(subtitlesInfo.getSubtitleFileName())) {
                        subtitlesInfo.setExtended(true);
                        return subtitlesInfo;
                    }
                } else {
                    return subtitlesInfo;
                }
            }
        }

        throw new OSException("Subtitle not found.");
    }

    private URL buildSearchUrl(String imdbID, String seasonNumber, String episodeNumber, String language) {
        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append(API_URL);
        urlBuilder.append("imdbid-");
        urlBuilder.append(imdbID);
        urlBuilder.append("/");

        if (seasonNumber != null && episodeNumber != null) {
            urlBuilder.append(API_SEARCH_EPISODE);
            urlBuilder.append(episodeNumber);
            urlBuilder.append("/");

            urlBuilder.append(API_SEARCH_SEASON);
            urlBuilder.append(seasonNumber);
            urlBuilder.append("/");
        }

        urlBuilder.append(API_SEARCH_LANGUAGE);
        urlBuilder.append(language);
        urlBuilder.append("/");

        try {
            return new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isExtended(String subtitleName) {
        String nameWithSeason = "(Extended|extended|EXTENDED)";
        Pattern r = Pattern.compile(nameWithSeason);
        Matcher m = r.matcher(subtitleName);
        return m.find();
    }

}
