import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;

final class SubtitleCell extends ListCell<SubtitlesInfo> {

    private final String POSTER_WIDTH = "w200";
    private final String POSTER_HEIGHT = "h300";

    private final String POSTER_URL = "https://image.tmdb.org/t/p/" + POSTER_WIDTH + "_and_" + POSTER_HEIGHT + "_bestv2";

    private final HBox listItem;
    private final Label movieTitle;
    private final Label movieDesc;

    private final ImageView img_movieicon;

    public SubtitleCell() {

        movieTitle = new Label();
        movieDesc = new Label();
        img_movieicon = new ImageView();

        ImageView img_deletebutton = new ImageView("images/64/trash-alt2.png");
        ImageView img_blockbutton = new ImageView("images/64/ban.png");

        img_deletebutton.setFitHeight(img_deletebutton.getImage().getHeight() / 2);
        img_deletebutton.setFitWidth(img_deletebutton.getImage().getWidth() / 2);
        img_deletebutton.setPickOnBounds(true);
        img_deletebutton.getStyleClass().add("moviecell-button");

        img_blockbutton.setFitHeight(img_blockbutton.getImage().getHeight() / 2);
        img_blockbutton.setFitWidth(img_blockbutton.getImage().getWidth() / 2);
        img_blockbutton.setPickOnBounds(true);
        img_blockbutton.getStyleClass().add("moviecell-button");

        VBox movieInfoBox = new VBox(movieTitle, movieDesc);
        HBox buttonsBox = new HBox(img_blockbutton, img_deletebutton);
        VBox movieIconBox = new VBox(img_movieicon);

        listItem = new HBox(movieIconBox, movieInfoBox, buttonsBox);
        listItem.setFillHeight(true);

        listItem.getStyleClass().add("movie-li");
        buttonsBox.getStyleClass().add("moviebuttons");
        movieInfoBox.getStyleClass().add("movieinfo");

        listItem.setHgrow(movieInfoBox, Priority.ALWAYS);

        img_deletebutton.setOnMouseClicked(event -> getListView().getItems().remove(getItem()));
        img_blockbutton.setOnMouseClicked(event -> {
            getItem().setBlocked(true);
            getListView().getItems().remove(getItem());
        });
    }

    @Override
    public void updateItem(SubtitlesInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            movieTitle.setText(item.getTmdbMovie().getMovieName());

            String desc = item.getLanguage();

            if (item.getTmdbMovie().isTVShow()) {
                desc = desc + " - S" + item.getSeason() + "E" + item.getEpisode();
            }

            if (item.isExtended()) {
                desc = desc + " *EXTENDED*";
            }

            desc = desc + " - format: " + item.getSubtitleFormat();

            movieDesc.setText(desc);

            img_movieicon.setImage(new Image(POSTER_URL + item.getTmdbMovie().getPosterImagePath()));
            img_movieicon.setFitWidth(img_movieicon.getImage().getWidth() / 4);
            img_movieicon.setFitHeight(img_movieicon.getImage().getHeight() / 4);

            movieTitle.getStyleClass().add("movietitle");
            movieDesc.getStyleClass().add("moviedesc");
            setGraphic(listItem);
        }
    }
}
