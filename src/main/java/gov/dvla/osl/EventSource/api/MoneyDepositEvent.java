package gov.dvla.osl.EventSource.api;

import com.mongodb.MongoClient;
import gov.dvla.osl.EventSource.api.query.AccountBalance;
import gov.dvla.osl.EventSource.api.repositories.MongoRepository;

public class MoneyDepositEvent extends BalanceUpdatedEvent {

    int amount;



    public MoneyDepositEvent() {
    }

    public MoneyDepositEvent(String accountId, int amount, int balance) {
        super(accountId, balance);
        this.amount = amount;

    }



    public int getAmount() {
        return amount;
    }

}
