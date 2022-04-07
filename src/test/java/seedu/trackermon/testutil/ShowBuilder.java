package seedu.trackermon.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.trackermon.model.show.Comment;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.Rating;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.tag.Tag;
import seedu.trackermon.model.util.SampleDataUtil;

public class ShowBuilder {

    public static final String DEFAULT_NAME = "Track Me";
    public static final String DEFAULT_STATUS = "completed";
    public static final String DEFAULT_TAG = "Anime";
    public static final String DEFAULT_COMMENT = "HELP";
    public static final String DEFAULT_RATING = "0";

    private Name name;
    private Status status;
    private Set<Tag> tags;
    private Comment comment;
    private Rating rating;

    /**
     * Creates a {@code ShowBuilder} with the default details.
     */
    public ShowBuilder() {
        name = new Name(DEFAULT_NAME);
        status = Status.getStatus(DEFAULT_STATUS);
        tags = new HashSet<>();
        comment = new Comment(DEFAULT_COMMENT);
        rating = new Rating(DEFAULT_RATING);
    }

    /**
     * Initializes the ShowBuilder with the data of {@code showToCopy}.
     */
    public ShowBuilder(Show showToCopy) {
        name = showToCopy.getName();
        status = showToCopy.getStatus();
        tags = new HashSet<>(showToCopy.getTags());
        comment = showToCopy.getComment();
        rating = showToCopy.getRating();
    }

    /**
     * Sets the {@code Name} of the {@code Show} that we are building.
     */
    public ShowBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Show} that we are building.
     */
    public ShowBuilder withStatus(String status) {
        this.status = Status.getStatus(status);
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Show} that we are building.
     */
    public ShowBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Show} that we are building to empty.
     */
    public ShowBuilder withComment() {
        this.comment = new Comment("");
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Show} that we are building.
     */
    public ShowBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Show} that we are building to empty.
     */
    public ShowBuilder withRating() {
        this.rating = new Rating("0");
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Show} that we are building.
     */
    public ShowBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Show build() {
        return new Show(name, status, tags, comment, rating);
    }
}
