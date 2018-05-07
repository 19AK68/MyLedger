package com.example.andrey.myledger.Model;

public class AccountBook {



    private Long accountID;
    private String accountName;
    private String accountBalance;

    public AccountBook(){

    }

    public AccountBook (String accountName,String accountBalance){


        this.accountName=accountName;
        this.accountBalance=accountBalance;

    }


    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }
}
