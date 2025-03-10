package org.xpdojo.bank;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
    public static final String AMOUNT_MUST_BE_POSITIVE = "amount must be positive";
    public static final String AMOUNT_EXCEEDS_FUNDS = "not enough funds to withdraw amount";
    public static final String TYPE_DEPOSIT = "D";
    public static final String TYPE_WITHDRAW = "W";
    public static final String TYPE_OPEN = "O";

    private int balance = 0;
    private final ArrayList<AccountLine> accountLines=new ArrayList<>();

    public static Account emptyAccount() {
        return emptyAccountWithDateTime(null,null);
    }

    public static Account emptyAccountWithDateTime(String dt,String tm) {
        Account account = new Account();
        account.setBalanceDate(TYPE_OPEN,0,dt,tm); //also ensures at least one account line
        return account;
    }

    public int balance() {
        return balance;
    }
    public String balanceDate() { return latestAccountLine().date; }
    public String balanceTime() { return latestAccountLine().time; }

    private void setBalanceDate(String type,int amount,String dt, String tm) {
        if(dt==null || tm==null || dt.length()==0 || tm.length()==0){
            Calendar cal=Calendar.getInstance();
            dt=DateTimeHelper.getDate(cal);
            tm=DateTimeHelper.getTime(cal);
        }
        accountLines.add(new AccountLine(type,balance,amount,dt,tm));
    }

    public void deposit(int amount) throws IllegalArgumentException {
        depositWithDateTime(amount,null,null);
    }

    public void depositWithDateTime(int amount, String dt, String tm) throws IllegalArgumentException {
        if(amount<=0) throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE);
        balance+=amount;
        setBalanceDate(TYPE_DEPOSIT,amount,dt,tm);
    }
    public void withdraw(int amount) throws IllegalArgumentException {
        withdrawWithDateTime(amount,null,null);
    }

    public void withdrawWithDateTime(int amount, String dt, String tm) throws IllegalArgumentException{
        if(amount<=0) throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE);
        if(amount>balance) throw new IllegalArgumentException(AMOUNT_EXCEEDS_FUNDS);
        balance-=amount;
        setBalanceDate(TYPE_WITHDRAW,amount,dt,tm);
    }

    public void transferTo(int amount, Account toAccount) {
        withdraw(amount);
        toAccount.deposit(amount);
    }

    public AccountLine latestAccountLine() {
        return accountLines.get(accountLines.size()-1);
    }

    public List<AccountLine> statement() {
        return new ArrayList<>(accountLines);
    }

    public List<AccountLine> statement(String typeFilter, String dateFrom, String timeFrom, String dateTo, String timeTo) {
        return accountLines.stream().filter(
                x -> (typeFilter == null || typeFilter.length() == 0 || x.type.equals(typeFilter))
                // from date onwards
                && (dateFrom ==null || dateFrom.length()==0 || x.date.compareTo(dateFrom)>0 || (
                    x.date.equals(dateFrom)
                        && (timeFrom==null || timeFrom.length()==0 || x.time.compareTo(timeFrom)>=0)
                ))
                //upto to date
                && (dateTo ==null || dateTo.length()==0 || x.date.compareTo(dateTo)<0 || (
                x.date.equals(dateTo)
                        && (timeTo==null || timeTo.length()==0 || x.time.compareTo(timeTo)<=0)
            ))
        ).collect(Collectors.toList());
    }
}
