import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class SubtitleOffsetController {

    @FXML
    private Text subtitleName, status;

    @FXML
    private TextField timeShift;

    @FXML
    private AnchorPane subtitleOffset_pane;

    @FXML
    private HBox progressBarPane;

    private String subtitlePath;
    private CustomProgressBar customProgressBar;
    public void initialize() {
        customProgressBar = new CustomProgressBar(progressBarPane);
    }

    @FXML
    public void openSubtitle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open subtitles file.");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Subtitle extensions", "*.srt");

        fileChooser.getExtensionFilters().add(fileExtensions);

        File subtitleFile = fileChooser.showOpenDialog(status.getScene().getWindow());
        if(subtitleFile != null) {
            subtitlePath = subtitleFile.getAbsolutePath();
            subtitleName.setText(subtitleFile.getName());
            status.setText("Loaded subtitle.");
            subtitleOffset_pane.setVisible(true);
        }
    }

    @FXML
    public void save() {
            Thread thread = new Thread(() -> {

                Platform.runLater(() -> customProgressBar.start());

                String offsetPattern = "-{0,1}([0-9]+?)\\.{1}([0-9]{1,3})";
                boolean matcher = Pattern.matches(offsetPattern, timeShift.getText());

                if(matcher) {
                    String offsetstr = String.format("%.3f", Double.parseDouble(timeShift.getText()));
                    double offset = Double.parseDouble(offsetstr);
                    SubtitlesSyncer subtitlesSyncer = new SubtitlesSyncer(subtitlePath);
                    try {
                        subtitlesSyncer.srtOffset(offset);
                        subtitlesSyncer.save();
                        status.setText("Operation successfully completed.");
                    } catch (IOException e) {
                        status.setText("Error.");
                    }
                } else {
                    status.setText("Not supported offset value.");
                }

                Platform.runLater(() -> customProgressBar.stop());

            });
            thread.start();
    }

    @FXML
    public void cancel() {
        subtitlePath = "";
        status.setText("");
        timeShift.setText("");
        subtitleOffset_pane.setVisible(false);
    }

}
