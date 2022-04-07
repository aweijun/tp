package seedu.trackermon.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public static final int DEFAULT_INDEX = -1;

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** The application should exit. */
    private final boolean isExit;

    /** The application is importing a file. */
    private final boolean isImport;

    /** The index information of commands */
    private final int indexAffected;

    /**
     * Constructs a {@code CommandResult} with all specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isExit, boolean isImport,
                         int indexAffected) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = isShowHelp;
        this.isExit = isExit;
        this.isImport = isImport;
        this.indexAffected = indexAffected;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code isShowHelp},
     * {@code isExit}, {@code isImport}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isExit, boolean isImport) {
        this(feedbackToUser, isShowHelp, isExit, isImport, DEFAULT_INDEX);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code isShowHelp},
     * {@code isExit}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isExit) {
        this(feedbackToUser, isShowHelp, isExit, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code indexAffected},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, int indexAffected) {
        this(feedbackToUser, false, false, false, indexAffected);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isImport() {
        return isImport;
    }

    public int getIndexAffected() {
        return indexAffected;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isExit);
    }
}
