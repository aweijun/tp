package seedu.trackermon.model.show;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

/**
 * Compares the two {@code Show}'s {@code Status} and return an integer value based on the ordering.
 */
public class StatusComparator implements Comparator<Show> {

    @Override
    public int compare(Show show, Show other) {
        requireNonNull(show);
        requireNonNull(other);
        return show.compareStatus(other);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof StatusComparator);
    }

}
