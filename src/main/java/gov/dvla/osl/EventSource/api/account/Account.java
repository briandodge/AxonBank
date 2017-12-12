package gov.dvla.osl.EventSource.api.account;

import gov.dvla.osl.EventSource.api.AccountCreatedEvent;
import gov.dvla.osl.EventSource.api.CreateAccountCommand;
import gov.dvla.osl.EventSource.api.MoneyWithdrawnEvent;
import gov.dvla.osl.EventSource.api.WithdrawMoneyCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class Account {

    @AggregateIdentifier
    private String accountId;
    private int balance;
    private int overdraftLimit;

    public Account() {     }

    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getOverDraftLimit()));
    }


    @CommandHandler
    public void handle(WithdrawMoneyCommand command) throws OverdraftLimitExceededException {
        if (balance + overdraftLimit >= command.getAmount()) {
            apply(new MoneyWithdrawnEvent(accountId, command.getAmount(), balance - command.getAmount()));
        } else {
            throw new OverdraftLimitExceededException();
        }
    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.overdraftLimit = event.getOverDraftLimit();
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event){
        this.balance = event.getBalance();
    }

}
