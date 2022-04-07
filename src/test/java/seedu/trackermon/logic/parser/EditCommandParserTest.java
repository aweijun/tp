package seedu.trackermon.logic.parser;

import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.trackermon.logic.commands.CommandTestUtil.COMMENT_DESC_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.trackermon.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.trackermon.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.trackermon.logic.commands.CommandTestUtil.NAME_DESC_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.NAME_DESC_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.STATUS_DESC_COMPLETED;
import static seedu.trackermon.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.trackermon.logic.commands.CommandTestUtil.TAG_DESC_HENTAI;
import static seedu.trackermon.logic.commands.CommandTestUtil.TAG_DESC_MOVIE;
import static seedu.trackermon.logic.commands.CommandTestUtil.TAG_DESC_YURI;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_COMMENT_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_COMPLETED;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_HENTAI;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_MOVIE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_YURI;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.trackermon.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.trackermon.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.trackermon.testutil.TypicalIndexes.INDEX_THIRD_SHOW;

import org.junit.jupiter.api.Test;

import seedu.trackermon.commons.core.index.Index;
import seedu.trackermon.logic.commands.EditCommand;
import seedu.trackermon.logic.commands.EditCommand.EditShowDescriptor;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.tag.Tag;
import seedu.trackermon.testutil.EditShowDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified with field
        assertParseFailure(parser, NAME_DESC_ALICE_IN_WONDERLAND, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ALICE_IN_WONDERLAND, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ALICE_IN_WONDERLAND, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS)); // invalid name
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC,
                String.format(MESSAGE_INVALID_INPUT, Status.MESSAGE_CONSTRAINTS)); // invalid status
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                String.format(MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS)); // invalid tag

        // invalid name followed by valid status
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + STATUS_DESC_COMPLETED,
                String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));

        // valid name followed by invalid name. The test case for invalid name followed by valid name
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + NAME_DESC_ALICE_IN_WONDERLAND + INVALID_NAME_DESC,
                String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Show} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_MOVIE + TAG_DESC_HENTAI + TAG_EMPTY,
                String.format(MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, "1" + TAG_DESC_MOVIE + TAG_EMPTY + TAG_DESC_HENTAI,
                String.format(MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_MOVIE + TAG_DESC_HENTAI,
                String.format(MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_STATUS_DESC,
                String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SHOW;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ALICE_IN_WONDERLAND
                + STATUS_DESC_COMPLETED + COMMENT_DESC_BAD + TAG_DESC_MOVIE
                + TAG_DESC_HENTAI;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_ALICE_IN_WONDERLAND)
                .withStatus(VALID_STATUS_COMPLETED).withComment(VALID_COMMENT_BAD)
                .withTags(VALID_TAG_MOVIE, VALID_TAG_HENTAI).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SHOW;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_ALICE_IN_WONDERLAND + STATUS_DESC_COMPLETED;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder()
                .withName(VALID_NAME_ALICE_IN_WONDERLAND)
                .withStatus(VALID_STATUS_COMPLETED).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_SHOW;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ALICE_IN_WONDERLAND;
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder()
                .withName(VALID_NAME_ALICE_IN_WONDERLAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC_COMPLETED;
        descriptor = new EditShowDescriptorBuilder().withStatus(VALID_STATUS_COMPLETED).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_HENTAI;
        descriptor = new EditShowDescriptorBuilder().withTags(VALID_TAG_HENTAI).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SHOW;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_ALICE_IN_WONDERLAND + STATUS_DESC_COMPLETED + TAG_DESC_HENTAI
                + NAME_DESC_GONE + STATUS_DESC_WATCHING + TAG_DESC_MOVIE + TAG_DESC_YURI;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_GONE)
                .withStatus(VALID_STATUS_WATCHING).withTags(VALID_TAG_HENTAI, VALID_TAG_MOVIE, VALID_TAG_YURI)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SHOW;
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC
                + NAME_DESC_ALICE_IN_WONDERLAND;
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder()
                .withName(VALID_NAME_ALICE_IN_WONDERLAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC
                + NAME_DESC_ALICE_IN_WONDERLAND + TAG_DESC_MOVIE;
        descriptor = new EditShowDescriptorBuilder()
                .withName(VALID_NAME_ALICE_IN_WONDERLAND).withTags(VALID_TAG_MOVIE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_SHOW;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
