import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SearchSubtitlesTest {

    @Test
    public void searchTVShowTest() {

        OSApi osApi = new OSApi();

        SubtitlesInfo subtitlesInfo = null;

        try {
            subtitlesInfo = osApi.searchSubtitle(
                    "0475784",
                    "01",
                    "06",
                    "eng",
                    "srt",
                    false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSException e) {
            e.printStackTrace();
        }

        assertEquals(true, subtitlesInfo != null);
    }
}
