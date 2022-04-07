package seedu.trackermon.logic.parser;

import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.trackermon.logic.commands.CommandTestUtil.COMMENT_DESC_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.trackermon.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.trackermon.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.trackermon.logic.commands.CommandTestUtil.NAME_DESC_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.NAME_DESC_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.trackermon.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.trackermon.logic.commands.CommandTestUtil.RATING_DESC_HIGH;
import static seedu.trackermon.logic.commands.CommandTestUtil.RATING_DESC_LOW;
import static seedu.trackermon.logic.commands.CommandTestUtil.STATUS_DESC_COMPLETED;
import static seedu.trackermon.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.trackermon.logic.commands.CommandTestUtil.TAG_DESC_HENTAI;
import static seedu.trackermon.logic.commands.CommandTestUtil.TAG_DESC_MOVIE;
import static seedu.trackermon.logic.commands.CommandTestUtil.TAG_DESC_YURI;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_COMMENT_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_RATING_HIGH;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_RATING_LOW;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_COMPLETED;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_HENTAI;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_MOVIE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_YURI;
import static seedu.trackermon.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.trackermon.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.trackermon.testutil.TypicalShows.ALICE_IN_WONDERLAND;
import static seedu.trackermon.testutil.TypicalShows.GONE;

import org.junit.jupiter.api.Test;

import seedu.trackermon.logic.commands.AddCommand;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.tag.Tag;
import seedu.trackermon.testutil.ShowBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Show expectedShow = new ShowBuilder(ALICE_IN_WONDERLAND).withStatus(VALID_STATUS_COMPLETED)
                .withTags(VALID_TAG_MOVIE).withComment(VALID_COMMENT_BAD).withRating(VALID_RATING_HIGH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ALICE_IN_WONDERLAND
                + STATUS_DESC_COMPLETED + COMMENT_DESC_BAD + TAG_DESC_MOVIE + RATING_DESC_HIGH ,
                new AddCommand(expectedShow));
        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_GONE + NAME_DESC_ALICE_IN_WONDERLAND
                + STATUS_DESC_COMPLETED + COMMENT_DESC_BAD + RATING_DESC_HIGH + TAG_DESC_MOVIE,
                new AddCommand(expectedShow));

        // multiple status - last status accepted
        assertParseSuccess(parser, NAME_DESC_ALICE_IN_WONDERLAND + STATUS_DESC_WATCHING + RATING_DESC_HIGH
                + COMMENT_DESC_BAD + TAG_DESC_MOVIE + STATUS_DESC_COMPLETED, new AddCommand(expectedShow));

        // multiple tags - all accepted
        Show expectedShowMultipleTags = new ShowBuilder(GONE).withComment(VALID_COMMENT_BAD)
                .withRating(VALID_RATING_LOW).withTags(VALID_TAG_YURI, VALID_TAG_HENTAI).build();

        assertParseSuccess(parser, NAME_DESC_GONE + STATUS_DESC_WATCHING + COMMENT_DESC_BAD + RATING_DESC_LOW
                + TAG_DESC_YURI + TAG_DESC_HENTAI, new AddCommand(expectedShowMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Show expectedShow = new ShowBuilder(ALICE_IN_WONDERLAND).withTags().withComment().withRating().build();
        assertParseSuccess(parser, NAME_DESC_ALICE_IN_WONDERLAND + STATUS_DESC_COMPLETED,
                new AddCommand(expectedShow));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ALICE_IN_WONDERLAND + STATUS_DESC_COMPLETED,
                expectedMessage);

        // missing status prefix
        assertParseFailure(parser, NAME_DESC_ALICE_IN_WONDERLAND + VALID_STATUS_COMPLETED,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ALICE_IN_WONDERLAND + VALID_STATUS_COMPLETED,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STATUS_DESC_COMPLETED
                + TAG_DESC_MOVIE, String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));

        // invalid status
        assertParseFailure(parser, NAME_DESC_ALICE_IN_WONDERLAND + INVALID_STATUS_DESC
                + TAG_DESC_MOVIE, String.format(MESSAGE_INVALID_INPUT, Status.MESSAGE_CONSTRAINTS));

        // invalid tag
        assertParseFailure(parser, NAME_DESC_ALICE_IN_WONDERLAND + STATUS_DESC_COMPLETED
                + INVALID_TAG_DESC, String.format(MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS));

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_STATUS_DESC,
                String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ALICE_IN_WONDERLAND
                        + STATUS_DESC_COMPLETED + TAG_DESC_MOVIE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }
}
