package seedu.trackermon.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.trackermon.commons.core.GuiSettings;
import seedu.trackermon.model.show.Show;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Show> PREDICATE_SHOW_ALL_SHOWS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' show list file path.
     */
    Path getShowListFilePath();

    /**
     * Sets the user prefs' show list file path.
     */
    void setShowListFilePath(Path showListFilePath);

    /**
     * Replaces show list data with the data in {@code showList}.
     */
    void setShowList(ReadOnlyShowList showList);

    /** Returns the ShowList */
    ReadOnlyShowList getShowList();

    /** Returns the ShowList size */
    int getShowListSize();

    /**
     * Returns true if a show with the same name as {@code show} exists in the show list.
     */
    boolean hasShow(Show show);

    /**
     * Deletes the given show.
     * The person must exist in the show list.
     */
    void deleteShow(Show target);

    /**
     * Adds the given show.
     * {@code show} must not already exist in the show list.
     */
    void addShow(Show show);

    /**
     * Replaces the given person {@code target} with {@code editedShow}.
     * {@code target} must exist in the show list.
     * The show name of {@code editedShow} must not be the same as another existing show in the show list.
     */
    void setShow(Show target, Show editedShow);

    /** Returns an unmodifiable view of the filtered show list */
    ObservableList<Show> getFilteredShowList();

    /**
     * Updates the filter of the filtered show list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredShowList(Predicate<Show> predicate);

    /** Returns an unmodifiable view of the sorted show list */
    ObservableList<Show> getSortedShowList();

    /**
     * Updates the sort of the sorted show list by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateSortedShowList(Comparator<Show> comparator);
    /**
     * Saves the ordering of the sorted show list.
     */
    void saveSortedShowList();
}
