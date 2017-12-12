package gov.dvla.osl.EventSource.api;

public class AccountCreatedEvent {

    String accountId;
    int overDraftLimit;

    public AccountCreatedEvent() {
    }

    public AccountCreatedEvent(String accountId, int overDraftLimit) {
        this.accountId = accountId;
        this.overDraftLimit = overDraftLimit;
    }


    public String getAccountId() {
        return accountId;
    }

    public int getOverDraftLimit() {
        return overDraftLimit;
    }
}
