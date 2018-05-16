package com.example.andrey.myledger.Model;

public class Cost {

    private Long costID;
    private String costDate;
    private String costTime;
    private String costCategoty;
    private String costSum;
    private String costAccount;
    private String costComment;

    public Cost(){

    }

    public Cost(String costDate, String costTime,String costCategoty,String costSum, String costAccount, String costComment){

        this.costDate = costDate;
        this.costTime = costTime;
        this.costCategoty = costCategoty;
        this.costSum = costSum;
        this.costAccount = costAccount;
        this.costComment = costComment;

    }

    public Long getCostID() {
        return costID;
    }

    public void setCostID(Long costID) {
        this.costID = costID;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getCostCategoty() {
        return costCategoty;
    }

    public void setCostCategoty(String costCategoty) {
        this.costCategoty = costCategoty;
    }

    public String getCostSum() {
        return costSum;
    }

    public void setCostSum(String costSum) {
        this.costSum = costSum;
    }

    public String getCostAccount() {
        return costAccount;
    }

    public void setCostAccount(String costAccount) {
        this.costAccount = costAccount;
    }

    public String getCostComment() {
        return costComment;
    }

    public void setCostComment(String costComment) {
        this.costComment = costComment;
    }


}
