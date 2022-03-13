package seedu.address.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SERIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalShows.ME;
import static seedu.address.testutil.TypicalShows.YOU;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.show.exceptions.DuplicatedShowException;
import seedu.address.model.show.exceptions.ShowNotFoundException;
import seedu.address.testutil.ShowBuilder;

public class UniqueShowListTest {
    private final UniqueShowList uniqueShowList = new UniqueShowList();

    @Test
    public void contains_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.contains(null));
    }

    @Test
    public void contains_showNotInList_returnsFalse() {
        assertFalse(uniqueShowList.contains(ME));
    }

    @Test
    public void contains_showInList_returnsTrue() {
        uniqueShowList.add(ME);
        assertTrue(uniqueShowList.contains(ME));
    }

    @Test
    public void contains_showWithSameIdentityFieldsInList_returnsTrue() {
        uniqueShowList.add(ME);
        Show editedMe = new ShowBuilder(ME).withTags(VALID_TAG_MOVIE)
                .build();
        assertTrue(uniqueShowList.contains(editedMe));
    }

    @Test
    public void add_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.add(null));
    }

    @Test
    public void add_duplicateShow_throwsDuplicateShowException() {
        uniqueShowList.add(ME);
        assertThrows(DuplicatedShowException.class, () -> uniqueShowList.add(ME));
    }

    @Test
    public void setShow_nullTargetShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShow(null, ME));
    }

    @Test
    public void setShow_nullEditedShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShow(ME, null));
    }

    @Test
    public void setShow_targetShowNotInList_throwsShowNotFoundException() {
        assertThrows(ShowNotFoundException.class, () -> uniqueShowList.setShow(ME, ME));
    }

    @Test
    public void setShow_editedShowIsSameShow_success() {
        uniqueShowList.add(ME);
        uniqueShowList.setShow(ME, ME);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(ME);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShow_editedShowHasSameIdentity_success() {
        uniqueShowList.add(ME);
        Show editedMe = new ShowBuilder(ME).withStatus(VALID_STATUS_WATCHING).withTags(VALID_TAG_SERIES)
                .build();
        uniqueShowList.setShow(ME, editedMe);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(editedMe);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShow_editedShowHasDifferentIdentity_success() {
        uniqueShowList.add(ME);
        uniqueShowList.setShow(ME, YOU);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(YOU);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShow_editedShowHasNonUniqueIdentity_throwsDuplicateShowException() {
        uniqueShowList.add(ME);
        uniqueShowList.add(YOU);
        assertThrows(DuplicatedShowException.class, () -> uniqueShowList.setShow(ME, YOU));
    }

    @Test
    public void remove_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.remove(null));
    }

    @Test
    public void remove_showDoesNotExist_throwsShowNotFoundException() {
        assertThrows(ShowNotFoundException.class, () -> uniqueShowList.remove(ME));
    }

    @Test
    public void remove_existingShow_removesShow() {
        uniqueShowList.add(ME);
        uniqueShowList.remove(ME);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShows_nullUniqueShowList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShows((UniqueShowList) null));
    }

    @Test
    public void setShows_uniqueShowList_replacesOwnListWithProvidedUniqueShowList() {
        uniqueShowList.add(ME);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(YOU);
        uniqueShowList.setShows(expectedUniqueShowList);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShows_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShows((List<Show>) null));
    }

    @Test
    public void setShows_list_replacesOwnListWithProvidedList() {
        uniqueShowList.add(ME);
        List<Show> showList = Collections.singletonList(YOU);
        uniqueShowList.setShows(showList);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(YOU);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShows_listWithDuplicateShows_throwsDuplicateShowException() {
        List<Show> listWithDuplicateShows = Arrays.asList(ME, ME);
        assertThrows(DuplicatedShowException.class, () -> uniqueShowList.setShows(listWithDuplicateShows));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueShowList.asUnmodifiableObservableList().remove(0));
    }
}
