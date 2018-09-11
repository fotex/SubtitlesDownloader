import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML
    private ComboBox selectBox_language, selectBox_extension;

    @FXML
    private CheckBox checkBox_extended;

    public void initialize() {

        selectBox_language.getItems().addAll(SettingsManager.getInstance().getLanguageCodes().getAllLanguages());
        selectBox_language.getSelectionModel().select(SettingsManager.getInstance().getLanguageCodes()
                .getLanguageName(SettingsManager.getInstance().getProperty("language")));
        selectBox_language.getStyleClass().add("custom-scroll");

        selectBox_extension.getSelectionModel().select(SettingsManager.getInstance().getProperty("extension"));
        selectBox_extension.getItems().addAll(SettingsManager.getInstance().getAllExtensions());

        checkBox_extended.setSelected(Boolean.valueOf(SettingsManager.getInstance().getProperty("extended")));
    }

    @FXML
    public void save() {
        String language = null;
        String extension = null;
        String extended;

        if (selectBox_language.getSelectionModel().getSelectedItem() != null) {
            language = selectBox_language.getSelectionModel().getSelectedItem().toString();
        }
        if (selectBox_extension.getSelectionModel().getSelectedItem() != null) {
            extension = selectBox_extension.getSelectionModel().getSelectedItem().toString();
        }

        if (checkBox_extended.isSelected()) {
            extended = "true";
        } else {
            extended = "false";
        }

        SettingsManager.getInstance().saveSettings(language, extension, extended);
    }
}
