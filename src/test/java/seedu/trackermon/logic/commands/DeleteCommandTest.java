package seedu.trackermon.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.logic.commands.CommandTestUtil.showShowAtIndex;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import org.junit.jupiter.api.Test;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.commons.core.index.Index;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.UserPrefs;
import seedu.trackermon.model.show.Show;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalShowList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Show showToDelete = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SHOW);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SHOW_SUCCESS, showToDelete);

        ModelManager expectedModel = new ModelManager(model.getShowList(), new UserPrefs());
        expectedModel.deleteShow(showToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        Show showToDelete = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SHOW);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SHOW_SUCCESS, showToDelete);

        Model expectedModel = new ModelManager(model.getShowList(), new UserPrefs());
        expectedModel.deleteShow(showToDelete);
        showNoShow(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        Index outOfBoundIndex = INDEX_SECOND_SHOW;
        // ensures that outOfBoundIndex is still in bounds of show list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getShowList().getShows().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_SHOW);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_SHOW);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_SHOW);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoShow(Model model) {
        model.updateFilteredShowList(p -> false);

        assertTrue(model.getFilteredShowList().isEmpty());
    }
}
