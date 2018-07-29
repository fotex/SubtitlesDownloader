import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;

public class TMDBClient {

    private TMDBApi tmdbApi;

    public TMDBClient() {
        tmdbApi = new TMDBApi();
    }

    public TMDBMovieInfo search(String name, boolean isTVShow) throws IOException, TMDBException {

        TMDBMovieInfo movie = new TMDBMovieInfo();

        JsonObject jsonObject = tmdbApi.query(name, true, isTVShow);
        JsonArray jsonArray = jsonObject.getAsJsonArray("results");
        JsonObject jsonResult = null;

        if(jsonArray.size() > 0) {
            jsonResult = jsonArray.get(0).getAsJsonObject();

            if(isTVShow) {
                movie.setMovieName(jsonResult.get("original_name").getAsString());
            } else {
                movie.setMovieName(jsonResult.get("title").getAsString());
            }

            movie.setPosterImagePath(jsonResult.get("poster_path").getAsString());

            movie.setImdbId(getIMDBId(jsonResult.get("id").getAsString(), isTVShow));
            movie.setTVShow(isTVShow);
        } else {
            throw new TMDBException("Movie not found!");
        }

        return movie;
    }

    private String getIMDBId(String id, boolean isTVShow) throws IOException {
        JsonObject jsonObject = tmdbApi.query(id, false, isTVShow);
        JsonObject jsonArray = jsonObject.getAsJsonObject("external_ids");

        if(jsonArray.size() > 0) {
            return jsonArray.get("imdb_id").getAsString().replaceAll("tt", "");
        }

        return null;
    }
}
