package seedu.trackermon.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_INPUT = "Invalid input! \n%1$s";
    public static final String MESSAGE_INVALID_INDEX = String.format(MESSAGE_INVALID_INPUT,
            "Index must be a positive whole number within the bounds of the show list.");
    public static final String MESSAGE_SHOWS_LISTED_OVERVIEW = "%1$d shows listed!";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in Trackermon!";
    public static final String MESSAGE_INVALID_ORDER = "Order should only be asc or dsc.";
}
