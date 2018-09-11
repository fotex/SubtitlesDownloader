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

    private final String DEFAULT_LANGUAGE = "eng";
    private final String DEFAULT_EXTENSION = "srt";
    private final String DEFAULT_EXDENDEDVERSION = "false";

    private LanguageCodes languageCodes;

    private SettingsManager() {
        languageCodes = new LanguageCodes();
        extensions = new ArrayList<>();

        extensions.add("srt");
        extensions.add("sub");
    }

    public void saveSettings(String language, String extension, String extended) {
        try {
            File file = new File(destinationPath);

            FileManager.createConfigFile(file);

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(destinationPath));
            jsonWriter.beginObject();

            jsonWriter.name("language").value(languageCodes.getISO639_3(language));
            instance.setProperty("language", languageCodes.getISO639_3(language));

            jsonWriter.name("extension").value(extension);
            instance.setProperty("extension", extension);

            jsonWriter.name("extended").value(extended);
            instance.setProperty("extended", extended);

            jsonWriter.endObject();
            jsonWriter.close();
        } catch (IOException e) {
            instance.log.log(Level.INFO, "SettingsManager: Failed to save settings.");
        }
    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            try {
                instance = new SettingsManager();

                JsonParser jsonParser = new JsonParser();
                JsonElement root;

                root = jsonParser.parse(new FileReader(instance.destinationPath));
                JsonObject jsonObject = root.getAsJsonObject();

                String language = jsonObject.get("language").getAsString();
                String extension = jsonObject.get("extension").getAsString();
                String extended = jsonObject.get("extended").getAsString();

                instance.setProperty("extended", extended);

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

                instance.setProperty("language", instance.DEFAULT_LANGUAGE);
                instance.setProperty("extension", instance.DEFAULT_EXTENSION);
                instance.setProperty("extended", instance.DEFAULT_EXDENDEDVERSION);
            }
        }
        return instance;
    }

    public ArrayList<String> getAllExtensions() {
        return extensions;
    }

    public LanguageCodes getLanguageCodes() {
        return languageCodes;
    }
}
