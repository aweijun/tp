package seedu.trackermon.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.trackermon.testutil.Assert.assertThrows;
import static seedu.trackermon.testutil.TypicalShows.HIMYM;
import static seedu.trackermon.testutil.TypicalShows.WEATHERING_WITH_YOU;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.trackermon.commons.exceptions.DataConversionException;
import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.ShowList;

public class JsonShowListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonShowListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readShowList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readShowList(null));
    }

    private java.util.Optional<ReadOnlyShowList> readShowList(String filePath) throws Exception {
        return new JsonShowListStorage(Paths.get(filePath)).readShowList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readShowList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readShowList("notJsonFormatShowList.json"));
    }

    @Test
    public void readShowList_invalidShowShowList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readShowList("invalidShowShowList.json"));
    }

    @Test
    public void readShowList_invalidAndValidPersonShowList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readShowList("invalidAndValidShowShowList.json"));
    }

    @Test
    public void readAndSaveShowList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempShowList.json");
        ShowList original = getTypicalShowList();
        JsonShowListStorage jsonShowListStorage = new JsonShowListStorage(filePath);

        // Save in new file and read back
        jsonShowListStorage.saveShowList(original, filePath);
        ReadOnlyShowList readBack = jsonShowListStorage.readShowList(filePath).get();
        assertEquals(original, new ShowList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addShow(WEATHERING_WITH_YOU);
        original.removeShow(HIMYM);
        jsonShowListStorage.saveShowList(original, filePath);
        readBack = jsonShowListStorage.readShowList(filePath).get();
        assertEquals(original, new ShowList(readBack));

        // Save and read without specifying file path
        original.addShow(HIMYM);
        jsonShowListStorage.saveShowList(original); // file path not specified
        readBack = jsonShowListStorage.readShowList().get(); // file path not specified
        assertEquals(original, new ShowList(readBack));

    }

    @Test
    public void saveShowList_nullShowList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveShowList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code showList} at the specified {@code filePath}.
     */
    private void saveShowList(ReadOnlyShowList showList, String filePath) {
        try {
            new JsonShowListStorage(Paths.get(filePath))
                    .saveShowList(showList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveShowList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveShowList(new ShowList(), null));
    }
}
