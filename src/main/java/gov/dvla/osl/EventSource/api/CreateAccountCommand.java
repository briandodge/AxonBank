package gov.dvla.osl.EventSource.api;

public class CreateAccountCommand {

    String accountId;
    int overDraftLimit;

    public CreateAccountCommand() {
    }

    public CreateAccountCommand(String accountId, int overDraftLimit) {
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
