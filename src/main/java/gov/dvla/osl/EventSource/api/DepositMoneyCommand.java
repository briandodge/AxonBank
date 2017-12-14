package gov.dvla.osl.EventSource.api;

import com.mongodb.MongoClient;
import gov.dvla.osl.EventSource.api.query.AccountBalance;
import gov.dvla.osl.EventSource.api.repositories.MongoRepository;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class DepositMoneyCommand {

    @TargetAggregateIdentifier
    String accountId;
    int amount;




    public DepositMoneyCommand() {    }

    public DepositMoneyCommand(String accountId, int amount) {
        this.accountId = accountId;
        this.amount = amount;




    }


    public String getAccountId() {
        return accountId;
    }

    public int getAmount() {
        return amount;
    }
}
