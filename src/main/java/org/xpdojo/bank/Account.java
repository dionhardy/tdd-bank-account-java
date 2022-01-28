package org.xpdojo.bank;

public class Account {
    private int balance = 0;

    public static Account emptyAccount() {
        return new Account();
    }

    public int balance() {
        return balance;
    }

    public void deposit(int amount) throws IllegalArgumentException {
        if(amount<=0) throw new IllegalArgumentException("amount must be positive");
        balance+=amount;
    }

    public void withdraw(int amount) {
        balance-=amount;
    }

    public void transfer(int amount, Account toAccount) {
        withdraw(amount);
        toAccount.deposit(amount);
    }
}
