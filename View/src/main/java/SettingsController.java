import javafx.fxml.FXML;
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

        if (passwordChanged) {
            password = passwordField.getText();
        } else {
            password = null;
        }

        SettingsManager.getInstance().saveSettings(login, password, language, extension);

        OSClient osClient = new OSClient();
        osClient.setLoginToken(SettingsManager.getInstance().getProperty("loginToken"));
        osClient.logout();

        ControllerConnector.getController().initLogin();
        ControllerConnector.getController().updateLoginInfo();
    }
}
