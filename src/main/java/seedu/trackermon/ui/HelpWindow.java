package seedu.trackermon.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.trackermon.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {
    public static final String USERGUIDE_URL = "https://ay2122s2-cs2103t-t09-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final double TITLE_COLUMN_RATIO = 0.2;
    private static final double COMMAND_INPUT_RATIO = 0.795;

    private TableView<CommandBuilder> commandSummaryTableView;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private StackPane commandSummaryStackPane;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        commandSummaryTableView = new TableView<>();
        commandSummaryStackPane.getChildren().add(commandSummaryTableView);
        buildTable(commandSummaryTableView, getCommandSummary());
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Fills the table with command information.
     */
    private void buildTable(TableView<CommandBuilder> table, ObservableList<CommandBuilder> commands) {
        TableColumn<CommandBuilder, String> titleColumn = new TableColumn<>("Command Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("commandTitle"));

        TableColumn<CommandBuilder, String> commandInputColumn = new TableColumn<>("Command Input");
        commandInputColumn.setCellValueFactory(new PropertyValueFactory<>("commandInput"));

        table.setItems(commands);

        titleColumn.prefWidthProperty().bind(table.widthProperty().multiply(TITLE_COLUMN_RATIO));
        commandInputColumn.prefWidthProperty().bind(table.widthProperty().multiply(COMMAND_INPUT_RATIO));

        table.getColumns().add(titleColumn);
        table.getColumns().add(commandInputColumn);

        titleColumn.setStyle("-fx-alignment: CENTER;");

        table.prefHeightProperty().bind(commandSummaryStackPane.heightProperty());
    }

    /**
     * Describes the command information.
     */
    private static ObservableList<CommandBuilder> getCommandSummary() {
        return FXCollections.observableArrayList(
                new CommandBuilder("Add", "add n/NAME s/STATUS [r/RATING] [c/COMMENT] "
                        + " [t/TAG]…"),
                new CommandBuilder("Delete", "delete INDEX"),
                new CommandBuilder("Clear", "clear"),
                new CommandBuilder("Edit", "edit INDEX {[n/NAME] [s/STATUS] [r/RATING] "
                        + "[c/COMMENT] [t/TAG]…}"),
                new CommandBuilder("Exit", "exit"),
                new CommandBuilder("Export", "export"),
                new CommandBuilder("General Find", "find KEYWORD…"),
                new CommandBuilder("Precise Find", "find {[n/NAME] [s/STATUS]"
                        + " [r/RATING] [t/TAG]…}"),
                new CommandBuilder("Help", "help"),
                new CommandBuilder("Import", "import"),
                new CommandBuilder("List", "list"),
                new CommandBuilder("Sort", "sort [n/ORDER] [s/ORDER] [r/ORDER] [t/ORDER]"
                        + " [so/SEQUENCE]"),
                new CommandBuilder("Suggest", "suggest")
        );
    }

}
