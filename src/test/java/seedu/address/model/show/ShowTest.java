package seedu.address.model.show;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SERIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalShows.ALICE_IN_WONDERLAND;
import static seedu.address.testutil.TypicalShows.YOU;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ShowBuilder;

public class ShowTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Show show = new ShowBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> show.getTags().remove(0));
    }

    @Test
    public void isSameShow() {
        // same object -> returns true
        assertTrue(ALICE_IN_WONDERLAND.isSameShow(ALICE_IN_WONDERLAND));

        // null -> returns false
        assertFalse(ALICE_IN_WONDERLAND.isSameShow(null));

        // same name, all other attributes different -> returns true
        Show editedAlice = new ShowBuilder(ALICE_IN_WONDERLAND).withStatus(VALID_STATUS_WATCHING)
                .withTags(VALID_TAG_SERIES).build();
        assertTrue(ALICE_IN_WONDERLAND.isSameShow(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ShowBuilder(ALICE_IN_WONDERLAND).withName(VALID_NAME_YOU).build();
        assertFalse(ALICE_IN_WONDERLAND.isSameShow(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Show editedYou = new ShowBuilder(YOU).withName(VALID_NAME_ME.toLowerCase()).build();
        assertFalse(YOU.isSameShow(editedYou));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_YOU + " ";
        editedYou = new ShowBuilder(YOU).withName(nameWithTrailingSpaces).build();
        assertFalse(YOU.isSameShow(editedYou));
    }

    @Test
    public void equals() {
        // same values -> returns true
        System.out.println("HERE" + ALICE_IN_WONDERLAND.toString());
        Show aliceCopy = new ShowBuilder(ALICE_IN_WONDERLAND).build();
        System.out.println("Hi");
        assertTrue(ALICE_IN_WONDERLAND.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_IN_WONDERLAND.equals(ALICE_IN_WONDERLAND));

        // null -> returns false
        assertFalse(ALICE_IN_WONDERLAND.equals(null));

        // different type -> returns false
        assertFalse(ALICE_IN_WONDERLAND.equals(5));

        // different Show -> returns false
        assertFalse(ALICE_IN_WONDERLAND.equals(YOU));

        // different name -> returns false
        Show editedAlice = new ShowBuilder(ALICE_IN_WONDERLAND).withName(VALID_NAME_YOU).build();
        assertFalse(ALICE_IN_WONDERLAND.equals(editedAlice));

        // different status -> returns false
        editedAlice = new ShowBuilder(ALICE_IN_WONDERLAND).withStatus(VALID_STATUS_WATCHING).build();
        assertFalse(ALICE_IN_WONDERLAND.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ShowBuilder(ALICE_IN_WONDERLAND).withTags(VALID_TAG_SERIES).build();
        assertFalse(ALICE_IN_WONDERLAND.equals(editedAlice));
    }

}
