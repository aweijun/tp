package seedu.trackermon.model.show.exceptions;

public class DuplicatedShowException extends RuntimeException {

    public DuplicatedShowException() {
        super("Operation would result in duplicate shows");
    }
}
