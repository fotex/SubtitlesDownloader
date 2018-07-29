import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CustomProgressBar {

    private Circle circle;
    private HBox hBox;

    public CustomProgressBar(HBox hBox) {

        this.hBox = hBox;

        circle = new Circle();
        circle.setRadius(50.0f);
        circle.setFill(Color.web("#2196F3"));

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000), circle);
        scaleTransition.setByX(1f);
        scaleTransition.setByY(1f);
        scaleTransition.setByX(-1f);
        scaleTransition.setByY(-1f);

        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        scaleTransition.play();
    }

    public void stop() {
        hBox.setVisible(false);
        hBox.getChildren().remove(circle);
        hBox.getParent().setOpacity(1.0);
    }

    public void start() {
        hBox.setVisible(true);
        hBox.getChildren().add(circle);
        hBox.setAlignment(Pos.CENTER);
        hBox.getParent().setOpacity(0.4);
    }

    public void setColor(String hex) {
        circle.setFill(Color.web(hex));
    }




}
