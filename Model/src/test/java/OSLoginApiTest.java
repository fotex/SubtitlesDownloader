import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OSLoginApiTest {

    @Test
    public void testLoginAuthorization() {
        String login = "";
        String password = "";

        OSClient osClient = new OSClient();
        boolean isLoggedAsUser = osClient.login(login, password);

        SettingsManager.getInstance().setProperty("loginToken", osClient.getLoginToken());

        assertEquals(false, SettingsManager.getInstance().getProperty("loginToken").isEmpty());
        assertEquals(true, osClient.checkSession());
        assertEquals(false, isLoggedAsUser);
    }

}
