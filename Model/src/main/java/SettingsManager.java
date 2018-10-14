import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;
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
    private final String DEFAULT_OFFSET_SUBSNUMBER = "10";
    private final String DEFAULT_OFFSET_FIRSTSUBID = "1";

    public final int OFFSET_MINSUBSNUMBER = 5;
    public final int OFFSET_MAXSUBSNUMBER = 15;


    private LanguageCodes languageCodes;

    private SettingsManager() {
        languageCodes = new LanguageCodes();
        extensions = new ArrayList<>();

        extensions.add("srt");
        extensions.add("sub");
    }

    public void saveSettings(String language, String extension, String extended, String firstsubid, String subsnumber) {
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

            jsonWriter.name("offset_firstsubid").value(firstsubid);
            instance.setProperty("offset_firstsubid", firstsubid);

            jsonWriter.name("offset_subtitlesnumber").value(subsnumber);
            instance.setProperty("offset_subtitlesnumber", subsnumber);

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
                String firstsubid = jsonObject.get("offset_firstsubid").getAsString();
                String subsnumber = jsonObject.get("offset_subtitlesnumber").getAsString();

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

                if (Integer.parseInt(firstsubid) >= 1) {
                    instance.setProperty("offset_firstsubid", firstsubid);
                } else {
                    instance.setProperty("offset_firstsubid", instance.DEFAULT_OFFSET_FIRSTSUBID);
                }

                if (Integer.parseInt(subsnumber) <= instance.OFFSET_MAXSUBSNUMBER && Integer.parseInt(subsnumber) >= instance.OFFSET_MINSUBSNUMBER) {
                    instance.setProperty("offset_subtitlesnumber", subsnumber);
                } else {
                    instance.setProperty("offset_subtitlesnumber", instance.DEFAULT_OFFSET_SUBSNUMBER);
                }

            } catch (Exception e) {
                instance.log.log(Level.INFO, "SettingsManager: Config corrupted or not found.");
                instance.log.log(Level.INFO, "SettingsManager: Loading default config.");

                instance.setProperty("language", instance.DEFAULT_LANGUAGE);
                instance.setProperty("extension", instance.DEFAULT_EXTENSION);
                instance.setProperty("extended", instance.DEFAULT_EXDENDEDVERSION);
                instance.setProperty("offset_firstsubid", instance.DEFAULT_OFFSET_FIRSTSUBID);
                instance.setProperty("offset_subtitlesnumber", instance.DEFAULT_OFFSET_SUBSNUMBER);
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
