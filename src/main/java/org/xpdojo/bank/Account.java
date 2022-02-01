package org.xpdojo.bank;

public class Account {
    public static final String AMOUNT_MUST_BE_POSITIVE = "amount must be positive";
    public static final String AMOUNT_EXCEEDS_FUNDS = "not enough funds to withdraw amount";

    private int balance = 0;

    public static Account emptyAccount() {
        return new Account();
    }

    public int balance() {
        return balance;
    }

    public void deposit(int amount) throws IllegalArgumentException {
        if(amount<=0) throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE);
        balance+=amount;
    }

    public void withdraw(int amount) throws IllegalArgumentException{
        if(amount<=0) throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE);
        if(amount>balance) throw new IllegalArgumentException(AMOUNT_EXCEEDS_FUNDS);
        balance-=amount;
    }

    public void transferTo(int amount, Account toAccount) {
        withdraw(amount);
        toAccount.deposit(amount);
    }
}
