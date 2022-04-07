package seedu.trackermon.logic.commands;

import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.UserPrefs;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.testutil.ShowBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalShowList(), new UserPrefs());
    }

    @Test
    public void execute_newShow_success() {
        Show validShow = new ShowBuilder().build();

        Model expectedModel = new ModelManager(model.getShowList(), new UserPrefs());
        expectedModel.addShow(validShow);

        assertCommandSuccess(new AddCommand(validShow), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validShow), expectedModel);
    }

    @Test
    public void execute_duplicateShow_throwsCommandException() {
        Show showInList = model.getShowList().getShows().get(0);
        assertCommandFailure(new AddCommand(showInList), model, Messages.MESSAGE_DUPLICATE_SHOW);
    }
}
