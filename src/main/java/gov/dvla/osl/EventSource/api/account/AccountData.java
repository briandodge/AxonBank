package gov.dvla.osl.EventSource.api.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountData {

    String accountId;
    int amount;

    public AccountData() {
    }

    public AccountData(String accountId, int amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    @JsonProperty
    public String getAccountId() {
        return accountId;
    }
    @JsonProperty

    public int getAmount() {
        return amount;
    }
}
