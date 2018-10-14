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
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML
    private Text title, appName;

    @FXML
    private ImageView menu_dashboard, menu_subtitle, menu_settings, menu_blockedsubtitles, menu_info;

    @FXML
    private BorderPane rootPane;

    @FXML
    private HBox minimize;

    @FXML
    private AnchorPane sideBar;

    private Menu menu;
    private Parent root;

    private final String APP_NAME = "Subtitles Downloader";
    private final String APP_VERSION = "1.1.0_alpha";

    private double positionX;
    private double positionY;

    protected final Logger log = Logger.getLogger(getClass().getName());

    public void initialize() {
        appName.setText(APP_NAME + " " + APP_VERSION);

        menu = new Menu(title);
        menu.add("Search subtitles", menu_dashboard);
        menu.add("Blocked subtitles", menu_blockedsubtitles);
        menu.add("Subtitle offset", menu_subtitle);
        menu.add("Settings", menu_settings);
        menu.add("Informations", menu_info);

        try {
            root = FXMLLoader.load(getClass().getResource("subtitlesearch.fxml"));
            rootPane.setLeft(root);
        } catch (IOException e) {
            log.log(Level.INFO, "Cannot load dashboard");
        }
    }

    @FXML
    private void onMenuHandle(MouseEvent event) {
        String menuName = menu.changeFocus((ImageView) event.getTarget());

        try {
            switch (menuName) {
                case "Search subtitles":
                    root = FXMLLoader.load(getClass().getResource("subtitlesearch.fxml"));
                    break;
                case "Blocked subtitles":
                    root = FXMLLoader.load(getClass().getResource("blockedsubtitles.fxml"));
                    break;
                case "Subtitle offset":
                    root = FXMLLoader.load(getClass().getResource("subtitleoffsetupload.fxml"));
                    break;
                case "Settings":
                    root = FXMLLoader.load(getClass().getResource("settings.fxml"));
                    break;
                case "Informations":
                    root = FXMLLoader.load(getClass().getResource("info.fxml"));
                    break;
            }

            stopThreads();

            rootPane.setLeft(root);
        } catch (IOException e) {
            log.log(Level.INFO, "Menu: load error.");
        }
    }

    // Stop other threads or mediaplayers when user click menu button.
    private void stopThreads() {
        if (SubtitleOffsetController.mediaPlayer != null) {
            SubtitleOffsetController.mediaPlayer.stop();
        }
        if (SubtitleOffsetUpload.convertThread != null && SubtitleOffsetUpload.convertThread.isAlive()) {
            SubtitleOffsetUpload.convertThreadStopped = true;
        }
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
