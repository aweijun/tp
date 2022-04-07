package seedu.trackermon.model.show;

import static java.util.Objects.requireNonNull;
import static seedu.trackermon.commons.util.AppUtil.checkArgument;


public class Rating implements Comparable<Rating> {

    public static final String INVALID_RATING = "Rating must be a whole number from 0 to 5";

    public static final int MAX_RATING = 5;

    public static final String VALIDATION_REGEX_RATING = "[0-5]";

    public final int rating;

    /**
     * Constructs a {@code rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(int rating) {
        requireNonNull(rating);
        checkArgument(isValidScore(rating), INVALID_RATING);
        this.rating = rating;
    }

    /**
     * Constructs a {@code rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidScore(rating), INVALID_RATING);
        this.rating = Integer.parseInt(rating);
    }

    /**
     * Return true if a given rating is a valid rating.
     */
    public static boolean isValidScore(int rating) {
        if (rating <= MAX_RATING && rating >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Return true if a given rating is a valid rating.
     */
    public static boolean isValidScore(String rating) {
        return rating.matches(VALIDATION_REGEX_RATING);
    }

    @Override
    public String toString() {
        return Integer.toString(rating);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Rating
                && rating == ((Rating) other).rating);
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(rating).hashCode();
    }

    @Override
    public int compareTo(Rating other) {
        return Integer.compare(this.rating, other.rating);
    }
}
