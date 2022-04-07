package seedu.trackermon.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.trackermon.logic.parser.SortCommandParser.VALUE_ASC;
import static seedu.trackermon.logic.parser.SortCommandParser.VALUE_DSC;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.trackermon.commons.core.Messages;
import seedu.trackermon.commons.core.index.Index;
import seedu.trackermon.commons.util.StringUtil;
import seedu.trackermon.logic.parser.exceptions.ParseException;
import seedu.trackermon.model.show.Comment;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.Rating;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();

        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Messages.MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_INPUT, Name.MESSAGE_CONSTRAINTS));
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim().toUpperCase();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_INPUT, Status.MESSAGE_CONSTRAINTS));
        }
        return Status.getStatus(trimmedStatus);
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        if (!Rating.isValidScore(rating)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_INPUT, Rating.INVALID_RATING));
        }
        return new Rating(rating);
    }


    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_INPUT, Tag.MESSAGE_CONSTRAINTS));
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String comment} into a {@code Comment}
     */
    public static Comment parseComment(String comment) {
        requireNonNull(comment);
        Comment validComment = new Comment(comment);
        return validComment;
    }

    /**
     * Checks input string is valid.
     * @param order input string.
     * @return input string.
     * @throws ParseException if the input is not valid.
     */
    public static String checkOrder(String order) throws ParseException {
        requireNonNull(order);
        if (!order.equals(VALUE_ASC) && !order.equals(VALUE_DSC)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_INPUT, Messages.MESSAGE_INVALID_ORDER));
        }
        return order;
    }
}
