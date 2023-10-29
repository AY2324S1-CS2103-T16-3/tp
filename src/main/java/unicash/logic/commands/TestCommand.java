package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.model.util.SampleDataUtil.getTestUniCash;

import unicash.model.Model;

/**
 * Sets UniCa$h to a test state by replacing the current UniCa$h
 * storage data and populating it with the test UniCa$h containing
 * test transactions from {@code SampleDataUtil}.
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test_unicash";
    public static final String MESSAGE_SUCCESS =
            "UNICA$H TEST MODE TRANSACTIONS ADDED!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUniCash(getTestUniCash());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
