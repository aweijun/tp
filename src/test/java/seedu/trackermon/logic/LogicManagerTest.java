package seedu.trackermon.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.trackermon.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.COMMENT_DESC_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.NAME_DESC_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.RATING_DESC_HIGH;
import static seedu.trackermon.logic.commands.CommandTestUtil.STATUS_DESC_COMPLETED;
import static seedu.trackermon.testutil.Assert.assertThrows;
import static seedu.trackermon.testutil.TypicalShows.ALICE_IN_WONDERLAND;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.trackermon.commons.core.GuiSettings;
import seedu.trackermon.logic.commands.AddCommand;
import seedu.trackermon.logic.commands.CommandResult;
import seedu.trackermon.logic.commands.HelpCommand;
import seedu.trackermon.logic.commands.ListCommand;
import seedu.trackermon.logic.commands.exceptions.CommandException;
import seedu.trackermon.logic.parser.exceptions.ParseException;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.UserPrefs;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.storage.JsonShowListStorage;
import seedu.trackermon.storage.JsonUserPrefsStorage;
import seedu.trackermon.storage.StorageManager;
import seedu.trackermon.testutil.ShowBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    private static final String UNKNOWN_COMMAND_HELP = String.format(MESSAGE_UNKNOWN_COMMAND,
            HelpCommand.MESSAGE_USAGE);

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonShowListStorage showListStorage =
                new JsonShowListStorage(temporaryFolder.resolve("Trackermon.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(showListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, UNKNOWN_COMMAND_HELP);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub

        JsonShowListStorage addressBookStorage =
                new JsonShowListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_ALICE_IN_WONDERLAND + STATUS_DESC_COMPLETED
                + COMMENT_DESC_BAD + RATING_DESC_HIGH;
        Show expectedShow = new ShowBuilder(ALICE_IN_WONDERLAND).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addShow(expectedShow);
        String expectedMessage = LogicManager.FILE_OPS_SAVE_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredShowList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredShowList().remove(0));
    }

    @Test
    public void testGetters() {
        // Checking showListFilePath
        assertEquals(model.getShowListFilePath(), logic.getShowListFilePath());

        // Checking showList
        model.addShow(ALICE_IN_WONDERLAND);

        assertEquals(Arrays.asList(ALICE_IN_WONDERLAND), logic.getShowList().getShows());

        // Checking GuiSettings
        assertEquals(new GuiSettings(), logic.getGuiSettings());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getShowList(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonShowListIoExceptionThrowingStub extends JsonShowListStorage {
        private JsonShowListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveShowList(ReadOnlyShowList showList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
