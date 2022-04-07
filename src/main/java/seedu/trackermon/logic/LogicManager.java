package seedu.trackermon.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.trackermon.commons.core.GuiSettings;
import seedu.trackermon.commons.core.LogsCenter;
import seedu.trackermon.commons.exceptions.DataConversionException;
import seedu.trackermon.logic.commands.Command;
import seedu.trackermon.logic.commands.CommandResult;
import seedu.trackermon.logic.commands.exceptions.CommandException;
import seedu.trackermon.logic.parser.TrackermonParser;
import seedu.trackermon.logic.parser.exceptions.ParseException;
import seedu.trackermon.model.Model;
import seedu.trackermon.model.ReadOnlyShowList;
import seedu.trackermon.model.show.Show;
import seedu.trackermon.storage.Storage;



/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_SAVE_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String FILE_OPS_READ_ERROR_MESSAGE = "Could not read import data: File may be corrupted.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TrackermonParser parser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parser = new TrackermonParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = parser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            if (commandResult.isImport()) {
                Optional<ReadOnlyShowList> showListOptional = storage.readShowList();
                ReadOnlyShowList currentData = model.getShowList();

                model.setShowList(showListOptional.orElse(currentData));
            }
            storage.saveShowList(model.getShowList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_SAVE_ERROR_MESSAGE + ioe, ioe);
        } catch (DataConversionException dce) {
            undoImport();
            throw new CommandException(FILE_OPS_READ_ERROR_MESSAGE, dce);
        }

        return commandResult;
    }

    private void undoImport() throws CommandException {
        try {
            // Since model's show list has yet to be updated, we can "undo" the import
            storage.saveShowList(model.getShowList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_SAVE_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public ReadOnlyShowList getShowList() {
        return model.getShowList();
    }

    @Override
    public ObservableList<Show> getFilteredShowList() {
        return model.getFilteredShowList();
    }

    @Override
    public ObservableList<Show> getSortedShowList() {
        return model.getSortedShowList();
    }

    @Override
    public Path getShowListFilePath() {
        return model.getShowListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
