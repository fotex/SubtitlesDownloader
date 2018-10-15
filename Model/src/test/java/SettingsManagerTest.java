import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettingsManagerTest {

    @Test
    public void configTest() {
        assertEquals(false, (SettingsManager.getInstance().getProperty("language") == null));
        assertEquals(false, (SettingsManager.getInstance().getProperty("extension") == null));
    }

    @Test
    public void saveSettingsTest() {
        String language = "English";
        String extension = "srt";
        String extended = "false";
        String firstSubtitleId = "1";
        String numberOfSubtitles = "15";

        SettingsManager.getInstance().saveSettings(language, extension, extended, firstSubtitleId, numberOfSubtitles);

        assertEquals(true, SettingsManager.getInstance().getProperty("language").equals("eng"));
        assertEquals(true, SettingsManager.getInstance().getProperty("extension").equals(extension));
        assertEquals(true, SettingsManager.getInstance().getProperty("offset_firstsubid").equals(firstSubtitleId));
        assertEquals(true, SettingsManager.getInstance().getProperty("offset_subtitlesnumber").equals("15"));
    }
}
