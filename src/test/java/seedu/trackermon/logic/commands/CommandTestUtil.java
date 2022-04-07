package seedu.trackermon.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.trackermon.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.trackermon.commons.core.index.Index;
import seedu.trackermon.logic.commands.exceptions.CommandException;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ShowList;
import seedu.trackermon.model.show.NameContainsKeywordsPredicate;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.testutil.EditShowDescriptorBuilder;



/**
 * Contains helper methods for testing commands.
 */

public class CommandTestUtil {

    public static final String PREAMBLE_WHITESPACE = " ";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_NAME_WEATHERING_WITH_YOU = "Weathering with You";
    public static final String VALID_NAME_ALICE_IN_WONDERLAND = "Alice in WonderLand";
    public static final String VALID_NAME_HIMYM = "HIMYM";
    public static final String VALID_NAME_GONE = "gone";
    public static final String VALID_NAME_HANCOCK = "Hancock";
    public static final String VALID_RATING_LOW = "1";
    public static final String VALID_RATING_HIGH = "5";
    public static final String VALID_STATUS_COMPLETED = "completed";
    public static final String VALID_STATUS_WATCHING = "watching";
    public static final String VALID_TAG_MOVIE = "movie";
    public static final String VALID_TAG_SERIES = "series";
    public static final String VALID_TAG_YURI = "yuri";
    public static final String VALID_TAG_HENTAI = "hentai";
    public static final String VALID_COMMENT_GOOD = "good";
    public static final String VALID_COMMENT_BAD = "bad";
    public static final String COMMENT_DESC_GOOD = " " + PREFIX_COMMENT + VALID_COMMENT_GOOD;
    public static final String COMMENT_DESC_BAD = " " + PREFIX_COMMENT + VALID_COMMENT_BAD;
    public static final String NAME_DESC_ALICE_IN_WONDERLAND = " " + PREFIX_NAME + VALID_NAME_ALICE_IN_WONDERLAND;
    public static final String NAME_DESC_GONE = " " + PREFIX_NAME + VALID_NAME_GONE;
    public static final String RATING_DESC_HIGH = " " + PREFIX_RATING + VALID_RATING_HIGH;
    public static final String RATING_DESC_LOW = " " + PREFIX_RATING + VALID_RATING_LOW;
    public static final String STATUS_DESC_COMPLETED = " " + PREFIX_STATUS + VALID_STATUS_COMPLETED;
    public static final String STATUS_DESC_WATCHING = " " + PREFIX_STATUS + VALID_STATUS_WATCHING;
    public static final String TAG_DESC_MOVIE = " " + PREFIX_TAG + VALID_TAG_MOVIE;
    public static final String TAG_DESC_YURI = " " + PREFIX_TAG + VALID_TAG_YURI;
    public static final String TAG_DESC_HENTAI = " " + PREFIX_TAG + VALID_TAG_HENTAI;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "PicoNoPiko&"; // '&' not allowed in names
    public static final String INVALID_STATUS_DESC = " "
            + PREFIX_STATUS + "Watch"; // Status have to be completed or watching
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_RATING_DESC = " " + PREFIX_TAG + "A"; // 'A' not allowed in rating

    public static final EditCommand.EditShowDescriptor DESC_ALICE_IN_WONDERLAND;
    public static final EditCommand.EditShowDescriptor DESC_GONE;

    static {
        DESC_ALICE_IN_WONDERLAND = new EditShowDescriptorBuilder().withName(VALID_NAME_ALICE_IN_WONDERLAND)
                .withStatus(VALID_STATUS_COMPLETED).withTags(VALID_TAG_MOVIE).withComment(VALID_COMMENT_BAD)
                .withRating(VALID_RATING_HIGH).build();
        DESC_GONE = new EditShowDescriptorBuilder().withName(VALID_NAME_GONE)
                .withStatus(VALID_STATUS_WATCHING).withTags(VALID_TAG_SERIES, VALID_TAG_HENTAI)
                .withComment(VALID_COMMENT_BAD).withRating(VALID_RATING_LOW).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the show list, filtered show list and selected show in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ShowList expectedShowList = new ShowList(actualModel.getShowList());
        List<Show> expectedFilteredList = new ArrayList<>(actualModel.getFilteredShowList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedShowList, actualModel.getShowList());
        assertEquals(expectedFilteredList, actualModel.getFilteredShowList());
    }
    /**
     * Updates {@code model}'s filtered list to display only the show at the given {@code targetIndex} in the
     * {@code model}'s show list.
     */
    public static void showShowAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredShowList().size());

        Show show = model.getFilteredShowList().get(targetIndex.getZeroBased());
        final String[] splitName = show.getName().fullName.split("\\s+");
        model.updateFilteredShowList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredShowList().size());
    }
}
