import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML
    private Text title, loginInfoLabel, appName;

    @FXML
    private ImageView menu_dashboard, menu_subtitle, menu_settings, menu_blockedsubtitles;

    @FXML
    private BorderPane rootPane;

    @FXML
    private HBox minimize;

    @FXML
    private AnchorPane sideBar;

    private Menu menu;
    private Parent root;

    private OSClient osClient;

    private final String APP_NAME = "Subtitles Downloader";
    private final String APP_VERSION = "1.0.8_alpha";

    private double positionX;
    private double positionY;

    protected final Logger log = Logger.getLogger(getClass().getName());

    public void initialize() {
        appName.setText(APP_NAME + " " + APP_VERSION);

        osClient = new OSClient();
        osClient.setLoginToken(SettingsManager.getInstance().getProperty("loginToken"));

        updateLoginInfo();
        initSessionChecker();

        menu = new Menu(title);
        menu.add("Dashboard", menu_dashboard);
        menu.add("Blocked subtitles", menu_blockedsubtitles);
        menu.add("Subtitle offset", menu_subtitle);
        menu.add("Settings", menu_settings);

        try {
            root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        } catch (IOException e) {
            log.log(Level.INFO, "Cannot load dashboard");
        }

        rootPane.setLeft(root);
    }

    @FXML
    private void onMenuHandle(MouseEvent event) {
        String menuName = menu.changeFocus((ImageView) event.getTarget());

        try {
            switch (menuName) {
                case "Dashboard":
                    root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    break;
                case "Blocked subtitles":
                    root = FXMLLoader.load(getClass().getResource("blockedsubtitles.fxml"));
                    break;
                case "Subtitle offset":
                    root = FXMLLoader.load(getClass().getResource("subtitle.fxml"));
                    break;
                case "Settings":
                    root = FXMLLoader.load(getClass().getResource("settings.fxml"));
                    break;
            }

            rootPane.setLeft(root);
        } catch (IOException e) {
            log.log(Level.INFO, "Cannot load menu");
        }
    }

    public void initLogin() {
        if (!osClient.checkSession()) {
            boolean isLoggedAsUser = osClient.login(SettingsManager.getInstance().getProperty("login"),
                    SettingsManager.getInstance().getProperty("password"));

            SettingsManager.getInstance().setProperty("loginToken", osClient.getLoginToken());
            SettingsManager.getInstance().setProperty("isLoggedAsUser", String.valueOf(isLoggedAsUser));

            updateLoginInfo();
        }
    }

    private void updateLoginInfo() {
        if (osClient.checkSession() &&
                !SettingsManager.getInstance().getProperty("login").isEmpty() &&
                SettingsManager.getInstance().getProperty("isLoggedAsUser").equals("true")) {
            this.loginInfoLabel.setText("Logged as " + SettingsManager.getInstance().getProperty("login"));
        } else {
            this.loginInfoLabel.setText("Not logged in.");
        }
    }

    /**
     * This method is used for not expiring current client session. This method is called every 10 minutes.
     */
    private void initSessionChecker() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                initLogin();
            }
        }, 10 * 60 * 1000, 10 * 60 * 1000);
    }

    @FXML
    private void getPosition(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            positionX = event.getSceneX();
            positionY = event.getSceneY();
        }
    }

    @FXML
    private void move(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            sideBar.getScene().getWindow().setX(event.getScreenX() - positionX);
            sideBar.getScene().getWindow().setY(event.getScreenY() - positionY);
        }
    }

    @FXML
    private void close() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
