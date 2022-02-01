package org.xpdojo.bank;

public class AccountLine {
    public final int balance;
    public final String date;
    public final String time;
    public final String type;

    public AccountLine(String type, int balance, String balanceDate, String balanceTime) {
        this.type=type;
        this.balance=balance;
        this.date=balanceDate;
        this.time=balanceTime;
    }
}
