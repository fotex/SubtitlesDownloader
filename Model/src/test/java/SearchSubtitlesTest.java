import org.apache.xmlrpc.XmlRpcException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SearchSubtitlesTest {

    @Test
    public void searchMovieTest() {

        assertEquals(false, SettingsManager.getInstance().getProperty("loginToken").isEmpty());

        OSClient osClient = new OSClient();
        osClient.setLoginToken(SettingsManager.getInstance().getProperty("loginToken"));

        SubtitlesInfo subtitlesInfo = null;

        try {
            subtitlesInfo = osClient.searchSubtitles("0475784", "01", "01", "eng", "ext", false);
        } catch (OSException | XmlRpcException e) {
            e.printStackTrace();
        }

        assertEquals(false, (subtitlesInfo == null));
        assertEquals("srt", subtitlesInfo.getSubtitleFormat());
        assertEquals("English", subtitlesInfo.getLanguage());
    }
}
