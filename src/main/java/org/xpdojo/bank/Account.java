package org.xpdojo.bank;

public class Account {
    private int balance = 0;

    public static Account emptyAccount() {
        return new Account();
    }

    public int balance() {
        return balance;
    }

    public void deposit(int amount) {
        balance+=amount;
    }

    public void withdraw(int amount) {
        balance-=amount;
    }
}
