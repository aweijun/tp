package seedu.trackermon.model.show;

import java.util.List;
import java.util.function.Predicate;

import seedu.trackermon.commons.util.StringUtil;

/**
 * Tests that a {@code Show}'s {@code Rating} matches any of the keywords given.
 */
public class RatingContainsKeywordsPredicate implements Predicate<Show> {
    private final List<String> keywords;

    public RatingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Show show) {
        // Checks for fragmented words in rating
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsFragmentedWordIgnoreCase(show.getRating().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RatingContainsKeywordsPredicate) other).keywords)); // state check
    }

}
