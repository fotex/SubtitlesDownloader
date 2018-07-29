import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubtitlesBlocker {
    private final String BLOCKED_SUBTITLES = "config" + FileManager.separator + "blockedsubs.json";

    protected final Logger log = Logger.getLogger(getClass().getName());

    private JsonParser jsonParser;
    private JsonElement root;
    private File file;

    public SubtitlesBlocker() {
        jsonParser = new JsonParser();
        file = new File(BLOCKED_SUBTITLES);

        if(!file.exists()) {
            FileManager.createConfigFile(file);
        }
    }

    public void block(SubtitlesInfo subtitlesInfo) {
        try {
            root = jsonParser.parse(new FileReader(BLOCKED_SUBTITLES));

            JsonArray jsonArray = root.getAsJsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("subid", subtitlesInfo.getSubtitlesId());
            jsonObject.addProperty("name", subtitlesInfo.getTmdbMovie().getMovieName());
            jsonObject.addProperty("season", subtitlesInfo.getSeason());
            jsonObject.addProperty("episode", subtitlesInfo.getEpisode());
            jsonObject.addProperty("language", subtitlesInfo.getLanguage());
            jsonObject.addProperty("extension", subtitlesInfo.getSubtitleFormat());
            jsonArray.add(jsonObject);

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(file));
            jsonWriter.jsonValue(jsonArray.toString());
            jsonWriter.close();
        } catch (Exception e) {
            log.log(Level.INFO, "SubtitlesBlocker: Blocked subtitles list file corrupted or not found.");
            fixFile();
        }
    }

    public void unblock(String id) {
        try {
            root = jsonParser.parse(new FileReader(BLOCKED_SUBTITLES));

            JsonArray jsonArray = root.getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                if(jsonObject.get("subid").getAsString().equals(id)) {
                    jsonArray.remove(i);
                }
            }

            JsonWriter jsonWriter = null;
            jsonWriter = new JsonWriter(new FileWriter(file));
            jsonWriter.jsonValue(jsonArray.toString());
            jsonWriter.close();
        } catch (Exception e) {
            log.log(Level.INFO, "SubtitlesBlocker: Blocked subtitles list file corrupted or not found.");
            fixFile();
        }
    }

    public ArrayList<SubtitlesInfo> getBlockedSubtitles() {
        JsonParser jsonParser = new JsonParser();
        JsonElement root = null;

        ArrayList<SubtitlesInfo> blockedSubtitlesList = new ArrayList<>();

        try {
            root = jsonParser.parse(new FileReader(BLOCKED_SUBTITLES));

            JsonArray jsonArray = root.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {

                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("IDSubtitleFile", jsonObject.get("subid").getAsString());

                SubtitlesInfo subtitlesInfo = new SubtitlesInfo(parameter);

                subtitlesInfo.setBlocked(true);
                subtitlesInfo.setFileName(jsonObject.get("name").getAsString());

                if(jsonObject.get("season").toString().equals("null")) {
                    subtitlesInfo.setSeason(null);
                    subtitlesInfo.setEpisode(null);
                } else {
                    subtitlesInfo.setSeason(jsonObject.get("season").getAsString());
                    subtitlesInfo.setEpisode(jsonObject.get("episode").getAsString());
                }

                subtitlesInfo.setLanguage(jsonObject.get("language").getAsString());
                subtitlesInfo.setSubtitleFormat(jsonObject.get("extension").getAsString());

                blockedSubtitlesList.add(subtitlesInfo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return blockedSubtitlesList;
    }

    public boolean isSubtitleBlocked(String id) {
        JsonParser jsonParser = new JsonParser();
        JsonElement root = null;

        try {
            root = jsonParser.parse(new FileReader(BLOCKED_SUBTITLES));

            JsonArray jsonArray = root.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                if (jsonObject.get("subid").getAsString().equals(id)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void fixFile() {
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter(new FileWriter(file));
            jsonWriter.beginArray();
            jsonWriter.endArray();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
