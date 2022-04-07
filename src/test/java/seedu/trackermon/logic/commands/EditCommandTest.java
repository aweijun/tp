package seedu.trackermon.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.trackermon.logic.commands.CommandTestUtil.DESC_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.DESC_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_COMMENT_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_WEATHERING_WITH_YOU;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_SERIES;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.logic.commands.CommandTestUtil.showShowAtIndex;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import org.junit.jupiter.api.Test;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.commons.core.index.Index;
import seedu.trackermon.logic.commands.EditCommand.EditShowDescriptor;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.ShowList;
import seedu.trackermon.model.UserPrefs;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.testutil.EditShowDescriptorBuilder;
import seedu.trackermon.testutil.ShowBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalShowList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Show editedShow = new ShowBuilder().build();
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder(editedShow).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new ShowList(model.getShowList()), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(0), editedShow);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastShow = Index.fromOneBased(model.getFilteredShowList().size());
        Show lastShow = model.getFilteredShowList().get(indexLastShow.getZeroBased());

        ShowBuilder showInList = new ShowBuilder(lastShow);
        Show editedShow = showInList.withName(VALID_NAME_WEATHERING_WITH_YOU).withStatus(VALID_STATUS_WATCHING)
                .withTags(VALID_TAG_SERIES).withComment(VALID_COMMENT_BAD).build();

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_WEATHERING_WITH_YOU)
                .withStatus(VALID_STATUS_WATCHING).withTags(VALID_TAG_SERIES).withComment(VALID_COMMENT_BAD).build();
        EditCommand editCommand = new EditCommand(indexLastShow, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);
        Model expectedModel = new ModelManager(new ShowList(model.getShowList()), new UserPrefs());
        expectedModel.setShow(lastShow, editedShow);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW, new EditShowDescriptor());
        Show editedShow = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new ShowList(model.getShowList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        Show showInFilteredList = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        Show editedShow = new ShowBuilder(showInFilteredList).withName(VALID_NAME_ALICE_IN_WONDERLAND).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW,
                new EditShowDescriptorBuilder().withName(VALID_NAME_ALICE_IN_WONDERLAND).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new ShowList(model.getShowList()), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(0), editedShow);
        model.updateFilteredShowList(Model.PREDICATE_SHOW_ALL_SHOWS);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateShowUnfilteredList_failure() {
        Show firstShow = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder(firstShow).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_SHOW, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_DUPLICATE_SHOW);
    }

    @Test
    public void execute_duplicateShowFilteredList_failure() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        // edit show in filtered list into a duplicate in address book
        Show showInList = model.getShowList().getShows().get(INDEX_SECOND_SHOW.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW,
                new EditShowDescriptorBuilder(showInList).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_DUPLICATE_SHOW);
    }

    @Test
    public void execute_invalidShowIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_GONE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of show list
     */
    @Test
    public void execute_invalidShowIndexFilteredList_failure() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);
        Index outOfBoundIndex = INDEX_SECOND_SHOW;
        // ensures that outOfBoundIndex is still in bounds of show list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getShowList().getShows().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditShowDescriptorBuilder().withName(VALID_NAME_GONE).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_SHOW, DESC_ALICE_IN_WONDERLAND);

        // same values -> returns true
        EditShowDescriptor copyDescriptor = new EditShowDescriptor(DESC_ALICE_IN_WONDERLAND);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_SHOW, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_SHOW, DESC_ALICE_IN_WONDERLAND)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_SHOW, DESC_GONE)));
    }

}
