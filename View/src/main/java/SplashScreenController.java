import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenController {

    @FXML
    AnchorPane root;

    @FXML
    Text loadingInfo;

    public void initialize() {
        Thread thread = new Thread(() -> {
            if(!NetworkStatusChecker.checkStatus()) {
                Platform.runLater(() -> loadingInfo.setText("No internet connection..."));
                waitForConnection();
            } else {
                Platform.runLater(() -> loadingInfo.setText("Connecting to OpenSubtitles API..."));
                initLogin();

                Platform.runLater(() -> {
                    loadingInfo.setText("Logged...");
                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml"));
                    Parent main = null;
                    try {
                        main = mainLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ControllerConnector.setConnector(mainLoader.getController());

                    Stage stage = (Stage) loadingInfo.getScene().getWindow();
                    Scene scene = new Scene(main);
                    scene.getStylesheets().add("css/style.css");
                    stage.setScene(scene);

                    //Center stage
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 3);
                });
            }
        });
        thread.start();

    }

    public void initLogin() {
        OSClient osClient = new OSClient();
        boolean isLoggedAsUser = osClient.login(SettingsManager.getInstance().getProperty("login"),
                SettingsManager.getInstance().getProperty("password"));

        SettingsManager.getInstance().setProperty("loginToken", osClient.getLoginToken());
        SettingsManager.getInstance().setProperty("isLoggedAsUser", String.valueOf(isLoggedAsUser));
    }

    public void waitForConnection() {
        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                loadingInfo.setText("Waiting for internet connection...");
                if(NetworkStatusChecker.checkStatus()) {
                    initialize();
                    timer.cancel();
                }

            }
        }, 5000, 5000);
    }

    @FXML
    private void close() {
        Platform.exit();
        System.exit(0);
    }

}
