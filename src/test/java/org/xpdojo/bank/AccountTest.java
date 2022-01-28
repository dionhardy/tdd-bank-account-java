package org.xpdojo.bank;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    public void account_emptyAccountBalanceIsZero() {
        assertThat(Account.emptyAccount().balance()).isEqualTo(0);
    }

    @Test
    public void account_addSingleDeposit() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        assertThat(theAccount.balance()).isEqualTo(10);
    }

    @Test
    public void account_addTwoDeposits() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        theAccount.deposit(20);
        assertThat(theAccount.balance()).isEqualTo(30);
    }

    @Test()
    public void account_addZeroDeposit() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Account.emptyAccount().deposit(0));
        assertThat(exception.getMessage()).isEqualTo("amount must be positive");
    }

    @Test()
    public void account_addNegativeDeposit() {
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
    public void account_withdrawMultipleAmount() {
        Account theAccount = Account.emptyAccount();
        theAccount.deposit(10);
        theAccount.withdraw(5);
        theAccount.withdraw(3);
        assertThat(theAccount.balance()).isEqualTo(2);
    }

    @Test()
    public void account_makeZeroWithdraw() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Account.emptyAccount().withdraw(0));
        assertThat(exception.getMessage()).isEqualTo("amount must be positive");
    }

    @Test
    public void account_transferAmount() {
        Account fromAccount = Account.emptyAccount();
        fromAccount.deposit(10);
        Account toAccount = Account.emptyAccount();
        fromAccount.transfer(5,toAccount);
        assertThat(fromAccount.balance()).isEqualTo(5);
        assertThat(toAccount.balance()).isEqualTo(5);
    }
}
