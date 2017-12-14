package gov.dvla.osl.EventSource.api.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;
import gov.dvla.osl.EventSource.api.repositories.MongoRepository;

public class AccountBalance {

    String accountId;
    int balance;

    public AccountBalance() {
    }

    public AccountBalance(String accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    @JsonProperty
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty
    public int getBalance() {
        return balance;
    }
}