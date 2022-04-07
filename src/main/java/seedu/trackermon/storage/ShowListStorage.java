package seedu.trackermon.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.trackermon.commons.exceptions.DataConversionException;
import seedu.trackermon.model.ReadOnlyShowList;

/**
 * Represents a storage for {@link seedu.trackermon.model.ShowList}.
 */
public interface ShowListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getShowListFilePath();

    /**
     * Returns Trackermon data as a {@link ReadOnlyShowList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyShowList> readShowList() throws DataConversionException, IOException;

    /**
     * @see #getShowListFilePath()
     */
    Optional<ReadOnlyShowList> readShowList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyShowList} to the storage.
     * @param showList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveShowList(ReadOnlyShowList showList) throws IOException;

    /**
     * @see #saveShowList(ReadOnlyShowList)
     */
    void saveShowList(ReadOnlyShowList showList, Path filePath) throws IOException;

}
