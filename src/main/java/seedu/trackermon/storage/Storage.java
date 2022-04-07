package seedu.trackermon.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.trackermon.commons.exceptions.DataConversionException;
import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.ReadOnlyUserPrefs;
import seedu.trackermon.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ShowListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getShowListFilePath();

    @Override
    Optional<ReadOnlyShowList> readShowList() throws DataConversionException, IOException;

    @Override
    void saveShowList(ReadOnlyShowList showList) throws IOException;

}
