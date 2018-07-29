import java.net.URL;
import java.net.URLConnection;

public class NetworkStatusChecker {

    public NetworkStatusChecker() {
    }

    public static boolean checkStatus() {
        try {
            URL url = new URL("https://google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
