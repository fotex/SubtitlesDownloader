import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class SubtitleOffsetCells {

    private SrtManager srtManager;
    private Pane progressBar;
    private MediaPlayer mediaPlayer;
    private Text clickedCellText;
    private double moveX;
    private double firstSubtitleTime;
    private int msOffset;
    private int totalOffset;


    private ArrayList<Pane> cells = new ArrayList<>();
    private ArrayList<Double> cellsStartingPosition = new ArrayList<>();

    public SubtitleOffsetCells(SrtManager srtManager, MediaPlayer mediaPlayer, Pane progressBar) {
        this.srtManager = srtManager;
        this.progressBar = progressBar;
        this.mediaPlayer = mediaPlayer;
        this.firstSubtitleTime = srtManager.getSubtitleStartTime(0);

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(progressBar.getPrefWidth());
        rectangle.setHeight(progressBar.getPrefHeight());

        progressBar.setClip(rectangle);
    }

    public void draw() {
        for (int i = 0; i < srtManager.getSubtitleListSize(); i++) {
            Pane cell = new Pane();
            cells.add(cell);

            double startTime = (srtManager.getSubtitleStartTime(i) - firstSubtitleTime) / mediaPlayer.getMedia().getDuration().toMillis() * progressBar.getPrefWidth();
            double endTime = (srtManager.getSubtitleEndTime(i) - firstSubtitleTime) / mediaPlayer.getMedia().getDuration().toMillis() * progressBar.getPrefWidth();

            cell.setPrefHeight(20);
            cell.setPrefWidth(endTime - startTime);
            cell.setLayoutX(startTime);
            cell.setLayoutY(40);
            cell.setStyle("-fx-background-color: #d32f2f");
            cell.setId(Integer.toString(i));

            progressBar.getChildren().add(cell);

            initListener(cell);
        }
    }

    public void showCurrentSubtitleText(Text text, Duration value) {
        boolean found = false;
        for (int i = 0; i < srtManager.getSubtitleListSize(); i++) {
            double startTime = srtManager.getSubtitleStartTime(i) - firstSubtitleTime;
            double endTime = srtManager.getSubtitleEndTime(i) - firstSubtitleTime;

            if (value.toMillis() >= startTime && value.toMillis() <= endTime) {
                text.setText(srtManager.getSubtitleText(i));
                found = true;
            }
        }

        if (!found) {
            text.setText("");
        }
    }

    private void initListener(Pane cell) {
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).setStyle("-fx-background-color: #d32f2f");
                }
                cell.setStyle("-fx-background-color: #03A9F4");
                clickedCellText.setText(srtManager.getSubtitleText(Integer.parseInt(cell.getId())));
            }
        });
        cell.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cell.setCursor(Cursor.HAND);
            }
        });
        cell.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                for (int i = 0; i < cells.size(); i++) {
                    cellsStartingPosition.add(cells.get(i).getLayoutX());
                }

                moveX = mouseEvent.getSceneX();
                cell.setCursor(Cursor.MOVE);
                mediaPlayer.pause();
            }
        });
        cell.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).setLayoutX(cellsStartingPosition.get(i) + (mouseEvent.getSceneX() - moveX));

                    msOffset = (int) (((mouseEvent.getSceneX() - moveX) * mediaPlayer.getMedia().getDuration().toMillis()) / progressBar.getWidth());

                }
            }
        });
        cell.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cellsStartingPosition.clear();

                for (int i = 0; i < cells.size(); i++) {
                    srtManager.shiftSubtitle(i, msOffset);
                }

                mediaPlayer.play();
                totalOffset += msOffset;
                msOffset = 0;
            }
        });
    }

    public void setClickedText(Text clickedCellText) {
        this.clickedCellText = clickedCellText;
    }

    public int getOffset() {
        return totalOffset;
    }

    public void setOffset(int offset) {
        this.totalOffset = offset;
    }

}
