import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox selectBox_language, selectBox_extension;

    @FXML
    private CheckBox checkBox_extended;

    private boolean passwordChanged = false;

    public void initialize() {

        loginField.setPromptText("login");
        loginField.setText(SettingsManager.getInstance().getProperty("login"));

        passwordField.setPromptText("password");
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordChanged = true;
        });

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

        if (selectBox_language.getSelectionModel().getSelectedItem() != null) {
            language = selectBox_language.getSelectionModel().getSelectedItem().toString();
        }
        if (selectBox_extension.getSelectionModel().getSelectedItem() != null) {
            extension = selectBox_extension.getSelectionModel().getSelectedItem().toString();
        }

        String login = loginField.getText();
        String password;
        String extended;

        if (passwordChanged) {
            password = passwordField.getText();
        } else {
            password = null;
        }

        if (checkBox_extended.isSelected()) {
            extended = "true";
        } else {
            extended = "false";
        }

        SettingsManager.getInstance().saveSettings(login, password, language, extension, extended);

        if(passwordChanged) {
            OSClient osClient = new OSClient();
            osClient.setLoginToken(SettingsManager.getInstance().getProperty("loginToken"));
            osClient.logout();

            ControllerConnector.getController().initLogin();
            ControllerConnector.getController().updateLoginInfo();
        }
    }
}
