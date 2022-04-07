package seedu.trackermon.logic.parser;

import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.trackermon.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.trackermon.logic.commands.FindCommand;
import seedu.trackermon.logic.parser.exceptions.ParseException;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.NameContainsKeywordsPredicate;
import seedu.trackermon.model.show.Rating;
import seedu.trackermon.model.show.RatingContainsKeywordsPredicate;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.ShowContainsKeywordsPredicate;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.show.StatusContainsKeywordsPredicate;
import seedu.trackermon.model.show.TagsContainsKeywordsPredicate;
import seedu.trackermon.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_STATUS, PREFIX_TAG, PREFIX_RATING);
        boolean hasPrefix = false;
        boolean hasNamePrefix = argumentMultimap.arePrefixesPresent(PREFIX_NAME);
        boolean hasStatusPrefix = argumentMultimap.arePrefixesPresent(PREFIX_STATUS);
        boolean hasTagPrefix = argumentMultimap.arePrefixesPresent(PREFIX_TAG);
        boolean hasRatingPrefix = argumentMultimap.arePrefixesPresent(PREFIX_RATING);
        String[] keywordsArr;

        if (!argumentMultimap.getPreamble().isBlank()
                && (hasNamePrefix || hasStatusPrefix || hasRatingPrefix || hasTagPrefix)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Predicate<Show>> predicateArrayList = new ArrayList<>();
        if (hasNamePrefix) {
            hasPrefix = true;
            String input = argumentMultimap.getValue(PREFIX_NAME).get();
            keywordsArr = getKeywords(input);
            for (int i = 0; i < keywordsArr.length; i++) {
                if (!Name.isValidName(keywordsArr[i])) {
                    throw new ParseException(String.format(MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));
                }
                predicateArrayList.add(new NameContainsKeywordsPredicate(Arrays.asList(keywordsArr[i])));
            }
        }

        if (hasStatusPrefix) {
            hasPrefix = true;
            String input = argumentMultimap.getValue(PREFIX_STATUS).get();
            keywordsArr = getKeywords(input);
            for (int i = 0; i < keywordsArr.length; i++) {
                if (!Status.isValidStatus(keywordsArr[i])) {
                    throw new ParseException(String.format(MESSAGE_INVALID_INPUT, Status.MESSAGE_CONSTRAINTS));
                }
            }
            predicateArrayList.add(new StatusContainsKeywordsPredicate(Arrays.asList(keywordsArr)));
        }

        if (hasTagPrefix) {
            hasPrefix = true;
            List<String> input = argumentMultimap.getAllValues(PREFIX_TAG);
            for (int i = 0; i < input.size(); i++) {
                if (!Tag.isValidTagName(input.get(i))) {
                    throw new ParseException(String.format(MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS));
                }
                predicateArrayList.add(new TagsContainsKeywordsPredicate(Arrays.asList(input.get(i))));
            }
        }

        if (hasRatingPrefix) {
            hasPrefix = true;
            String input = argumentMultimap.getValue(PREFIX_RATING).get();
            keywordsArr = getRatingKeywords(input);
            for (int i = 0; i < keywordsArr.length; i++) {
                keywordsArr[i] = keywordsArr[i].replaceFirst("^0+(?!$)", "");
                if (!Rating.isValidScore(keywordsArr[i])) {
                    throw new ParseException(String.format(MESSAGE_INVALID_INPUT, Rating.INVALID_RATING));
                }
            }
            predicateArrayList.add(new RatingContainsKeywordsPredicate(Arrays.asList(keywordsArr)));
        }

        if (!hasPrefix) {
            if (args.isBlank()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            keywordsArr = getKeywords(args);

            return new FindCommand(new ShowContainsKeywordsPredicate(Arrays.asList(keywordsArr)));
        } else {
            return new FindCommand(predicateArrayList.stream().reduce(Predicate::and).orElse(x ->true));
        }
    }

    public String[] getKeywords(String args) throws ParseException {
        String trimmedArgs = args.trim();
        return trimmedArgs.split("\\s+");
    }

    public String[] getRatingKeywords(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INPUT, Rating.INVALID_RATING));
        }
        return trimmedArgs.split("\\s+");
    }
}
