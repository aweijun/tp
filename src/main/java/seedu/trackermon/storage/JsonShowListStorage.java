package seedu.trackermon.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.trackermon.commons.core.LogsCenter;
import seedu.trackermon.commons.exceptions.DataConversionException;
import seedu.trackermon.commons.exceptions.IllegalValueException;
import seedu.trackermon.commons.util.FileUtil;
import seedu.trackermon.commons.util.JsonUtil;
import seedu.trackermon.model.ReadOnlyShowList;

/**
 * A class to access Trackermon data stored as a json file on the hard disk.
 */
public class JsonShowListStorage implements ShowListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonShowListStorage.class);

    private Path filePath;

    public JsonShowListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getShowListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyShowList> readShowList() throws DataConversionException {
        return readShowList(filePath);
    }

    /**
     * Similar to {@link #readShowList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyShowList> readShowList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableShowList> jsonShowList = JsonUtil.readJsonFile(
                filePath, JsonSerializableShowList.class);
        if (!jsonShowList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonShowList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveShowList(ReadOnlyShowList showList) throws IOException {
        saveShowList(showList, filePath);
    }

    /**
     * Similar to {@link #saveShowList(ReadOnlyShowList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveShowList(ReadOnlyShowList showList, Path filePath) throws IOException {
        requireNonNull(showList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableShowList(showList), filePath);
    }

}
