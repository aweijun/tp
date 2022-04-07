package seedu.trackermon.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.trackermon.logic.commands.CommandTestUtil.DESC_ALICE_IN_WONDERLAND;
import static seedu.trackermon.logic.commands.CommandTestUtil.DESC_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_NAME_GONE;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;
import static seedu.trackermon.logic.commands.CommandTestUtil.VALID_TAG_SERIES;

import org.junit.jupiter.api.Test;

import seedu.trackermon.logic.commands.EditCommand.EditShowDescriptor;
import seedu.trackermon.testutil.EditShowDescriptorBuilder;

public class EditShowDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditShowDescriptor descriptorWithSameValues = new EditShowDescriptor(DESC_ALICE_IN_WONDERLAND);
        assertTrue(DESC_ALICE_IN_WONDERLAND.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALICE_IN_WONDERLAND.equals(DESC_ALICE_IN_WONDERLAND));

        // null -> returns false
        assertFalse(DESC_ALICE_IN_WONDERLAND.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALICE_IN_WONDERLAND.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALICE_IN_WONDERLAND.equals(DESC_GONE));

        // different name -> returns false
        EditShowDescriptor editedAmy = new EditShowDescriptorBuilder(DESC_ALICE_IN_WONDERLAND)
                .withName(VALID_NAME_GONE).build();
        assertFalse(DESC_ALICE_IN_WONDERLAND.equals(editedAmy));

        // different status -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_ALICE_IN_WONDERLAND).withStatus(VALID_STATUS_WATCHING).build();
        assertFalse(DESC_ALICE_IN_WONDERLAND.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_ALICE_IN_WONDERLAND).withTags(VALID_TAG_SERIES).build();
        assertFalse(DESC_ALICE_IN_WONDERLAND.equals(editedAmy));
    }
}
