import java.io.IOException;
import java.net.*;

public class NetworkStatusChecker {

    public NetworkStatusChecker() {
    }

    public static boolean checkStatus() {
        try(Socket socket = new Socket())
        {
            int port = 80;
            InetSocketAddress socketAddress = new InetSocketAddress("google.com", port);
            socket.connect(socketAddress, 3000);

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
