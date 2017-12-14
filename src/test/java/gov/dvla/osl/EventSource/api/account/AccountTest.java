package gov.dvla.osl.EventSource.api.account;

import gov.dvla.osl.EventSource.api.AccountCreatedEvent;
import gov.dvla.osl.EventSource.api.CreateAccountCommand;
import gov.dvla.osl.EventSource.api.MoneyWithdrawnEvent;
import gov.dvla.osl.EventSource.api.WithdrawMoneyCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        //fixture = Fixtures.newGivenWhenThenFixture(Account.class); // 3.0-M4
        fixture = new AggregateTestFixture<>(Account.class);
    }


    @Test
    public void testCreateAccount() throws Exception {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand("1234", 1000))
                .expectReturnValue("1234")
                .expectEvents(new AccountCreatedEvent("1234",1000));

    }


    @Test
    public void testWithdrawReasonableAmount() {
        fixture.given(new AccountCreatedEvent("1234",1000))
                .when(new WithdrawMoneyCommand("1234", 600))
                .expectReturnValue(null)
                .expectEvents(new MoneyWithdrawnEvent("1234",600,-600));
    }

    @Test
    public void testWithdrawAbsurdAmount() {
        fixture.given(new AccountCreatedEvent("1234",1000))
                .when(new WithdrawMoneyCommand("1234", 1001))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }


    @Test
    public void testWithdrawTwice() {
        fixture.given(new AccountCreatedEvent("1234", 1000),
                        new MoneyWithdrawnEvent("1234", 999, -999))
                .when(new WithdrawMoneyCommand("1234", 2))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }




}