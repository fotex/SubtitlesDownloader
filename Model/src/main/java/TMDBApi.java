import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class TMDBApi {

    private final String API_KEY = "08309f226d14d6bb137e1c434f32021d";

    private final String API_HOST = "api.themoviedb.org";
    private final String API_VERSION = "3";
    private final String API_URL = "https://" + API_HOST + "/" + API_VERSION + "/";

    private final String SEARCH = "search/";
    private final String TVSHOW = "tv";
    private final String MOVIE = "movie";

    public JsonObject query(String query, boolean isSearchQuery, boolean isTVShow) throws IOException {

        URL url;
        url = buildUrl(query, isTVShow, isSearchQuery);

        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));

        return root.getAsJsonObject();
    }

    private URL buildUrl(String query, boolean isTVShow, boolean isSearchQuery) {
        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append(API_URL);

        if(isSearchQuery) { urlBuilder.append(SEARCH); }

        if (isTVShow) {
            urlBuilder.append(TVSHOW);
        } else {
            urlBuilder.append(MOVIE);
        }

        if(isSearchQuery) {
            urlBuilder.append("?api_key=" + API_KEY);
        } else {
            urlBuilder.append("/").append(query);
            urlBuilder.append("?api_key=" + API_KEY + "&append_to_response=external_ids");
        }

        try {
            if(isSearchQuery) {
                urlBuilder.append("&query=").append(java.net.URLEncoder.encode(query, "UTF-8").replaceAll("\\+", "%20"));
            }
            return new URL(urlBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
