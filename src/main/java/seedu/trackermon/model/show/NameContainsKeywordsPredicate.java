package seedu.trackermon.model.show;

import java.util.List;
import java.util.function.Predicate;

import seedu.trackermon.commons.util.StringUtil;

/**
 * Tests that a {@code Show}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Show> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Show show) {
        // Checks for fragmented words in name
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsFragmentedWordIgnoreCase(show.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
