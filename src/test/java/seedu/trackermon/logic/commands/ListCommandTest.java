package seedu.trackermon.logic.commands;

import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.logic.commands.CommandTestUtil.showShowAtIndex;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalShowList(), new UserPrefs());
        expectedModel = new ModelManager(model.getShowList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
