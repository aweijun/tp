package seedu.trackermon.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.trackermon.model.Model;
import seedu.trackermon.storage.JsonFileManager;


/**
 * Exports data from Trackermon.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exported data file.";
    public static final String MESSAGE_FAIL = "Data file export failed.";
    public static final String MESSAGE_ABORT = "Export data aborted.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        JsonFileManager jfm = new JsonFileManager("trackermon.json");

        int result = jfm.exportFile(model.getShowListFilePath());

        if (result == JsonFileManager.SUCCESS) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (result == JsonFileManager.CANCEL) {
            return new CommandResult(MESSAGE_ABORT);
        }

        return new CommandResult(MESSAGE_FAIL);
    }
}
