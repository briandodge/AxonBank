package gov.dvla.osl.EventSource.api;

import com.mongodb.MongoClient;
import gov.dvla.osl.EventSource.api.query.AccountBalance;
import gov.dvla.osl.EventSource.api.repositories.MongoRepository;

public abstract class BalanceUpdatedEvent {


    String accountId;
    int balance;

    MongoRepository repository = new MongoRepository(new MongoClient(), "BankAccounts", "Accounts");

    public BalanceUpdatedEvent() {    }

    public BalanceUpdatedEvent(String accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
        repository.save(new AccountBalance(getAccountId(), getBalance()));
    }

    public String getAccountId() {
        return accountId;
    }

    public int getBalance() {
        return balance;
    }
}




