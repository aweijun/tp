package seedu.trackermon.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.trackermon.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.trackermon.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShowList;
import static seedu.trackermon.testutil.TypicalShows.getTypicalShows;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.trackermon.model.Model;
import seedu.trackermon.model.ModelManager;
import seedu.trackermon.model.UserPrefs;
import seedu.trackermon.model.show.NameComparator;
import seedu.trackermon.model.show.RatingComparator;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.StatusComparator;
import seedu.trackermon.model.show.TagComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private final Model model = new ModelManager(getTypicalShowList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalShowList(), new UserPrefs());

    @Test
    public void execute_oneKeyword_nameAcs() {
        Comparator<Show> comparator = new NameComparator();
        SortCommand command = new SortCommand(comparator);
        expectedModel.updateSortedShowList(comparator);
        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
        List<Show> expectedList = getTypicalShows();
        expectedList.sort(comparator);
        assertEquals(expectedList, model.getSortedShowList());
    }

    @Test
    public void execute_oneKeyword_statusDcs() {
        Comparator<Show> comparator = new StatusComparator().reversed();
        SortCommand command = new SortCommand(comparator);
        expectedModel.updateSortedShowList(comparator);
        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
        List<Show> expectedList = getTypicalShows();
        expectedList.sort(comparator);
        assertEquals(expectedList, model.getSortedShowList());
    }

    @Test
    public void execute_twoKeywords_tagRatingDsc() {
        Comparator<Show> comparator = new TagComparator().reversed()
                .thenComparing(new RatingComparator().reversed());
        SortCommand command = new SortCommand(comparator);
        expectedModel.updateSortedShowList(comparator);
        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
        List<Show> expectedList = getTypicalShows();
        expectedList.sort(comparator);
        assertEquals(expectedList, model.getSortedShowList());
    }

    @Test
    public void execute_threeKeywords_nameTagRatingDsc() {
        Comparator<Show> comparator = new NameComparator().reversed().thenComparing
                (new TagComparator().reversed().thenComparing(new RatingComparator().reversed()));
        SortCommand command = new SortCommand(comparator);
        expectedModel.updateSortedShowList(comparator);
        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
        List<Show> expectedList = getTypicalShows();
        expectedList.sort(comparator);
        assertEquals(expectedList, model.getSortedShowList());
    }

    @Test
    public void execute_fourKeywords_nameStatusTagRatingAcs() {
        Comparator<Show> comparator = new NameComparator().thenComparing
                (new StatusComparator().thenComparing(
                        new TagComparator().thenComparing(new RatingComparator().reversed())));
        SortCommand command = new SortCommand(comparator);
        expectedModel.updateSortedShowList(comparator);
        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
        List<Show> expectedList = getTypicalShows();
        expectedList.sort(comparator);
        assertEquals(expectedList, model.getSortedShowList());
    }
}
