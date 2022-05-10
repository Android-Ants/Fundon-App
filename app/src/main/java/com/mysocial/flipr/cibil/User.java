package com.mysocial.flipr.cibil;

public class User {

    private int loanCount;
    private int currentLoanCount;
    private int finishedOverDue;
    private int currOverdue;
    private int unsecredLoanCount;
    private int securedLoanCount;
    private int loanCountYear;
    private int paymentExccedingDeadline;
    private int disapprovedCount;
    private int libility;
    private int presentLoanAmount;
    private int amountPaid;
    private int maxCredit;
    private int CIBIL;

    public int getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount;
    }

    public int getCurrentLoanCount() {
        return currentLoanCount;
    }

    public void setCurrentLoanCount(int currentLoanCount) {
        this.currentLoanCount = currentLoanCount;
    }

    public int getFinishedOverDue() {
        return finishedOverDue;
    }

    public void setFinishedOverDue(int finishedOverDue) {
        this.finishedOverDue = finishedOverDue;
    }

    public int getCurrOverdue() {
        return currOverdue;
    }

    public void setCurrOverdue(int currOverdue) {
        this.currOverdue = currOverdue;
    }

    public int getUnsecredLoanCount() {
        return unsecredLoanCount;
    }

    public void setUnsecredLoanCount(int unsecredLoanCount) {
        this.unsecredLoanCount = unsecredLoanCount;
    }

    public int getSecuredLoanCount() {
        return securedLoanCount;
    }

    public void setSecuredLoanCount(int securedLoanCount) {
        this.securedLoanCount = securedLoanCount;
    }

    public int getLoanCountYear() {
        return loanCountYear;
    }

    public void setLoanCountYear(int loanCountYear) {
        this.loanCountYear = loanCountYear;
    }

    public int getPaymentExccedingDeadline() {
        return paymentExccedingDeadline;
    }

    public void setPaymentExccedingDeadline(int paymentExccedingDeadline) {
        this.paymentExccedingDeadline = paymentExccedingDeadline;
    }

    public int getDisapprovedCount() {
        return disapprovedCount;
    }

    public void setDisapprovedCount(int disapprovedCount) {
        this.disapprovedCount = disapprovedCount;
    }

    public int getLibility() {
        return libility;
    }

    public void setLibility(int libility) {
        this.libility = libility;
    }

    public int getPresentLoanAmount() {
        return presentLoanAmount;
    }

    public void setPresentLoanAmount(int presentLoanAmount) {
        this.presentLoanAmount = presentLoanAmount;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(int maxCredit) {
        this.maxCredit = maxCredit;
    }

    public void setCIBIL(int cibil) {
        this.CIBIL = cibil;
    }

    public int getCIBIL() {
        return CIBIL;
    }

}
