import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
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

        if (isSearchQuery) {
            url = buildSearchUrl(query, isTVShow);
        } else {
            url = buildUrl(query, isTVShow);
        }

        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));

        return root.getAsJsonObject();
    }

    private URL buildSearchUrl(String query, boolean isTVShow) {
        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append(API_URL);
        urlBuilder.append(SEARCH);

        if (isTVShow) {
            urlBuilder.append(TVSHOW);
        } else {
            urlBuilder.append(MOVIE);
        }

        urlBuilder.append("?api_key=" + API_KEY);

        try {
            urlBuilder.append("&query=").append(java.net.URLEncoder.encode(query, "UTF-8").replaceAll("\\+", "%20"));
            return new URL(urlBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private URL buildUrl(String id, boolean isTVShow) {
        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append(API_URL);

        if (isTVShow) {
            urlBuilder.append(TVSHOW);
        } else {
            urlBuilder.append(MOVIE);
        }

        urlBuilder.append("/").append(id);
        urlBuilder.append("?api_key=" + API_KEY + "&append_to_response=external_ids");

        try {
            return new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
