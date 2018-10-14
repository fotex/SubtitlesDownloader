import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @FXML
    private TextField firstSubField, numberOfSubField;

    public void initialize() {

        initListeners();

        selectBox_language.getItems().addAll(SettingsManager.getInstance().getLanguageCodes().getAllLanguages());
        selectBox_language.getSelectionModel().select(SettingsManager.getInstance().getLanguageCodes()
                .getLanguageName(SettingsManager.getInstance().getProperty("language")));
        selectBox_language.getStyleClass().add("custom-scroll");

        selectBox_extension.getSelectionModel().select(SettingsManager.getInstance().getProperty("extension"));
        selectBox_extension.getItems().addAll(SettingsManager.getInstance().getAllExtensions());

        checkBox_extended.setSelected(Boolean.valueOf(SettingsManager.getInstance().getProperty("extended")));

        firstSubField.setText(SettingsManager.getInstance().getProperty("offset_firstsubid"));
        numberOfSubField.setText(SettingsManager.getInstance().getProperty("offset_subtitlesnumber"));
    }

    @FXML
    public void save() {
        String language = null;
        String extension = null;
        String extended;
        String firstSub = SettingsManager.getInstance().getProperty("offset_firstsubid");
        String numberOfSubs = SettingsManager.getInstance().getProperty("offset_subtitlesnumber");

        if (selectBox_language.getSelectionModel().getSelectedItem() != null) {
            language = selectBox_language.getSelectionModel().getSelectedItem().toString();
        }
        if (selectBox_extension.getSelectionModel().getSelectedItem() != null) {
            extension = selectBox_extension.getSelectionModel().getSelectedItem().toString();
        }
        if (firstSubField != null) {
            if (Integer.parseInt(firstSubField.getText()) >= 1) {
                firstSub = firstSubField.getText();
            }
        }
        if (numberOfSubField != null) {
            if (Integer.parseInt(numberOfSubField.getText()) <= SettingsManager.getInstance().OFFSET_MAXSUBSNUMBER &&
                    Integer.parseInt(numberOfSubField.getText()) >= SettingsManager.getInstance().OFFSET_MINSUBSNUMBER) {
                numberOfSubs = numberOfSubField.getText();
            }

        }

        if (checkBox_extended.isSelected()) {
            extended = "true";
        } else {
            extended = "false";
        }

        SettingsManager.getInstance().saveSettings(language, extension, extended, firstSub, numberOfSubs);
    }

    private void initListeners() {
        firstSubField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    firstSubField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        numberOfSubField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberOfSubField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
