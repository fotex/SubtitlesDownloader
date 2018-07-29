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

        info.put("IDSubtitleFile", "12345");
        info.put("SubFormat", "srt");

        SubtitlesInfo subtitleInfo = new SubtitlesInfo(info);
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
