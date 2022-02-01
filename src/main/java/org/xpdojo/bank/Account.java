package org.xpdojo.bank;


import java.util.Calendar;

public class Account {
    public static final String AMOUNT_MUST_BE_POSITIVE = "amount must be positive";
    public static final String AMOUNT_EXCEEDS_FUNDS = "not enough funds to withdraw amount";

    private int balance = 0;
    private String balanceDate="";
    private String balanceTime="";
    private AccountLine accountLine;

    public static Account emptyAccount() {
        Account account = new Account();
        account.setBalanceDate(null,null);
        return account;
    }

    private void setBalanceDate(String dt, String tm) {
        if(dt==null || tm==null || dt.length()==0 || tm.length()==0){
            Calendar cal=Calendar.getInstance();
            dt=DateTimeHelper.getDate(cal);
            tm=DateTimeHelper.getTime(cal);
        }
        balanceDate=dt;
        balanceTime=tm;
        accountLine=new AccountLine(balance,balanceDate,balanceTime);
    }

    public int balance() {
        return balance;
    }
    public String balanceDate() { return balanceDate; }
    public String balanceTime() { return balanceTime; }

    public void deposit(int amount) throws IllegalArgumentException {
        depositWithDateTime(amount,null,null);
    }

    public void depositWithDateTime(int amount, String dt, String tm) throws IllegalArgumentException {
        if(amount<=0) throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE);
        balance+=amount;
        setBalanceDate(dt,tm);
    }
    public void withdraw(int amount) throws IllegalArgumentException {
        withdrawWithDateTime(amount,null,null);
    }

    public void withdrawWithDateTime(int amount, String dt, String tm) throws IllegalArgumentException{
        if(amount<=0) throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE);
        if(amount>balance) throw new IllegalArgumentException(AMOUNT_EXCEEDS_FUNDS);
        balance-=amount;
        setBalanceDate(dt,tm);
    }

    public void transferTo(int amount, Account toAccount) {
        withdraw(amount);
        toAccount.deposit(amount);
    }

    public AccountLine latestAccountLine() {
        return accountLine;
    }
}
