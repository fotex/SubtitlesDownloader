public class ControllerConnector {

    private static MainController controller = null;

    private ControllerConnector() { }

    public static void setConnector(MainController mainController) {
        controller = mainController;
    }

    public static MainController getController() {
        return controller;
    }

}
