import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlrpc.XmlRpcException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController {

    @FXML
    private AnchorPane subtitlesView;

    @FXML
    private Pane dragdropPane, subtitlesPane;

    @FXML
    private HBox progressBarPane;

    @FXML
    Text textInfo, limitInfo;

    private CustomProgressBar customProgressBar;
    private SubtitlesManager subtitlesManager;
    private SubtitlesBlocker subtitlesBlocker;

    private ObservableList<SubtitlesInfo> subtitlesList;
    private ListView<SubtitlesInfo> subtitlesListView;
    protected final Logger log = Logger.getLogger(getClass().getName());

    public void initialize() {
        customProgressBar = new CustomProgressBar(progressBarPane);
        subtitlesManager = new SubtitlesManager();

        subtitlesBlocker = new SubtitlesBlocker();

        subtitlesList = FXCollections.observableArrayList();
        subtitlesListView = new ListView<>(subtitlesList);

        subtitlesListView.setPrefWidth(subtitlesPane.getPrefWidth());
        subtitlesListView.setPrefHeight(subtitlesPane.getPrefHeight());
        subtitlesListView.getStyleClass().setAll("subtitlelist");

        subtitlesList.addListener((ListChangeListener<SubtitlesInfo>) c -> {
            while (c.next()) {
                if(c.wasRemoved()) {
                    subtitlesManager.remove(c.getRemoved().get(0));
                    updateLimitInfo();

                    if(subtitlesManager.getSize() == 0) {
                        cancel();
                    }

                    if(c.getRemoved().get(0).isBlocked()) {
                        subtitlesBlocker.block(c.getRemoved().get(0));
                    }
                }
            }
        });

        updateLimitInfo();
    }

    @FXML
    private void cancel() {
        subtitlesView.setVisible(false);
        dragdropPane.setVisible(true);

        subtitlesPane.getChildren().remove(subtitlesListView);
        subtitlesManager.clear();
    }

    @FXML
    private void download(MouseEvent event) {
        subtitlesManager.download();
    }

    @FXML
    private void onDragDrop(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void onDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();

        FileManager fileManager = new FileManager();
        ArrayList<File> moviesPaths = fileManager.getMoviePaths(files);

        findSubtitles(moviesPaths);
    }

    private void findSubtitles(ArrayList<File> moviesPaths) {
        Thread thread = new Thread(() -> {

            Platform.runLater(() -> customProgressBar.start());

            OSClient client = new OSClient();
            TMDBClient tmdbClient = new TMDBClient();

            TitleConverter titleConverter;

            client.setLoginToken(SettingsManager.getInstance().getProperty("loginToken"));

            subtitlesListView.setCellFactory(param -> new SubtitleCell());

            for (int i = 0; i < moviesPaths.size(); i++) {
                SubtitlesInfo subtitlesInfo;
                TMDBMovieInfo tmdbMovie;
                try {
                    String movieName = FilenameUtils.removeExtension(moviesPaths.get(i).getName());

                    titleConverter = new TitleConverter(movieName);
                    titleConverter.convert();

                    tmdbMovie = tmdbClient.search(titleConverter.getConvertedName(), titleConverter.isTVShow());

                    subtitlesInfo = client.searchSubtitles(
                            tmdbMovie.getImdbId(),
                            titleConverter.getSeason(),
                            titleConverter.getEpisode(),
                            SettingsManager.getInstance().getProperty("language"),
                            SettingsManager.getInstance().getProperty("extension"),
                            false);

                    subtitlesInfo.setTmdbMovie(tmdbMovie);
                    subtitlesInfo.setSeason(titleConverter.getSeason());
                    subtitlesInfo.setEpisode(titleConverter.getEpisode());
                    subtitlesInfo.setDestinationPath(moviesPaths.get(i).getParent());
                    subtitlesInfo.setFileName(movieName);
                    subtitlesInfo.setLanguageFormat(
                            SettingsManager.getInstance().getLanguageCodes().convertISO6392_ToISO639_3(
                                    SettingsManager.getInstance().getProperty("language")
                            ));

                    subtitlesManager.add(subtitlesInfo);

                } catch (IOException | XmlRpcException | TMDBException | OSException e) {
                    log.log(Level.INFO, "Subtitle not found.");
                }
            }

            Platform.runLater(() -> {
                subtitlesList.setAll(subtitlesManager.getSubtitleList());
                updateLimitInfo();

                if(subtitlesList.size() == 0) {
                    textInfo.setText("Subtitles not found.");
                } else {
                    dragdropPane.setVisible(false);
                    subtitlesView.setVisible(true);
                    subtitlesPane.getChildren().add(subtitlesListView);
                }

                customProgressBar.stop();
            });

        });

        thread.start();
    }

    private void updateLimitInfo() {
        limitInfo.setText(subtitlesManager.getSize() + "/" + SubtitlesManager.MAX_SUBTITLES);
    }
}
