package org.xpdojo.bank;

public class AccountLine {
    public final int balance;
    public final String date;
    public final String time;

    public AccountLine(int balance, String balanceDate, String balanceTime) {
        this.balance=balance;
        this.date=balanceDate;
        this.time=balanceTime;
    }
}
