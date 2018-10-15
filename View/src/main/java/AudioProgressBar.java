import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class AudioProgressBar {

    private Pane progressBar;
    private Line progressLine;
    private MediaPlayer mediaPlayer;
    private double maxLine;

    public AudioProgressBar(Pane progressBar, MediaPlayer mediaPlayer) {
        this.progressBar = progressBar;
        this.mediaPlayer = mediaPlayer;
        maxLine = progressBar.getPrefWidth();
    }

    public void drawMarks() {
        for (int i = 0; i < mediaPlayer.getMedia().getDuration().toMillis() / 1000; i++) {
            Line line = new Line();
            line.getStyleClass().add("time-mark");
            line.setEndX(0);
            line.setStartX(0);
            line.setStartY(0);
            line.setEndY(10);
            line.setLayoutX(i * (1000 * maxLine) / mediaPlayer.getMedia().getDuration().toMillis());

            if (i % 10 == 0) {
                line.setEndY(20);
            }

            progressBar.getChildren().add(line);
        }
    }

    public void updateProgress(Duration newValue) {
        progressLine.setLayoutX(newValue.toMillis() / mediaPlayer.getMedia().getDuration().toMillis() * maxLine);
    }

    public void addProgressLine(Line progressLine) {
        this.progressLine = progressLine;
    }

    public void initializeListeners() {
        progressBar.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                progressLine.getScene().setCursor(Cursor.DEFAULT);
            }
        });

        progressBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    progressLine.setLayoutX(event.getX());
                    mediaPlayer.seek(Duration.millis((event.getX() / maxLine) * mediaPlayer.getMedia().getDuration().toMillis()));
                }
            }
        });
    }

}
