package seedu.trackermon.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.logic.commands.exceptions.CommandException;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.show.Show;

/**
 * Adds a show to Trackermon.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_FORMAT = "Parameters: " + PREFIX_NAME + "NAME "
            + PREFIX_STATUS + "STATUS "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TAG + "TAG]…\u200B";

    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Boku no Hero "
            + PREFIX_STATUS + "completed "
            + PREFIX_RATING + "2 "
            + PREFIX_COMMENT + "This is about kids fighting "
            + PREFIX_TAG + "Anime "
            + PREFIX_TAG + "Action";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a show to Trackermon.\n" + COMMAND_FORMAT + "\n"
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New show added: %1$s";


    private final Show toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Show}
     * @param show a show to add into the show list.
     */
    public AddCommand(Show show) {
        requireNonNull(show);
        toAdd = show;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasShow(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_SHOW);
        }

        model.addShow(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), model.getShowListSize() - 1);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

