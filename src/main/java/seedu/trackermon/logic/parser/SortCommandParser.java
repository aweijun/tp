package seedu.trackermon.logic.parser;

import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.trackermon.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.logic.commands.SortCommand;
import seedu.trackermon.logic.parser.exceptions.ParseException;
import seedu.trackermon.model.show.NameComparator;
import seedu.trackermon.model.show.RatingComparator;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.StatusComparator;
import seedu.trackermon.model.show.TagComparator;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    protected static final String VALUE_ASC = "ASC";
    protected static final String VALUE_DSC = "DSC";
    private static final String VALUE_ORDER_NAME = "NAME";
    private static final String VALUE_ORDER_STATUS = "STATUS";
    private static final String VALUE_ORDER_RATING = "RATING";
    private static final String VALUE_ORDER_TAG = "TAG";
    private static final int NO_VALUE = -1;
    private static final int ADD_VALUE = 100;
    private static int startingValue = 0;
    private static HashMap<Comparator<Show>, Integer> order = new HashMap<>();

    public static final String COMMAND_EXAMPLE = "Example: To sort by rating in ascending order, "
            + "followed by status in descending order, " + SortCommand.COMMAND_WORD + " "
            + PREFIX_STATUS + VALUE_DSC.toLowerCase() + " "
            + PREFIX_RATING + VALUE_ASC.toLowerCase() + " "
            + PREFIX_SORT_ORDER + VALUE_ORDER_RATING.toLowerCase() + VALUE_ORDER_STATUS.toLowerCase();
    public static final String MESSAGE_INVALID_SO = "Sort order should be given a sequence "
            + "parameter made up of prefix names, in the order sort will arrange the shows.\n" + COMMAND_EXAMPLE;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new SortCommand(new NameComparator());
        }

        Comparator<Show> nameComparator = new NameComparator();
        Comparator<Show> statusComparator = new StatusComparator();
        Comparator<Show> ratingComparator = new RatingComparator();
        Comparator<Show> tagComparator = new TagComparator();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                        PREFIX_STATUS, PREFIX_RATING, PREFIX_TAG, PREFIX_SORT_ORDER);

        order = new HashMap<>();
        //Initialise order map
        order.put(nameComparator, NO_VALUE);
        order.put(statusComparator, NO_VALUE);
        order.put(ratingComparator, NO_VALUE);
        order.put(tagComparator, NO_VALUE);

        //put into Order map for every prefix used
        Comparator<Show> finalNameComparator = putIntoMap(nameComparator, PREFIX_NAME, argMultimap);
        Comparator<Show> finalStatusComparator = putIntoMap(statusComparator, PREFIX_STATUS, argMultimap);
        Comparator<Show> finalRatingComparator = putIntoMap(ratingComparator, PREFIX_RATING, argMultimap);
        Comparator<Show> finalTagComparator = putIntoMap(tagComparator, PREFIX_TAG, argMultimap);

        //a new Map for knowing what word to search for in so/
        HashMap<Comparator<Show>, String> comparatorString = new HashMap<>();
        comparatorString.put(finalNameComparator, VALUE_ORDER_NAME);
        comparatorString.put(finalStatusComparator, VALUE_ORDER_STATUS);
        comparatorString.put(finalRatingComparator, VALUE_ORDER_RATING);
        comparatorString.put(finalTagComparator, VALUE_ORDER_TAG);
        //reorder the Order map according to the input value in so/
        reorderMap(argMultimap, comparatorString);

        //sort the hashmap
        Map<Comparator<Show>, Integer> sortedOrder = order.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        //the final comparator built according the Order Map
        Comparator<Show> comparator = buildComparator(sortedOrder);

        //if no prefixes are present, sort by name ascending
        return new SortCommand(Objects.requireNonNullElse(comparator, nameComparator));

    }

    /**
     * Returns a comparator then chains the previous comparator to a new one if the previous is not null.
     * @param comparator previous comparator to built upon.
     * @param entry is the new comparator to chain.
     * @return a comparator build upon the previous comparator.
     */
    private static Comparator<Show> chainComparator(Comparator<Show> comparator,
                                                    Map.Entry<Comparator<Show>, Integer> entry) {
        if (comparator == null) {
            return entry.getKey();
        } else {
            return comparator.thenComparing(entry.getKey());
        }
    }

    /**
     * Returns a new comparator based on the values in sorted Map.
     * @param sortedOrder the sorted map for the new comparator to build upon.
     * @return a new comparator based on the values in sorted Map.
     */
    private static Comparator<Show> buildComparator(Map<Comparator<Show>, Integer> sortedOrder) {
        Comparator<Show> overallComparator = null;
        for (Map.Entry<Comparator<Show>, Integer> entry: sortedOrder.entrySet()) {
            if (entry.getValue() != NO_VALUE) {
                overallComparator = chainComparator(overallComparator, entry);
            }
        }
        return overallComparator;
    }

    /**
     * Checks if the prefix is used and if it is, update Order map.
     * Checks if descending value is used correctly, update the Order map with descending comparator and
     * returns the comparator.
     * @param comparator key of the Order Map.
     * @param prefix the Prefix to search.
     * {@code ArgumentMultimap}.
     * @return a comparator depending on whether it was reversed.
     */
    private static Comparator<Show> putIntoMap(Comparator<Show> comparator,
                                               Prefix prefix, ArgumentMultimap argMultimap) throws ParseException {
        Comparator<Show> newComparator = comparator;
        if (arePrefixesPresent(argMultimap, prefix)) {
            String value = argMultimap.getValue(prefix).orElse(VALUE_ASC);
            value = ParserUtil.checkOrder(value.toUpperCase().trim());
            if (value.equals(VALUE_DSC)) {
                newComparator = comparator.reversed();
            }
            order.put(newComparator, startingValue);
            startingValue++;
        }

        return newComparator;
    }

    /**
     * Updates value of Order Map if the value is not -1.
     * @param comparator key of the Order Map.
     * @param index new index to update.
     */
    private static void updateMapValues(Comparator<Show> comparator, int index) {
        if (order.get(comparator) != NO_VALUE) {
            order.replace(comparator, index);
        }
    }

    /**
     * Add ADD_VALUE to value.
     * @param value integer to change.
     * @return the new value.
     */
    private static int addValue(int value) {
        return value + ADD_VALUE;
    }

    /**
     * Changes the values of Order Map based on input value for prefix so/.
     * {@code ArgumentMultimap}.
     * @param comparatorString Map that contains the comparator and the string to search.
     */
    private static void reorderMap(ArgumentMultimap argMultimap, HashMap<Comparator<Show>,
            String> comparatorString) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_SORT_ORDER)) {
            String valueOrder = argMultimap.getValue(PREFIX_SORT_ORDER).get();
            valueOrder = valueOrder.toUpperCase().trim();
            for (Map.Entry<Comparator<Show>, String> entry : comparatorString.entrySet()) {
                int index = valueOrder.indexOf(entry.getValue());
                if (index != NO_VALUE) {
                    int newValue = addValue(index);
                    updateMapValues(entry.getKey(), newValue);
                }
            }

            for (Map.Entry<Comparator<Show>, Integer> entry : order.entrySet()) {
                int value = entry.getValue();
                if ((value < ADD_VALUE) && value != NO_VALUE) {
                    throw new ParseException(String.format(Messages.MESSAGE_INVALID_INPUT, MESSAGE_INVALID_SO));
                }
            }
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
