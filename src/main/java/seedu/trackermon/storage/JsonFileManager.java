package seedu.trackermon.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javafx.stage.FileChooser;

public class JsonFileManager {

    public static final int SUCCESS = 0;
    public static final int CANCEL = 1;
    public static final int ERROR = 2;
    private static final String IMPORT_TITLE = "Choose a JSON file to load into Trackermon";
    private static final String EXPORT_TITLE = "Choose a location to save Trackermon's data";

    private FileChooser fileChooser;

    /**
     * Creates a {@code FileManager} that handles import and export without baseFileName.
     */
    public JsonFileManager() {
        this("");
    }

    /**
     * Creates a {@code FileManager} that handles import and export.
     * @param baseFileName default file name in JFileChooser
     */
    public JsonFileManager(String baseFileName) {
        fileChooser = new FileChooser();

        if (!baseFileName.isBlank()) {
            baseFileName = (baseFileName.endsWith(".json")) ? baseFileName : baseFileName + ".json";
            fileChooser.setInitialFileName(baseFileName);
        }

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON file", "*.json"));
    }

    /**
     * Takes in a dataPath and overwrites the selected file with filedata from dataPath.
     *
     * @param dataPath
     * @return int value representing outcome.
     */
    public int exportFile(Path dataPath) {
        fileChooser.setTitle(EXPORT_TITLE);
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            if (!selectedFile.getAbsolutePath().endsWith(".json")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".json");
            }

            Path exportPath = selectedFile.toPath();
            try {
                Files.copy(dataPath, exportPath, StandardCopyOption.REPLACE_EXISTING);
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                return ERROR;
            }
        }
        return CANCEL;
    }

    /**
     * Takes in a dataPath and overwrites the file at dataPath with selected file.
     *
     * @param dataPath
     * @return int value representing outcome.
     */
    public int importFile(Path dataPath) {
        fileChooser.setTitle(IMPORT_TITLE);
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Path importPath = selectedFile.toPath();
            try {
                Files.copy(importPath, dataPath, StandardCopyOption.REPLACE_EXISTING);
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                return ERROR;
            }
        }
        return CANCEL;
    }
}
