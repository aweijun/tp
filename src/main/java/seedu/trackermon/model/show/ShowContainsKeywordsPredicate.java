package seedu.trackermon.model.show;

import java.util.List;
import java.util.function.Predicate;

import seedu.trackermon.commons.util.StringUtil;

/**
 * Tests that a {@code Show}'s {@code Name}, {@code Status}, {@code Tag} or {@code Rating} matches any of the
 * keywords given.
 */
public class ShowContainsKeywordsPredicate implements Predicate<Show> {
    private final List<String> keywords;

    public ShowContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Show show) {
        // Checks for fragmented words in name, status, rating and tags
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsFragmentedWordIgnoreCase(show.getName().fullName, keyword)
                || StringUtil.containsFragmentedWordIgnoreCase(show.getStatus().toString(), keyword)
                || StringUtil.containsFragmentedWordIgnoreCase(show.getTags().toString(), keyword)
                || StringUtil.containsFragmentedWordIgnoreCase(show.getRating().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ShowContainsKeywordsPredicate) other).keywords)); // state check
    }

}
