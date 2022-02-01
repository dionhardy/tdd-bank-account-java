package org.xpdojo.bank;

public class AccountLine {
    public int balance;
    public String date;
    public String time;

    public AccountLine(int balance, String balanceDate, String balanceTime) {
        this.balance=balance;
        this.date=balanceDate;
        this.time=balanceTime;
    }
}
