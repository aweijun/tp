package seedu.trackermon.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.trackermon.commons.core.LogsCenter;
import seedu.trackermon.model.show.Show;


/**
 * Panel containing the list of shows.
 */
public class ShowListPanel extends UiPart<Region> {
    private static final String FXML = "ShowListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ShowListPanel.class);

    private final ShowDetailsCard showDetailsCard;

    @FXML
    private ListView<Show> showListView;

    /**
     * Creates a {@code ShowListPanel} with the given {@code ObservableList}.
     */
    public ShowListPanel(ObservableList<Show> showList, ShowDetailsCard showDetailsCard) {
        super(FXML);
        this.showDetailsCard = showDetailsCard;

        showListView.setItems(showList);
        showListView.setCellFactory(listView -> new ShowListViewCell());

        showListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Show>() {
            @Override
            public void changed(ObservableValue<? extends Show> observable, Show oldValue, Show newValue) {
                // Your action here
                showDetailsCard.updateShowDetails(newValue);
            }
        });

        handleUpdatedList();
    }

    /**
     *
     * @param index
     */
    public void updateSelection(int index) {
        showListView.getSelectionModel().select(index);
        showListView.scrollTo(index);
    }

    /**
     * Updates the GUI of the list to select the first item.
     */
    public void handleUpdatedList() {
        if (showListView.getSelectionModel().getSelectedItem() == null) {
            showListView.getSelectionModel().selectFirst();
        }
        showDetailsCard.updateShowDetails(showListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code ShowCard}.
     */
    class ShowListViewCell extends ListCell<Show> {
        @Override
        protected void updateItem(Show show, boolean empty) {
            super.updateItem(show, empty);

            if (empty || show == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ShowCard(show, getIndex() + 1).getRoot());
            }
        }
    }


}
