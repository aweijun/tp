package seedu.trackermon.model.show;

public class Comment {

    public final String comment;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A valid comment.
     */
    public Comment(String comment) {
        String emojiFilter = "[(\\u00a9|\\u00ae|[\\u2000-\\u3300]|\\ud83c[\\ud000-\\udfff]|"
                + "\\ud83d[\\ud000-\\udfff]|\\ud83e[\\ud000-\\udfff])]";
        this.comment = comment.replaceAll(emojiFilter, "");
    }

    @Override
    public String toString() {
        return comment;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Comment
                && comment.equals(((Comment) other).comment));
    }

    @Override
    public int hashCode() {
        return comment.hashCode();
    }
}
