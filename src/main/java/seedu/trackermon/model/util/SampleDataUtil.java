package seedu.trackermon.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.ShowList;
import seedu.trackermon.model.show.Comment;
import seedu.trackermon.model.show.Name;
import seedu.trackermon.model.show.Rating;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.model.show.Status;
import seedu.trackermon.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ShowList} with sample data.
 */
public class SampleDataUtil {
    public static Show[] getSampleShows() {
        return new Show[] {
            new Show(new Name("Attack on Titan"), Status.WATCHING, getTagSet("Anime"), new Comment("Levi"),
                    new Rating(3)),
            new Show(new Name("Another"), Status.COMPLETED, getTagSet("Anime", "Horror"), new Comment("WTF"),
                    new Rating(3))
        };
    }

    public static ReadOnlyShowList getSampleShowList() {
        ShowList sampleShowList = new ShowList();
        for (Show sampleShow : getSampleShows()) {
            sampleShowList.addShow(sampleShow);
        }
        return sampleShowList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
