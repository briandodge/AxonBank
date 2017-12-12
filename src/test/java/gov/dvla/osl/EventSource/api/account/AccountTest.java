package gov.dvla.osl.EventSource.api.account;

import gov.dvla.osl.EventSource.api.AccountCreatedEvent;
import gov.dvla.osl.EventSource.api.CreateAccountCommand;
import gov.dvla.osl.EventSource.api.MoneyWithdrawnEvent;
import gov.dvla.osl.EventSource.api.WithdrawMoneyCommand;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Account.class);
    }


    @Test
    public void testCreateAccount() throws Exception {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand("1234", 1000))
                .expectEvents(new AccountCreatedEvent("1234",1000));

    }


    @Test
    public void testWithdrawReasonableAmount() {
        fixture.given(new AccountCreatedEvent("1234",1000))
                .when(new WithdrawMoneyCommand("1234", 600))
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
                        new MoneyWithdrawnEvent("1234, 999, -999"))
                .when(new MoneyWithdrawnEvent("1234", 2, -1))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }




}