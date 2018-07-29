import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsManager extends Properties {

    private static SettingsManager instance = null;

    private ArrayList<String> extensions;

    private final String destinationPath = "config" + FileManager.separator + "settings.json";

    protected final Logger log = Logger.getLogger(getClass().getName());
    private final String DEFAULT_LOGIN = "";
    private final String DEFAULT_PASSWORD = "";

    private final String DEFAULT_LANGUAGE = "eng";
    private final String DEFAULT_EXTENSION = "srt";

    private LanguageCodes languageCodes;

    private SettingsManager() {
        languageCodes = new LanguageCodes();
        extensions = new ArrayList<>();

        extensions.add("srt");
        extensions.add("sub");
    }

    public void saveSettings(String login, String password, String language, String extension) {
        try {
            File file = new File(destinationPath);

            FileManager.createConfigFile(file);

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(destinationPath));
            jsonWriter.beginObject();

            if(password != null) {
                if (!(login.isEmpty() | password.isEmpty())) {
                    jsonWriter.name("login").value(login);
                    instance.setProperty("login", login);

                    jsonWriter.name("password").value(encryptPassword(password));
                    instance.setProperty("password", encryptPassword(password));
                } else {
                    jsonWriter.name("login").value("");
                    instance.setProperty("login", "");

                    jsonWriter.name("password").value("");
                    instance.setProperty("password", "");
                }
            } else {
                jsonWriter.name("login").value(login);
                jsonWriter.name("password").value(SettingsManager.getInstance().getProperty("password"));
            }

            jsonWriter.name("language").value(languageCodes.getISO639_3(language));
            instance.setProperty("language", languageCodes.getISO639_3(language));
            jsonWriter.name("extension").value(extension);
            instance.setProperty("extension", extension);

            jsonWriter.endObject();
            jsonWriter.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static SettingsManager getInstance() {
        if(instance == null) {
            try {
                instance = new SettingsManager();

                JsonParser jsonParser = new JsonParser();
                JsonElement root = null;

                root = jsonParser.parse(new FileReader(instance.destinationPath));
                JsonObject jsonObject = root.getAsJsonObject();

                String login = jsonObject.get("login").getAsString();
                String password = jsonObject.get("password").getAsString();
                String language = jsonObject.get("language").getAsString();
                String extension = jsonObject.get("extension").getAsString();

                instance.setProperty("login", login);
                instance.setProperty("password", password);

                if (instance.languageCodes.containsKey(language)) {
                    instance.setProperty("language", language);
                } else {
                    instance.setProperty("language", instance.DEFAULT_LANGUAGE);
                }
                if (instance.extensions.contains(extension)) {
                    instance.setProperty("extension", extension);
                } else {
                    instance.setProperty("extension", instance.DEFAULT_EXTENSION);
                }

            } catch (Exception e) {
                instance.log.log(Level.INFO, "SettingsManager: Config corrupted or not found.");
                instance.log.log(Level.INFO, "SettingsManager: Loading default config.");

                instance.setProperty("login", instance.DEFAULT_LOGIN);
                instance.setProperty("password", instance.DEFAULT_PASSWORD);
                instance.setProperty("language", instance.DEFAULT_LANGUAGE);
                instance.setProperty("extension", instance.DEFAULT_EXTENSION);
            }
        }
        return instance;
    }

    public ArrayList<String> getAllExtensions() {
        return extensions;
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(password.getBytes(Charset.forName("UTF8")));
        final byte[] resultByte = messageDigest.digest();

        return new String(Hex.encodeHex(resultByte));
    }

    public LanguageCodes getLanguageCodes() {
        return languageCodes;
    }
}
