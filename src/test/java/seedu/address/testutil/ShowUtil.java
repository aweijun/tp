//package seedu.address.testutil;
//
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.model.show.Show;
//
//public class ShowUtil {
//    /**
//     * Returns an add command string for adding the {@code Show}.
//     */
//    public static String getAddCommand(Show show) {
//        return AddCommand.COMMAND_WORD + " " + getShowDetails(show);
//    }
//
//    /**
//     * Returns the part of command string for the given {@code Show}'s details.
//     */
//    public static String getShowDetails(Show show) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_NAME + show.getName().fullName + " ");
//        sb.append()
//        sb.append(PREFIX_ADDRESS + Show.getStatus().value + " ");
//        Show.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }
//
//    /**
//     * Returns the part of command string for the given {@code EditShowDescriptor}'s details.
//     */
//    public static String getEditShowDescriptorDetails(EditShowDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
//        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
//        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
//}
