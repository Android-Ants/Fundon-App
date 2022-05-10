package com.mysocial.flipr.cibil;

public class User {

    private int loanCount=0;
    private int currentLoanCount=0;
    private int finishedOverDue=0;
    private int currOverdue=0;
    private int unsecredLoanCount=0;
    private int securedLoanCount=0;
    private int loanCountYear=0;
    private int disapprovedCount=0;
    private int totalLoanCredited=0;
    private int presentLoanAmount=0;
    private int amountPaid=0;
    private int maxCredit=1000;
    private int CIBIL=-1;

    public int getTotalLoanCredited() {
        return totalLoanCredited;
    }

    public void setTotalLoanCredited(int totalLoanCredited) {
        this.totalLoanCredited = totalLoanCredited;
    }

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

    public int getDisapprovedCount() {
        return disapprovedCount;
    }

    public void setDisapprovedCount(int disapprovedCount) {
        this.disapprovedCount = disapprovedCount;
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
