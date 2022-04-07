package seedu.trackermon.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.trackermon.commons.core.GuiSettings;
import seedu.trackermon.logic.commands.CommandResult;
import seedu.trackermon.logic.commands.exceptions.CommandException;
import seedu.trackermon.logic.parser.exceptions.ParseException;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.show.Show;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ShowList.
     *
     * @see Model#getShowList()
     */
    ReadOnlyShowList getShowList();

    /** Returns an unmodifiable view of the filtered list of shows */
    ObservableList<Show> getFilteredShowList();

    /** Returns an unmodifiable view of the sorted list of shows */
    ObservableList<Show> getSortedShowList();

    /**
     * Returns the user prefs' show list file path.
     */
    Path getShowListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
