package com.mysocial.flipr.cibil;

import static java.lang.Math.abs;

public class Cibil {

    public Cibil() {
    }

    void setProfile(User user){
        int cibil=getCIBIL(user);
        user.setCIBIL(cibil);
        int loanpaid=user.getAmountPaid();
        int maxLoanAmount=0;
        if (cibil==-1) maxLoanAmount=1000;
        else maxLoanAmount=cibil*loanpaid/700;
        user.setMaxCredit(maxLoanAmount);
    }

    int getCIBIL(User user){
        int score=300;
        if (pastPerformance(user)!=-1)
            score+=4*30*pastPerformance(user)/100;
        else return -1;
        if (creditMixDuration(user)!=-1)
            score+=4*25*creditMixDuration(user)/100;
        else return -1;
        if (leverage(user)!=-1)
            score+=4*25*leverage(user)/100;
        else return -1;
        if (otherFactor(user)!=-1)
            score+=4*20*otherFactor(user)/100;
        else return -1;
        return score;
    }


    int pastPerformance(User user){
        int score=0;
        int total=user.getLoanCount();
        int pending=user.getCurrentLoanCount();
        if (total==0){
            return -1;
        }
        score+=(40*(total-pending))/total;
        int overDue=user.getFinishedOverDue();
        if (total==pending){
            score+=0;
        }
        else{
            score+=(15*(total-pending-overDue)/(total-pending));
        }
        int currOverDue=user.getCurrOverdue();
        if (pending==0) score+=35;
        else score+=(35*(pending-currOverDue)/(pending));
        if (pending>(total-pending)) {
            score+=(10*(overDue+currOverDue));
        }
        else {
            score+=10;
        }
        return score;
    }

    int creditMixDuration(User user){
        int score=0;
        int unSecLoan=user.getUnsecredLoanCount();
        int secLoan=user.getSecuredLoanCount();
        int total=unSecLoan+secLoan;
        if (total==0){
            return -1;
        }
        int ratio1=(100*(abs(70-100*secLoan/total))/70)/2;
        int ratio2=(100*(abs(30-100*unSecLoan/total))/30)/2;
        score+=35*(ratio1+ratio2)/100;
        int loanCountThisYear=user.getLoanCountYear();
        if (loanCountThisYear<4){
            score+=40;
        }
        else{
            score+=40*3/loanCountThisYear;
        }
        score+=25*(user.getFinishedOverDue()+user.getCurrOverdue())/total;
        return score;
    }

    int leverage(User user){
        int score=0;
        int presentLoneAmount=user.getPresentLoanAmount();
        int userCreditLimit=user.getMaxCredit();
        if (userCreditLimit==0) return -1;
        if (userCreditLimit>presentLoneAmount){
            score+=70*(userCreditLimit-presentLoneAmount)/userCreditLimit;
        }
        int amountLoaned=user.getTotalLoanCredited();
        int amountPaid= user.getAmountPaid();

        if (amountLoaned==0) return -1;
        else score+=30*amountPaid/(amountLoaned);
        return score;
    }

    int otherFactor(User user){
        int score=0;
        int disapproved=user.getDisapprovedCount();
        int total=user.getLoanCount();
        if (total==0) return -1;
        score+=60*(total-disapproved)/total;
        int amountpaid=user.getAmountPaid();
        score+=40*amountpaid/(amountpaid+user.getTotalLoanCredited());
        return score;
    }

}
