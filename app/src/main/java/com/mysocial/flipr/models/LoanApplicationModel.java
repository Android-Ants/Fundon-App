package com.mysocial.flipr.models;

public class LoanApplicationModel {
    public String id,borrowerUserName,borrowerEmail,lenderUserName,lenderEmail,status,date;
    public int loanAmount,loanTenure,interestRate;
    boolean secured;

    public LoanApplicationModel(String id, String borrowerUserName, String borrowerEmail, String lenderUserName, String lenderEmail, String status, String date, int loanAmount, int loanTenure, int interestRate, boolean secured) {
        this.id = id;
        this.borrowerUserName = borrowerUserName;
        this.borrowerEmail = borrowerEmail;
        this.lenderUserName = lenderUserName;
        this.lenderEmail = lenderEmail;
        this.status = status;
        this.date = date;
        this.loanAmount = loanAmount;
        this.loanTenure = loanTenure;
        this.interestRate = interestRate;
        this.secured = secured;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowerUserName() {
        return borrowerUserName;
    }

    public void setBorrowerUserName(String borrowerUserName) {
        this.borrowerUserName = borrowerUserName;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getLenderUserName() {
        return lenderUserName;
    }

    public void setLenderUserName(String lenderUserName) {
        this.lenderUserName = lenderUserName;
    }

    public String getLenderEmail() {
        return lenderEmail;
    }

    public void setLenderEmail(String lenderEmail) {
        this.lenderEmail = lenderEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(int loanTenure) {
        this.loanTenure = loanTenure;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public boolean getSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }
}
