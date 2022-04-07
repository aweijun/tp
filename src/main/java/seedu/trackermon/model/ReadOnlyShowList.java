package seedu.trackermon.model;

import javafx.collections.ObservableList;
import seedu.trackermon.model.show.Show;

public interface ReadOnlyShowList {

    /**
     * Returns an unmodifiable view of the show list.
     * This list will not contain any duplicate show.
     */
    ObservableList<Show> getShows();
}
