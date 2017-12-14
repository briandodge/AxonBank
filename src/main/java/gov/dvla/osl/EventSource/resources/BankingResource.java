package gov.dvla.osl.EventSource.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import gov.dvla.osl.EventSource.api.CreateAccountCommand;

import gov.dvla.osl.EventSource.api.DepositMoneyCommand;
import gov.dvla.osl.EventSource.api.WithdrawMoneyCommand;
import gov.dvla.osl.EventSource.api.account.Account;
import gov.dvla.osl.EventSource.api.account.AccountData;

import gov.dvla.osl.EventSource.api.query.AccountBalance;
import gov.dvla.osl.EventSource.api.repositories.MongoRepository;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.DataSourceConnectionProvider;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;

import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy;
import org.axonframework.serialization.json.JacksonSerializer;


import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.io.IOException;
import java.sql.SQLException;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Path("/banking")
public class BankingResource {


    private Configuration config;

    public BankingResource() {

        //final MongoEventStorageEngine engine = mongoEventStorageEngine();


        config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Account.class)
                //.configureCommandBus(c -> new SimpleCommandBus())
                //.configureCommandBus(c -> new AsynchronousCommandBus())
                .configureEmbeddedEventStore(c -> mongoEventStorageEngine())
                //.configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .buildConfiguration();

        config.start();

    }


    private MongoEventStorageEngine mongoEventStorageEngine(){

        return new MongoEventStorageEngine(
                new JacksonSerializer(),
                null,   // careful, wont work in earlier versions
                mongoTemplate(),
                new DocumentPerCommitStorageStrategy());
    }

    private MongoTemplate mongoTemplate(){

        MongoClient client = new MongoClient("127.0.0.1", 27017);

        return new DefaultMongoTemplate(client, "axon", "domainevents", "snapshotevents");
    }

    private JdbcEventStorageEngine jdbcEventStorageEngine() throws SQLException, NamingException {


//        Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
//        source.setDataSourceName("A Data Source");
//        source.setServerName("localhost");
//        source.setDatabaseName("test");
//        source.setUser("testuser");
//        source.setPassword("testpassword");
//        source.setMaxConnections(10);
//        new InitialContext().rebind("DataSource", source);
//
//        DataSource source = (DataSource) new InitialContext().lookup("DataSource");

        ConnectionProvider provider = new DataSourceConnectionProvider(null);

        return new JdbcEventStorageEngine(null,
                null,null,provider,null);
    }

    MongoRepository<AccountBalance, String> repository = new MongoRepository<AccountBalance, String>(new MongoClient(), "BankAccounts", "Accounts");


    @GET
    @Path("/balance/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response balance(@PathParam("accountId") String accountId){
        ObjectMapper mapper = new ObjectMapper();
        String json = repository.find(accountId);
        AccountBalance balance = null;
        try {
            balance = mapper.readValue(json, AccountBalance.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(balance).build();
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account){
        config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand(account.getAccountId(), account.getOverdraftLimit())));
        return Response.noContent().build();
    }


    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response withdrawMonies(AccountData data){

        config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand(data.getAccountId(), data.getAmount())));

        return Response.noContent().build();
    }

    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response depositMonies(AccountData data){
        config.commandBus().dispatch(asCommandMessage(new DepositMoneyCommand(data.getAccountId(), data.getAmount())));

        return Response.noContent().build();
    }



}
