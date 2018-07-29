import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TMDBSearchTest {

    @Test
    public void tvShowSearchTest() {
        TMDBClient tmdbClient = new TMDBClient();

        TMDBMovieInfo tmdbMovieInfo = null;

        try {
            tmdbMovieInfo = tmdbClient.search("Westworld", true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TMDBException e) {
            e.printStackTrace();
        }

        assertEquals(false, (tmdbMovieInfo == null));
        assertEquals("Westworld", tmdbMovieInfo.getMovieName());
        assertEquals("0475784", tmdbMovieInfo.getImdbId());
    }

    @Test
    public void movieSearchTest() {
        TMDBClient tmdbClient = new TMDBClient();

        TMDBMovieInfo tmdbMovieInfo = null;

        try {
            tmdbMovieInfo = tmdbClient.search("Titanic", false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TMDBException e) {
            e.printStackTrace();
        }

        assertEquals(false, (tmdbMovieInfo == null));
        assertEquals("Titanic", tmdbMovieInfo.getMovieName());
        assertEquals("0120338", tmdbMovieInfo.getImdbId());
    }

}
