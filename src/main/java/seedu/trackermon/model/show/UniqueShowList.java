package seedu.trackermon.model.show;

import static java.util.Objects.requireNonNull;
import static seedu.trackermon.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.trackermon.model.show.exceptions.DuplicatedShowException;
import seedu.trackermon.model.show.exceptions.ShowNotFoundException;


public class UniqueShowList implements Iterable<Show> {

    private final ObservableList<Show> internalList = FXCollections.observableArrayList();
    private final ObservableList<Show> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent show as the given argument.
     */
    public boolean contains(Show toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameShow);
    }

    /**
     * Adds a show to the list.
     * The show must not already exist in the list.
     */
    public void add(Show toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatedShowException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the show {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The show identity of {@code editedShow} must not be the same as another existing show in the list.
     */
    public void setShow(Show target, Show editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ShowNotFoundException();
        }

        if (!target.isSameShow(editedPerson) && contains(editedPerson)) {
            throw new DuplicatedShowException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent show from the list.
     * The show must exist in the list.
     */
    public void remove(Show toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            //to-do: Add new exception
            throw new ShowNotFoundException();
        }
    }

    public void setShows(UniqueShowList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setShows(List<Show> lists) {
        requireAllNonNull(lists);
        if (!showsAreUnique(lists)) {
            throw new DuplicatedShowException();
        }

        internalList.setAll(lists);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Show> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Show> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueShowList // instanceof handles nulls
                && internalList.equals(((UniqueShowList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean showsAreUnique(List<Show> lists) {
        for (int i = 0; i < lists.size() - 1; i++) {
            for (int j = i + 1; j < lists.size(); j++) {
                if (lists.get(i).isSameShow(lists.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
