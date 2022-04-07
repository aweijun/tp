package seedu.trackermon.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Random;

import seedu.trackermon.logic.commands.exceptions.CommandException;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.SuggestPredicate;

/**
 * Returns a random Show from the displayed list.
 */
public class SuggestCommand extends Command {

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "Here is my suggestion!";

    public static final String MESSAGE_FAILURE_NO_SHOWS = "There are no shows currently being listed!";

    public static final String MESSAGE_FAILURE_ONE_SHOW = "There is only one show in the list";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Show> currList = model.getFilteredShowList();
        Integer sizeOfShowList = currList.size();

        if (sizeOfShowList <= 0) {
            throw new CommandException(MESSAGE_FAILURE_NO_SHOWS);
        }
        if (sizeOfShowList == 1) {
            throw new CommandException(MESSAGE_FAILURE_ONE_SHOW);
        }
        Show randomShow = getRandomShow(model);
        SuggestPredicate suggestPredicate = new SuggestPredicate(randomShow);
        model.updateFilteredShowList(suggestPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private static Show getRandomShow(Model model) {
        Random rn = new Random();
        List<Show> currList = model.getFilteredShowList();
        Integer sizeOfShowList = currList.size();
        Integer randomIndex = rn.nextInt(sizeOfShowList);
        return currList.get(randomIndex);
    }
}
