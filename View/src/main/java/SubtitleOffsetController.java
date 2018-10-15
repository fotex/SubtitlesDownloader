import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubtitleOffsetController implements Initializable {

    @FXML
    private Line progressLine;

    @FXML
    private Pane progressBar, timePane;

    @FXML
    private Text currentSubtitleText, clickedCellText;

    @FXML
    private ImageView saveButton, playButton, leftShiftButton, rightShiftButton, statusImage;

    public static MediaPlayer mediaPlayer;

    private AudioProgressBar audioProgressBar;
    private SrtManager srtManager;
    private SubtitleOffsetCells subtitleOffsetCells;

    public SubtitleOffsetController() {

    }

    public SubtitleOffsetController(SrtManager srtManager) {
        this.srtManager = srtManager;
    }

    public void initialize(URL location, ResourceBundle resources) {
        File movieFile = new File("tmp" + FileManager.separator + "moviesound.wav");
        String filePath = movieFile.toURI().toString();
        Media media = new Media(filePath);

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer(media);
        }

        playButtonListener();
        shiftButtonsListener();
        saveButtonListener();
        mediaPlayerInitialize();

        statusImage.setVisible(false);
    }

    private void mediaPlayerInitialize() {
        mediaPlayer.setOnStopped(() -> mediaPlayer = null);

        mediaPlayer.setOnReady(new Runnable() {

            public void run() {

                audioProgressBar = new AudioProgressBar(progressBar, mediaPlayer);
                audioProgressBar.addProgressLine(progressLine);
                audioProgressBar.initializeListeners();
                audioProgressBar.drawMarks();
                subtitleOffsetCells = new SubtitleOffsetCells(srtManager, mediaPlayer, progressBar);
                subtitleOffsetCells.setClickedText(clickedCellText);

                subtitleOffsetCells.draw();

                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

                        audioProgressBar.updateProgress(newValue);
                        subtitleOffsetCells.showCurrentSubtitleText(currentSubtitleText, newValue);

                    }
                });

                mediaPlayer.play();

            }

        });
    }

    private void playButtonListener() {
        ImageView img_play = new ImageView("images/256/play.png");
        ImageView img_pause = new ImageView("images/256/pause.png");

        mediaPlayer.setOnPlaying(() -> playButton.setImage(img_pause.getImage()));
        mediaPlayer.setOnPaused(() -> playButton.setImage(img_play.getImage()));

        playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setCursor(Cursor.HAND);
            }
        });
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                }
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                }
            }
        });
    }

    private void shiftButtonsListener() {
        leftShiftButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                leftShiftButton.setCursor(Cursor.HAND);
            }
        });
        leftShiftButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("test");
                mediaPlayer.seek(Duration.millis(mediaPlayer.getCurrentTime().toMillis() - 1000));
            }
        });
        rightShiftButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rightShiftButton.setCursor(Cursor.HAND);
            }
        });
        rightShiftButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("test");
                mediaPlayer.seek(Duration.millis(mediaPlayer.getCurrentTime().toMillis() + 1000));
            }
        });
    }

    private void saveButtonListener() {

        ImageView img_ok = new ImageView("images/256/circle_ok.png");
        ImageView img_error = new ImageView("images/256/circle_error.png");

        saveButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveButton.setCursor(Cursor.HAND);
            }
        });
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SrtSubtitlesSyncer srtSubtitlesSyncer = new SrtSubtitlesSyncer(srtManager.getSubtitlePath());

                float offset = subtitleOffsetCells.getOffset() / 1000f;

                System.out.println(offset);

                try {
                    srtSubtitlesSyncer.offset(offset);
                    srtSubtitlesSyncer.save();
                    subtitleOffsetCells.setOffset(0);
                    statusImage.setImage(img_ok.getImage());
                    statusImage.setVisible(true);
                } catch (IOException e) {
                    statusImage.setImage(img_error.getImage());
                    statusImage.setVisible(true);
                    e.printStackTrace();
                }
            }
        });
    }
}
