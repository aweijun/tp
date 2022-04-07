package seedu.trackermon.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.trackermon.commons.core.Messages.MESSAGE_SHOWS_LISTED_OVERVIEW;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.testutil.TypicalShows.ALICE_IN_WONDERLAND;
import static seedu.trackermon.testutil.TypicalShows.FRIENDS;
import static seedu.trackermon.testutil.TypicalShows.GONE;
import static seedu.trackermon.testutil.TypicalShows.HIMYM;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.UserPrefs;
import seedu.trackermon.model.show.NameContainsKeywordsPredicate;
import seedu.trackermon.model.show.RatingContainsKeywordsPredicate;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.ShowContainsKeywordsPredicate;
import seedu.trackermon.model.show.StatusContainsKeywordsPredicate;
import seedu.trackermon.model.show.TagsContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalShowList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalShowList(), new UserPrefs());

    @Test
    public void equals() {
        ShowContainsKeywordsPredicate firstPredicate =
                new ShowContainsKeywordsPredicate(Collections.singletonList("first"));
        ShowContainsKeywordsPredicate secondPredicate =
                new ShowContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noShowFound() {
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 0);
        ShowContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredShowList());
    }

    @Test
    public void execute_multipleKeywords_multipleShowsFound() {
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 3);
        ShowContainsKeywordsPredicate predicate = preparePredicate("friends gone HIMYM");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GONE, FRIENDS, HIMYM), model.getFilteredShowList());
    }

    @Test
    public void execute_nameField_preciseSearch() {
        String input = "Alice in Wonderland";
        String[] keywordsArr = getKeywords(input);
        List<Predicate<Show>> predicateArrayList = new ArrayList<>();
        for (int i = 0; i < keywordsArr.length; i++) {
            predicateArrayList.add(new NameContainsKeywordsPredicate(Arrays.asList(keywordsArr[i])));
        }
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 1);
        Predicate<Show> predicate = predicateArrayList.stream().reduce(Predicate::and).orElse(x -> true);
        FindCommand command = new FindCommand(predicateArrayList.stream().reduce(Predicate::and).orElse(x -> true));
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_IN_WONDERLAND), model.getFilteredShowList());
    }

    @Test
    public void execute_statusField_preciseSearch() {
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 1);
        StatusContainsKeywordsPredicate predicate = preparePredicateStatus("completed");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_IN_WONDERLAND), model.getFilteredShowList());
    }

    @Test
    public void execute_tagField_preciseSearch() {
        List<String> input = Arrays.asList("Horror", "friends");
        List<Predicate<Show>> predicateArrayList = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            predicateArrayList.add(new TagsContainsKeywordsPredicate(Arrays.asList(input.get(i))));
        }
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 2);
        Predicate<Show> predicate = predicateArrayList.stream().reduce(Predicate::and).orElse(x -> true);
        FindCommand command = new FindCommand(predicateArrayList.stream().reduce(Predicate::and).orElse(x -> true));
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FRIENDS, HIMYM), model.getFilteredShowList());
    }

    @Test
    public void execute_rateField_preciseSearch() {
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 1);
        RatingContainsKeywordsPredicate predicate = preparePredicateRating("5");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_IN_WONDERLAND), model.getFilteredShowList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ShowContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ShowContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private NameContainsKeywordsPredicate preparePredicateName(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private StatusContainsKeywordsPredicate preparePredicateStatus(String userInput) {
        return new StatusContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private TagsContainsKeywordsPredicate preparePredicateTags(String userInput) {
        return new TagsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private RatingContainsKeywordsPredicate preparePredicateRating(String userInput) {
        return new RatingContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    public String[] getKeywords(String args) {
        return args.trim().split("\\s+");
    }
}
