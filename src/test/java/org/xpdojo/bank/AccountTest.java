package org.xpdojo.bank;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    public void account_emptyAccountBalanceIsZero() {
        assertThat(Account.emptyAccount().balance()).isEqualTo(0);
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
}
