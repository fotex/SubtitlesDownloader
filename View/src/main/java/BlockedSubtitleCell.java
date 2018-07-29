import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

final class BlockedSubtitleCell extends ListCell<SubtitlesInfo> {
    private final HBox listItem;
    private final Label movieTitle;
    private final Label movieDesc;

    public BlockedSubtitleCell() {
        movieTitle = new Label();
        movieDesc = new Label();

        ImageView img_deletebutton = new ImageView("images/64/trash-alt2.png");

        img_deletebutton.setFitHeight(img_deletebutton.getImage().getHeight()/2);
        img_deletebutton.setFitWidth(img_deletebutton.getImage().getWidth()/2);
        img_deletebutton.setPickOnBounds(true);
        img_deletebutton.getStyleClass().add("moviecell-button");

        VBox movieInfoBox = new VBox(movieTitle, movieDesc);
        HBox buttonsBox = new HBox(img_deletebutton);

        listItem = new HBox(movieInfoBox, buttonsBox);
        listItem.setFillHeight(true);

        listItem.getStyleClass().add("movie-li");
        buttonsBox.getStyleClass().add("moviebuttons");
        movieInfoBox.getStyleClass().add("movieinfo");

        listItem.setHgrow(movieInfoBox, Priority.ALWAYS);

        img_deletebutton.setOnMouseClicked(event -> getListView().getItems().remove(getItem()));
    }

    @Override
    public void updateItem(SubtitlesInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            movieTitle.setText(item.getFileName());

            String desc = item.getLanguage();

            if(item.getSeason() != null) {
                desc = desc + " - S" + item.getSeason() + "E" + item.getEpisode();
            }

            desc = desc + " - format: " + item.getSubtitleFormat();

            movieDesc.setText(desc);

            movieTitle.getStyleClass().add("movietitle");
            movieDesc.getStyleClass().add("moviedesc");
            setGraphic(listItem);
        }
    }
}
