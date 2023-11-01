package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import unicash.commons.enums.CommandType;
import unicash.model.Model;
import unicash.model.UniCash;

/**
 * Clears all transactions in UniCash.
 */
public class ClearTransactionsCommand extends Command {

    public static final String COMMAND_WORD = CommandType.CLEAR_TRANSACTIONS.getCommandWords();
    public static final String MESSAGE_SUCCESS = CommandType.CLEAR_TRANSACTIONS.getMessageSuccess();
    public static final String MESSAGE_USAGE = CommandType.CLEAR_TRANSACTIONS.getMessageUsage();


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUniCash(new UniCash());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
