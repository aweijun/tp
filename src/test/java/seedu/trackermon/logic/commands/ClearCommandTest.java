package seedu.trackermon.logic.commands;

import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import org.junit.jupiter.api.Test;

import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.ShowList;
import seedu.trackermon.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyShowList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyShowList_success() {
        Model model = new ModelManager(getTypicalShowList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalShowList(), new UserPrefs());
        expectedModel.setShowList(new ShowList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
