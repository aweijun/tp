package seedu.trackermon.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.UniqueShowList;


public class ShowList implements ReadOnlyShowList {

    private final UniqueShowList shows;

    {
        shows = new UniqueShowList();
    }

    public ShowList() {}

    /**
     * Creates an ShowList using the Shows in the {@code toBeCopied}
     */
    public ShowList(ReadOnlyShowList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the show list with {@code shows}.
     * {@code shows} must not contain duplicate shows.
     */
    public void setShows(List<Show> shows) {
        this.shows.setShows(shows);
    }

    /**
     * Resets the existing data of this {@code ShowList} with {@code newData}.
     */
    public void resetData(ReadOnlyShowList newData) {
        requireNonNull(newData);
        setShows(newData.getShows());
    }

    /**
     * Returns true if a show with the same identity as {@code show} exists in the show list.
     */
    public boolean hasShow(Show show) {
        requireNonNull(show);
        return shows.contains(show);
    }

    /**
     * Adds a show to the show list.
     * The show must not already exist in the show list.
     */
    public void addShow(Show p) {
        shows.add(p);
    }

    /**
     * Replaces the given show {@code target} in the list with {@code editedShow}.
     * {@code target} must exist in the show list.
     * The show identity of {@code editedShow} must not be the same as another existing show in the show list.
     */
    public void setShow(Show target, Show editedShow) {
        requireNonNull(editedShow);
        shows.setShow(target, editedShow);
    }

    /**
     * Removes {@code key} from this {@code ShowList}.
     * {@code key} must exist in the show list.
     */
    public void removeShow(Show key) {
        shows.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return shows.asUnmodifiableObservableList().size() + " shows";
        // TODO: refine later
    }

    @Override
    public ObservableList<Show> getShows() {
        return shows.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowList // instanceof handles nulls
                && shows.equals(((ShowList) other).shows));
    }

    @Override
    public int hashCode() {
        return shows.hashCode();
    }


}
