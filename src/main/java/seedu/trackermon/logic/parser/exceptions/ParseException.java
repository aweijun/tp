package seedu.trackermon.logic.parser.exceptions;

import seedu.trackermon.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {
    /**
     * Constructs a new {@code ParseException} with the specified detail {@code message}.
     * @param message the error message to display.
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ParseException} with the specified detail {@code message} and {@code cause}.
     * @param message the error message to display.
     * @param cause the cause of main exception.
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
