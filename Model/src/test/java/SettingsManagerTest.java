import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettingsManagerTest {

    @Test
    public void configTest() {
        assertEquals(false, (SettingsManager.getInstance().getProperty("login") == null));
        assertEquals(false, (SettingsManager.getInstance().getProperty("password") == null));
        assertEquals(false, (SettingsManager.getInstance().getProperty("language") == null));
        assertEquals(false, (SettingsManager.getInstance().getProperty("extension") == null));
    }

    @Test
    public void loginTokenTest() {
        SettingsManager.getInstance().setProperty("loginToken", "test");

        assertEquals(true, SettingsManager.getInstance().getProperty("loginToken").equals("test"));
    }

    @Test
    public void saveSettingsTest() {
        String login = "test";
        String password = "password";
        String language = "English";
        String extension = "srt";
        String extended = "false";

        SettingsManager.getInstance().saveSettings(login, password, language, extension, extended);

        assertEquals(true, SettingsManager.getInstance().getProperty("login").equals(login));
        assertEquals(false, SettingsManager.getInstance().getProperty("password").equals(password));
        assertEquals(true, SettingsManager.getInstance().getProperty("language").equals("eng"));
        assertEquals(true, SettingsManager.getInstance().getProperty("extension").equals(extension));
    }
}
