package seedu.trackermon.model.show.exceptions;

public class RatingNotValidException extends RuntimeException {

    public RatingNotValidException() {
        super("This is not a valid rating");
    }
}
