package gov.dvla.osl.EventSource.api.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.dvla.osl.EventSource.api.*;
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

    @CommandHandler
    public void handle(DepositMoneyCommand command) {
        apply(new MoneyDepositEvent(accountId, command.getAmount(), balance + command.getAmount()));
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

    @EventSourcingHandler
    public void on(MoneyDepositEvent event) { this.balance = event.getBalance(); }


    @JsonProperty
    public String getAccountId() {
        return accountId;
    }
    @JsonProperty
    public int getBalance() {
        return balance;
    }
    @JsonProperty
    public int getOverdraftLimit() {
        return overdraftLimit;
    }
}
