package seedu.trackermon.testutil;

import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_COMMENT_BAD;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_COMMENT_GOOD;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_HANCOCK;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_WEATHERING_WITH_YOU;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_COMPLETED;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_MOVIE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_SERIES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.trackermon.model.ShowList;
import seedu.trackermon.model.show.Show;


public class TypicalShows {
    public static final Show ALICE_IN_WONDERLAND = new ShowBuilder().withName("Alice in WonderLand")
            .withStatus("completed").withTags("movie").withComment("bad").withRating("5").build();
    public static final Show GONE = new ShowBuilder().withName("gone")
            .withStatus("watching").withTags().withComment("bad").withRating().build();
    public static final Show FRIENDS = new ShowBuilder().withName("friends")
            .withStatus("watching").withTags("Horror", "friends").withComment("good").withRating().build();
    public static final Show HIMYM = new ShowBuilder().withName("HIMYM")
            .withStatus("watching").withTags("Horror", "friends").withComment("good").withRating().build();

    public static final Show HANCOCK = new ShowBuilder().withName(VALID_NAME_HANCOCK).withStatus(VALID_STATUS_WATCHING)
            .withTags(VALID_TAG_MOVIE, VALID_TAG_SERIES).withComment(VALID_COMMENT_GOOD).build();
    // Manually added - Show's details found in {@code CommandTestUtil}
    public static final Show WEATHERING_WITH_YOU = new ShowBuilder().withName(VALID_NAME_WEATHERING_WITH_YOU)
            .withStatus(VALID_STATUS_COMPLETED).withTags(VALID_TAG_MOVIE).withComment(VALID_COMMENT_BAD).build();

    private TypicalShows() {} // prevents instantiation

    /**
     * Returns an {@code ShowList} with all the typical shows.
     */
    public static ShowList getTypicalShowList() {
        ShowList ab = new ShowList();
        for (Show show : getTypicalShows()) {
            ab.addShow(show);
        }
        return ab;
    }

    /**
     * Returns an {@code List} with all the typical shows.
     */
    public static List<Show> getTypicalShows() {
        return new ArrayList<>(Arrays.asList(ALICE_IN_WONDERLAND, GONE,
                FRIENDS, HIMYM, HANCOCK));
    }
}
