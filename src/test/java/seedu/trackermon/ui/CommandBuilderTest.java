package seedu.trackermon.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class CommandBuilderTest {

    private CommandBuilder commandBuilder = new CommandBuilder("Delete", "delete INDEX");

    @Test
    void getCommandTitle() {
        assertEquals("Delete", commandBuilder.getCommandTitle());
    }

    @Test
    void getCommandInput() {
        assertEquals("delete INDEX", commandBuilder.getCommandInput());
    }

    @Test
    void commandTitleProperty() {
        assertTrue(commandBuilder.commandTitleProperty().get().equals("Delete"));
    }

    @Test
    void commandInputProperty() {
        assertTrue(commandBuilder.commandInputProperty().get().equals("delete INDEX"));
    }
}
