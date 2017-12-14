package gov.dvla.osl.EventSource.api.query;

import gov.dvla.osl.EventSource.api.BalanceUpdatedEvent;
import gov.dvla.osl.EventSource.api.repositories.MongoRepository;
import org.axonframework.eventhandling.EventHandler;

public class AccountBalanceEventHandler {

    MongoRepository repository;

    public AccountBalanceEventHandler(MongoRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(BalanceUpdatedEvent event){
        repository.save(new AccountBalance(event.getAccountId(), event.getBalance()));
    }

}
