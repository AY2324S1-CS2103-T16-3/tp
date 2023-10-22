package unicash.ui;

import static unicash.ui.StyleSheet.TEXT_FILL_BLACK;
import static unicash.ui.StyleSheet.TEXT_FILL_GREEN;
import static unicash.ui.StyleSheet.TEXT_FILL_RED;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import unicash.model.transaction.Transaction;

/**
 * A UI Controller class for the status bar that is displayed at the
 * footer of the application. Contains information about data path and
 * balance indicator.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final String BALANCE_FORMAT = "Balance: $%.2f";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label balanceIndicator;

    private ObservableList<Transaction> transactions;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path} indicating the
     * current Data Source and a given {@code ObservableList} of transactions to
     * facilitate the updating of balance and keeping a balance counter.
     */
    public StatusBarFooter(Path saveLocation,
                           ObservableList<Transaction> transactionList) {
        super(FXML);
        saveLocationStatus.setText("Data source -> " + Paths.get(".").resolve(saveLocation));
        this.transactions = transactionList;

        /*
         * This initiates the first indication of balance upon application start,
         * before the method is added as a listener.
         */
        updateBalance(transactions);

        /*
         * The {@code updateBalance} method is added as a listener, which means that every
         * time the transactions list is updated, the balanceIndicator would be updated
         * dynamically. This is done with a {@code ListChangeListener} that reports a list
         * of changes done to an {@code ObservableList}
         */
        this.transactions.addListener((
                ListChangeListener.Change<? extends Transaction> c) -> {
            updateBalance(transactions);
        });
    }

    /**
     * Handles the updating of balanceIndicator in the status bar. If the balance
     * falls below 0, or goes into a negative value, the font style is set to the
     * universal red color style, and green otherwise.
     *
     * @param transactions the input transactions list to be monitored.
     */
    @FXML
    public void updateBalance(ObservableList<Transaction> transactions) {
        double balance = 0.0;

        for (Transaction t : transactions) {
            if (t.getTypeString().equalsIgnoreCase("expense")) {
                balance -= t.getAmount().amount;
            } else {
                balance += t.getAmount().amount;
            }
        }

        String balanceString = String.format(BALANCE_FORMAT, balance);
        balanceIndicator.setText(balanceString);

        if (Double.compare(balance, 0) < 0) {
            balanceIndicator.setStyle(TEXT_FILL_RED);

        } else if (Double.compare(balance, 0) > 0) {
            balanceIndicator.setStyle(TEXT_FILL_GREEN);

        } else {
            balanceIndicator.setStyle(TEXT_FILL_BLACK);
        }
    }

}
