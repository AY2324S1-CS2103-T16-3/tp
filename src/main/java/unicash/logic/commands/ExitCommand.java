package unicash.logic.commands;

import unicash.commons.util.ToStringBuilder;
import unicash.model.Model;

/**
 * Terminates UniCash.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting UniCa$h as requested ...";

    public static final String MESSAGE_FAILURE = String.format(
            "Exit command cannot have trailing arguments. "
                    + "Use the command %s without any trailing arguments.", COMMAND_WORD);

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExitCommand)) {
            return false;
        }

        return other instanceof ExitCommand;

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }

}
