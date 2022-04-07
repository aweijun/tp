package seedu.trackermon.model.show;

import java.util.List;
import java.util.function.Predicate;

import seedu.trackermon.commons.util.StringUtil;

/**
 * Tests that a {@code Show}'s {@code Status} matches any of the keywords given.
 */
public class StatusContainsKeywordsPredicate implements Predicate<Show> {
    private final List<String> keywords;

    public StatusContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Show show) {
        // Checks for fragmented words in status
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsFragmentedWordIgnoreCase(show.getStatus().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatusContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((StatusContainsKeywordsPredicate) other).keywords)); // state check
    }

}
