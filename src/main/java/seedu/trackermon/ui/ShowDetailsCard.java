package seedu.trackermon.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.trackermon.MainApp;
import seedu.trackermon.model.show.Rating;
import seedu.trackermon.model.show.Show;


/**
 * An UI component that displays information of a {@code Show}.
 */
public class ShowDetailsCard extends UiPart<Region> {

    private static final String FXML = "ShowDetailsCard.fxml";
    private static final String ICON_STAR = "/images/icon_star_30.png";
    private static final String ICON_STAR_FILLED = "/images/icon_star_filled_30.png";
    private static final double SPACING = 15;
    private static final double IMAGE_SIZE = 30;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4
     * where Trackermon is based on</a>
     */

    private Show show;

    @FXML
    private HBox cardPane;
    @FXML
    private TextArea name;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane ratings;
    @FXML
    private TextArea comment;


    /**
     * Creates a default {@code ShowDetailsCard} that has no show to display.
     */
    public ShowDetailsCard() {
        this(null);
    }

    /**
     * Creates a {@code ShowDetailsCard} with the given {@code Show} to display.
     */
    public ShowDetailsCard(Show show) {
        super(FXML);
        this.show = show;
        updateShowDetails(show);
    }

    /**
     * Updates the show details information to show the new show details.
     *
     * @param show the show whose details should be showed.
     */
    public void updateShowDetails(Show show) {
        boolean isShowExists = (show != null);
        name.setVisible(isShowExists);
        status.setVisible(isShowExists);
        tags.setVisible(isShowExists);
        comment.setVisible(isShowExists);
        ratings.setVisible(isShowExists);

        if (!isShowExists) {
            return;
        }

        assert show != null;

        this.show = show;
        name.setText(show.getName().fullName);

        String statusString = show.getStatus().toString();
        String statusMessage = "Status: " + statusString.substring(0, 1).toUpperCase()
                + statusString.substring(1);
        status.setText(statusMessage);
        tags.getChildren().clear();
        show.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.toString())));
        ratings.getChildren().clear();

        for (int i = 0; i < Rating.MAX_RATING; i++) {
            if (i < show.getRating().rating) {
                ratings.getChildren().add(getImageNode(ICON_STAR_FILLED));
            } else {
                ratings.getChildren().add(getImageNode(ICON_STAR));
            }
        }

        comment.setText(show.getComment().comment);
        updateTextArea(name);
    }

    private ImageView getImageNode(String imagePath) {
        ImageView imageNode = new ImageView(new Image(MainApp.class.getResourceAsStream(imagePath)));
        imageNode.setFitHeight(IMAGE_SIZE);
        imageNode.setFitWidth(IMAGE_SIZE);
        return imageNode;
    }

    /**
     * Updates TextArea to resize with text height length.
     * @param textArea the text area to be resized.
     */
    private void updateTextArea(TextArea textArea) {

        Text helper = new Text(textArea.getText());

        helper.setFont(textArea.getFont());

        double wrappingWidth = textArea.getWidth();

        double fontHeight = helper.getFont().getSize() + SPACING;
        double singleHeight = helper.getBoundsInLocal().getHeight();

        helper.setWrappingWidth(wrappingWidth);
        double rowHeight = helper.getBoundsInParent().getHeight();

        int count = (int) Math.ceil(rowHeight / singleHeight);

        textArea.setPrefHeight(count * fontHeight);
    }
}
