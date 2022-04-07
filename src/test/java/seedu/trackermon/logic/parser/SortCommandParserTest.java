package seedu.trackermon.logic.parser;

import static seedu.trackermon.logic.commands.SortCommand.COMMAND_WORD;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.trackermon.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.trackermon.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.trackermon.logic.parser.SortCommandParser.VALUE_ASC;

import org.junit.jupiter.api.Test;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.logic.commands.SortCommand;
import seedu.trackermon.model.show.NameComparator;
import seedu.trackermon.model.show.StatusComparator;

public class SortCommandParserTest {

    private static final String SPACE = " ";
    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_wrongArg_throwsParseException() {
        //checking for asc or dsc
        assertParseFailure(parser, COMMAND_WORD + SPACE + PREFIX_NAME,
                String.format(Messages.MESSAGE_INVALID_INPUT, Messages.MESSAGE_INVALID_ORDER));
    }

    @Test
    public void parse_wrongSO_throwsParseException() {
        //checking for number of full name for so
        assertParseFailure(parser, COMMAND_WORD + SPACE
                + PREFIX_NAME + VALUE_ASC + SPACE + PREFIX_SORT_ORDER,
                String.format(Messages.MESSAGE_INVALID_INPUT, SortCommandParser.MESSAGE_INVALID_SO));
    }

    @Test
    public void parse_noArgs_returnsSortCommand() {

        //no args, sort by name in ascending order
        SortCommand expectedSortCommand = new SortCommand(new NameComparator());
        assertParseSuccess(parser, COMMAND_WORD, expectedSortCommand);
    }

    @Test
    public void parse_oneArgs_returnsSortCommand() {

        //one arg, sort by status in ascending order
        SortCommand expectedSortCommand = new SortCommand(new StatusComparator());
        assertParseSuccess(parser, COMMAND_WORD + SPACE + PREFIX_STATUS + VALUE_ASC, expectedSortCommand);

    }

}
