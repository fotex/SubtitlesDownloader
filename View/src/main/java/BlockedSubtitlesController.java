import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class BlockedSubtitlesController {

    @FXML
    private AnchorPane main_pane, emptyInfo_pane;

    private SubtitlesBlocker subtitlesBlocker;

    private ObservableList<SubtitlesInfo> subtitlesList;
    private ListView<SubtitlesInfo> subtitlesListView;

    public void initialize() {
        subtitlesBlocker = new SubtitlesBlocker();

        subtitlesList = FXCollections.observableArrayList();
        subtitlesListView = new ListView<>(subtitlesList);

        subtitlesListView.setPrefWidth(main_pane.getPrefWidth());
        subtitlesListView.setPrefHeight(main_pane.getPrefHeight());
        subtitlesListView.getStyleClass().setAll("subtitlelist");

        ArrayList<SubtitlesInfo> blockedSubtitles = subtitlesBlocker.getBlockedSubtitles();

        subtitlesList.addListener((ListChangeListener<SubtitlesInfo>) c -> {
            while (c.next()) {
                if(c.wasRemoved()) {

                    subtitlesBlocker.unblock(c.getRemoved().get(0).getSubtitlesId());

                    if(subtitlesList.size() == 0) {
                        emptyInfo_pane.setVisible(true);
                    }
                }
            }
        });

        Thread thread = new Thread(() -> {
            subtitlesListView.setCellFactory(param -> new BlockedSubtitleCell());

            Platform.runLater(() -> {
                subtitlesList.setAll(blockedSubtitles);

                if(subtitlesList.size() == 0) {
                    emptyInfo_pane.setVisible(true);
                } else {
                    main_pane.getChildren().add(subtitlesListView);
                }
            });

        });
        thread.start();
    }

}
