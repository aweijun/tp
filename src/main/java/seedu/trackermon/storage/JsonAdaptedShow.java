package seedu.trackermon.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.trackermon.commons.exceptions.IllegalValueException;
import seedu.trackermon.model.show.Comment;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.Rating;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Show}.
 */
class JsonAdaptedShow {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final String name;
    private final String status;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String comment;
    private final String rating;
    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedShow(@JsonProperty("name") String name, @JsonProperty("status") String status,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("comment") String comment, @JsonProperty("rating") String rating) {
        this.name = name;
        this.status = status;
        this.rating = rating;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.comment = comment;
    }

    /**
     * Converts a given {@code Show} into this class for Jackson use.
     */
    public JsonAdaptedShow(Show source) {
        name = source.getName().fullName;
        status = source.getStatus().name();
        rating = source.getRating().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        comment = source.getComment().comment;
    }

    /**
     * Converts this Jackson-friendly adapted show object into the model's {@code Show} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted show.
     */
    public Show toModelType() throws IllegalValueException {
        final List<Tag> showTags = new ArrayList<>();

        for (JsonAdaptedTag tag : tagged) {
            showTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = Status.getStatus(status.toUpperCase());

        final Comment modelComment = new Comment(comment);

        if (rating == null) {
            throw new IllegalValueException("Rating Failed");
        }

        if (!Rating.isValidScore(rating)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }

        final Rating modelRating = new Rating(rating);

        final Set<Tag> modelTags = new HashSet<>(showTags);
        return new Show(modelName, modelStatus, modelTags, modelComment, modelRating);
    }

}
