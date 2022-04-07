package seedu.trackermon.model;

import java.nio.file.Path;

import seedu.trackermon.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getShowListFilePath();
}
