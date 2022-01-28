package org.xpdojo.bank;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
