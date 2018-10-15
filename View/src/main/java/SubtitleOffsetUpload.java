import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SubtitleOffsetUpload {

    @FXML
    private HBox progressBarPane;

    @FXML
    private BorderPane rootPane;

    private LoadingAnimation customProgressBar;
    private FileManager fileManager;

    @FXML
    private Text movieTextInfo, subtitleTextInfo;

    @FXML
    private ImageView subtitleImage, subtitleStatusImage, movieImage, movieStatusImage;

    private boolean movieUploaded, subtitleUploaded;
    private File subtitle, movie;

    public static Thread convertThread;

    public static volatile boolean convertThreadStopped;

    @FXML
    public void initialize() {
        fileManager = new FileManager();

        movieUploaded = false;
        subtitleUploaded = false;

        movieTextInfo.setText("Drag & Drop movie here");
        subtitleTextInfo.setText("Drag & Drop subtitle here");

        customProgressBar = new LoadingAnimation(progressBarPane);

        convertThreadStopped = false;
    }

    @FXML
    private void onDragDrop(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void onDropMovie(DragEvent event) {
        List<File> movies = event.getDragboard().getFiles();

        if (fileManager.isMovie(movies.get(0))) {
            movieTextInfo.setText("OK.");
            movieImage.setVisible(false);
            movieStatusImage.setVisible(true);
            movieUploaded = true;

            movie = movies.get(0);
            uploadAndShow();
        } else {
            movieTextInfo.setText("Not supported extension, try again.");
        }
    }

    @FXML
    private void onDropSubtitle(DragEvent event) {
        List<File> subtitles = event.getDragboard().getFiles();

        if (fileManager.isSubtitle(subtitles.get(0))) {
            subtitleTextInfo.setText("OK.");
            subtitleImage.setVisible(false);
            subtitleStatusImage.setVisible(true);
            subtitleUploaded = true;

            subtitle = subtitles.get(0);
            uploadAndShow();
        } else {
            subtitleTextInfo.setText("Not supported extension, try again.");
        }
    }

    private void uploadAndShow() {

        if (subtitleUploaded && movieUploaded) {

            MovieConverter movieConverter = new MovieConverter();
            SrtManager srtManager = new SrtManager(subtitle.getPath());

            convertThread = new Thread(() -> {

                Platform.runLater(() -> customProgressBar.start());

                try {

                    int firstsubid = Integer.parseInt(SettingsManager.getInstance().getProperty("offset_firstsubid"));
                    int subtitlesnumber = Integer.parseInt(SettingsManager.getInstance().getProperty("offset_subtitlesnumber"));

                    srtManager.loadSubtitleArray(firstsubid, subtitlesnumber);

                    float firstSubtitleTime = srtManager.getSubtitleStartTime(0) / 1000f;
                    float lastSubtitleTime = srtManager.getSubtitleEndTime(subtitlesnumber - 1) / 1000f;

                    float duration = lastSubtitleTime - firstSubtitleTime;

                    movieConverter.convertToWav(movie, firstSubtitleTime, duration);
                } catch (IOException | EncoderException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {

                    customProgressBar.stop();

                    if (!convertThreadStopped) {
                        try {
                            SubtitleOffsetController subtitleOffsetController = new SubtitleOffsetController(srtManager);

                            FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("subtitleoffset.fxml"));

                            splashLoader.setController(subtitleOffsetController);
                            AnchorPane parent = splashLoader.load();
                            rootPane.setCenter(parent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            });

            convertThread.start();
        }
    }
}
