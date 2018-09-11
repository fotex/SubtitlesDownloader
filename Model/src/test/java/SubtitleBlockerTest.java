import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class SubtitleBlockerTest {

    @Test
    public void blockSubtitleTest() {
        SubtitlesBlocker subtitlesBlocker = new SubtitlesBlocker();

        HashMap<String, String> info = new HashMap<>();
        TMDBMovieInfo tmdbMovieInfo = new TMDBMovieInfo();
        tmdbMovieInfo.setMovieName("test movie");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("IDSubtitleFile", "12345");
        jsonObject.addProperty("SubFormat", "srt");
        jsonObject.addProperty("LanguageName", "");
        jsonObject.addProperty("SubDownloadLink", "");
        jsonObject.addProperty("SubEncoding", "");
        jsonObject.addProperty("SubFileName", "");

        info.put("IDSubtitleFile", "12345");
        info.put("SubFormat", "srt");

        SubtitlesInfo subtitleInfo = new SubtitlesInfo(jsonObject);
        subtitleInfo.setTmdbMovie(tmdbMovieInfo);
        subtitleInfo.setSeason("01");
        subtitleInfo.setEpisode("02");
        subtitleInfo.setLanguage("English");

        subtitlesBlocker.block(subtitleInfo);

        assertEquals(true, subtitlesBlocker.isSubtitleBlocked("12345"));
    }

    @Test
    public void unblockSubtitleTest() {
        SubtitlesBlocker subtitlesBlocker = new SubtitlesBlocker();

        subtitlesBlocker.unblock("12345");

        assertEquals(false, subtitlesBlocker.isSubtitleBlocked("12345"));
    }

}
