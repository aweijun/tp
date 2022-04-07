package seedu.trackermon.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.trackermon.commons.core.GuiSettings;
import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.ShowList;
import seedu.trackermon.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonShowListStorage showListStorage = new JsonShowListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(showListStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void showListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonShowListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonShowListStorageTest} class.
         */
        ShowList original = getTypicalShowList();
        storageManager.saveShowList(original);
        ReadOnlyShowList retrieved = storageManager.readShowList().get();
        assertEquals(original, new ShowList(retrieved));
    }

    @Test
    public void getShowListFilePath() {
        assertNotNull(storageManager.getShowListFilePath());
    }

}
