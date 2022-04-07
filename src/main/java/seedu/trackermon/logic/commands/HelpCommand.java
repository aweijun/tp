package seedu.trackermon.logic.commands;

import seedu.trackermon.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows Trackermon usage instructions.\n"
            + COMMAND_EXAMPLE;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
