package gov.dvla.osl.EventSource.api;

public class MoneyWithdrawnEvent extends BalanceUpdatedEvent{

    int amount;


    public MoneyWithdrawnEvent() {     }

    public MoneyWithdrawnEvent(String accountId, int amount, int balance) {
        super(accountId, balance);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

}
