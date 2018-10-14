import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InformationController {

    @FXML
    VBox infoBox;

    public void initialize() {

        Text authorInfo = new Text("SubtitlesDownloader created by Grzegorz Woźnicki (fotex)\nhttp://github.com/fotex\n");
        authorInfo.getStyleClass().add("text-white");

        Text fontAwesomeInfo = new Text("Icons provided by Font Awesome under Creative Common License\nhttps://fontawesome.com/license\n");
        fontAwesomeInfo.getStyleClass().add("text-white");

        Text openSubtitlesInfo = new Text("Subtitles provided by OpenSubtitles\nhttp://opensubtitles.org/\n");
        openSubtitlesInfo.getStyleClass().add("text-white");

        Text tmdbInfo = new Text("TV Series and Movie database (images, titles, etc.) provided by themoviedb\nhttp://themoviedb.org/\n");
        tmdbInfo.getStyleClass().add("text-white");

        infoBox.getChildren().add(authorInfo);
        infoBox.getChildren().add(fontAwesomeInfo);
        infoBox.getChildren().add(openSubtitlesInfo);
        infoBox.getChildren().add(tmdbInfo);

    }

}
