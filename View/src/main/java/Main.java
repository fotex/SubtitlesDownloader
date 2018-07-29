import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("splash.fxml"));

        Parent splash = splashLoader.load();

        primaryStage.setTitle("Subtitles Downloader");
        Scene scene = new Scene(splash);
        scene.getStylesheets().add("css/style.css");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.show();

        //Center stage
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 3);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
