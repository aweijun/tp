package seedu.trackermon.model.show;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

/**
 * Compares the two {@code Show}'s {@code Name} and return an integer value based on the ordering.
 */
public class NameComparator implements Comparator<Show> {

    @Override
    public int compare(Show show, Show other) {
        requireNonNull(show);
        requireNonNull(other);
        return show.compareNames(other);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof NameComparator);
    }

}
