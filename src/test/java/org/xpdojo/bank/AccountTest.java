package org.xpdojo.bank;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    public void account_emptyAccountBalanceIsZero() {
        assertThat(Account.emptyAccount().balance()).isEqualTo(0);
    }

    @Test
    public void account_emptyAccountBalanceHasDateTime() {
        Account theAccount = Account.emptyAccount();
        assertThat(theAccount.balanceDate()).isNotEmpty();
        assertThat(theAccount.balanceTime()).isNotEmpty();
    }

    @Test
    public void account_depositSingleAmount() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        assertThat(theAccount.balance()).isEqualTo(10);
    }

    @Test
    public void account_depositMultipleAmounts() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        theAccount.deposit(20);
        assertThat(theAccount.balance()).isEqualTo(30);
    }

    @Test()
    public void account_depositZeroAmount() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Account.emptyAccount().deposit(0));
        assertThat(exception.getMessage()).isEqualTo("amount must be positive");
    }

    @Test()
    public void account_depositNegativeAmount() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Account.emptyAccount().deposit(-1));
        assertThat(exception.getMessage()).isEqualTo("amount must be positive");
    }

    @Test
    public void account_withdrawSingleAmount() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        theAccount.withdraw(5);
        assertThat(theAccount.balance()).isEqualTo(5);
    }

    @Test
    public void account_withdrawMultipleAmounts() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        theAccount.withdraw(5);
        theAccount.withdraw(3);
        assertThat(theAccount.balance()).isEqualTo(2);
    }

    @Test()
    public void account_withdrawZeroAmount() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Account.emptyAccount().withdraw(0));
        assertThat(exception.getMessage()).isEqualTo(Account.AMOUNT_MUST_BE_POSITIVE);
    }

    @Test()
    public void account_withdrawNegativeAmount() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Account.emptyAccount().withdraw(-1));
        assertThat(exception.getMessage()).isEqualTo(Account.AMOUNT_MUST_BE_POSITIVE);
    }

    @Test()
    public void account_withdrawAboveFundsAmount() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> theAccount.withdraw(15));
        assertThat(exception.getMessage()).isEqualTo(Account.AMOUNT_EXCEEDS_FUNDS);
    }

    @Test
    public void account_transferSingleAmount() {
        Account fromAccount = Account.emptyAccount();
        fromAccount.deposit(10);
        Account toAccount = Account.emptyAccount();
        fromAccount.transferTo(5,toAccount);
        assertThat(fromAccount.balance()).isEqualTo(5);
        assertThat(toAccount.balance()).isEqualTo(5);
    }

    @Test
    public void account_transferZeroAmount() {
        Account fromAccount = Account.emptyAccount();
        fromAccount.deposit(10);
        Account toAccount = Account.emptyAccount();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> fromAccount.transferTo(0,toAccount));
        assertThat(exception.getMessage()).isEqualTo(Account.AMOUNT_MUST_BE_POSITIVE);
        assertThat(fromAccount.balance()).isEqualTo(10);
        assertThat(toAccount.balance()).isEqualTo(0);
    }

    @Test
    public void account_transferNegativeAmount() {
        Account fromAccount = Account.emptyAccount();
        fromAccount.deposit(10);
        Account toAccount = Account.emptyAccount();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> fromAccount.transferTo(-1,toAccount));
        assertThat(exception.getMessage()).isEqualTo(Account.AMOUNT_MUST_BE_POSITIVE);
        assertThat(fromAccount.balance()).isEqualTo(10);
        assertThat(toAccount.balance()).isEqualTo(0);
    }

    @Test
    public void account_transferAmountAboveFunds() {
        Account fromAccount = Account.emptyAccount();
        fromAccount.deposit(10);
        Account toAccount = Account.emptyAccount();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> fromAccount.transferTo(15,toAccount));
        assertThat(exception.getMessage()).isEqualTo(Account.AMOUNT_EXCEEDS_FUNDS);
        assertThat(fromAccount.balance()).isEqualTo(10);
        assertThat(toAccount.balance()).isEqualTo(0);
    }

    @Test
    public void account_depositAmountAndDateTime() {
        Account theAccount = Account.emptyAccount();
        String dt = "2022-02-01";
        String tm = "16:56";
        theAccount.depositWithDateTime(10, dt, tm);
        assertThat(theAccount.balance()).isEqualTo(10);
        assertThat(theAccount.balanceDate()).isEqualTo(dt);
        assertThat(theAccount.balanceTime()).isEqualTo(tm);
    }

    @Test
    public void account_withdrawAmountAndDateTime() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        String dt = "2022-02-01";
        String tm = "16:56";
        theAccount.withdrawWithDateTime(5, dt, tm);
        assertThat(theAccount.balance()).isEqualTo(5);
        assertThat(theAccount.balanceDate()).isEqualTo(dt);
        assertThat(theAccount.balanceTime()).isEqualTo(tm);
    }

    @Test
    public void account_balanceSlip_latestAccountLine() {
        Account theAccount = Account.emptyAccount();
        String dt="2022-02-01";
        String tm="16:56";
        theAccount.depositWithDateTime(10,dt,tm);
        AccountLine accountLine = theAccount.latestAccountLine();
        assertThat(accountLine.balance).isEqualTo(10);
        assertThat(accountLine.date).isEqualTo(dt);
        assertThat(accountLine.time).isEqualTo(tm);
        assertThat(accountLine.amount).isEqualTo(10);
    }

    @Test
    public void account_statement() {
        Account theAccount = Account.emptyAccount();

        String dtDeposit="2022-02-01";
        String tmDeposit="16:56";
        theAccount.depositWithDateTime(10,dtDeposit,tmDeposit);

        String dtWithdraw = "2022-02-11";
        String tmWithdraw = "16:57";
        theAccount.withdrawWithDateTime(5,dtWithdraw,tmWithdraw);

        List<AccountLine> statement = theAccount.statement();
        assertThat(statement.size()).isEqualTo(3);

        //0 is the starting  balance line
        AccountLine accountLine = statement.get(0);
        assertThat(accountLine.balance).isEqualTo(0);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_OPEN);
        assertThat(accountLine.amount).isEqualTo(0);

        //1 is the deposit
        accountLine=statement.get(1);
        assertThat(accountLine.balance).isEqualTo(10);
        assertThat(accountLine.date).isEqualTo(dtDeposit);
        assertThat(accountLine.time).isEqualTo(tmDeposit);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_DEPOSIT);
        assertThat(accountLine.amount).isEqualTo(10);

        //2 is the withdraw
        accountLine=statement.get(2);
        assertThat(accountLine.balance).isEqualTo(5);
        assertThat(accountLine.date).isEqualTo(dtWithdraw);
        assertThat(accountLine.time).isEqualTo(tmWithdraw);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_WITHDRAW);
        assertThat(accountLine.amount).isEqualTo(5);
    }

    @Test
    public void account_statement_filteredType() {
        Account theAccount = Account.emptyAccountWithDateTime("2022-01-01","12:00");

        String dtDeposit="2022-02-01";
        String tmDeposit="16:56";
        theAccount.depositWithDateTime(10,dtDeposit,tmDeposit);

        String dtWithdraw = "2022-02-01";
        String tmWithdraw = "16:57";
        theAccount.withdrawWithDateTime(5,dtWithdraw,tmWithdraw);

        List<AccountLine> statement = theAccount.statement(Account.TYPE_DEPOSIT, null, null, null, null);
        assertThat(statement.size()).isEqualTo(1);

        //the deposit
        AccountLine accountLine = statement.get(0);
        assertThat(accountLine.balance).isEqualTo(10);
        assertThat(accountLine.date).isEqualTo(dtDeposit);
        assertThat(accountLine.time).isEqualTo(tmDeposit);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_DEPOSIT);
        assertThat(accountLine.amount).isEqualTo(10);

        statement = theAccount.statement(Account.TYPE_WITHDRAW, null, null, null, null);
        assertThat(statement.size()).isEqualTo(1);

        //the withdraw
        accountLine=statement.get(0);
        assertThat(accountLine.balance).isEqualTo(5);
        assertThat(accountLine.date).isEqualTo(dtWithdraw);
        assertThat(accountLine.time).isEqualTo(tmWithdraw);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_WITHDRAW);
        assertThat(accountLine.amount).isEqualTo(5);
    }

    @Test
    public void account_statement_filteredDate() {
        Account theAccount = Account.emptyAccountWithDateTime("2022-01-01","12:00");

        String dtDeposit="2022-02-01";
        String tmDeposit="16:56";
        theAccount.depositWithDateTime(10,dtDeposit,tmDeposit);

        String dtWithdraw = "2022-02-11";
        String tmWithdraw = "16:57";
        theAccount.withdrawWithDateTime(5,dtWithdraw,tmWithdraw);

        List<AccountLine> statement = theAccount.statement(null,"2022-02-11", null, null, null);
        assertThat(statement.size()).isEqualTo(1);

        //the withdraw by date
        AccountLine accountLine = statement.get(0);
        assertThat(accountLine.balance).isEqualTo(5);
        assertThat(accountLine.date).isEqualTo(dtWithdraw);
        assertThat(accountLine.time).isEqualTo(tmWithdraw);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_WITHDRAW);
        assertThat(accountLine.amount).isEqualTo(5);
    }


    @Test
    public void account_statement_filteredDateTime() {
        Account theAccount = Account.emptyAccountWithDateTime("2022-01-01","12:00");

        String dtDeposit="2022-02-01";
        String tmDeposit="16:56";
        theAccount.depositWithDateTime(10,dtDeposit,tmDeposit);

        String dtWithdraw = "2022-02-11";
        String tmWithdraw = "16:57";
        theAccount.withdrawWithDateTime(5,dtWithdraw,tmWithdraw);

        String dtWithdraw2 = "2022-02-11";
        String tmWithdraw2 = "17:01";
        theAccount.withdrawWithDateTime(2,dtWithdraw2,tmWithdraw2);

        List<AccountLine> statement = theAccount.statement(null,"2022-02-11","16:00","2022-02-11","17:00");
        assertThat(statement.size()).isEqualTo(1);

        //the first withdraw by date and time
        AccountLine accountLine = statement.get(0);
        assertThat(accountLine.balance).isEqualTo(5);
        assertThat(accountLine.amount).isEqualTo(5);
        assertThat(accountLine.date).isEqualTo(dtWithdraw);
        assertThat(accountLine.time).isEqualTo(tmWithdraw);
        assertThat(accountLine.type).isEqualTo(Account.TYPE_WITHDRAW);
    }}
