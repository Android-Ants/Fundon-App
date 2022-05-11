package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.cibil.User;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.Loan;
import com.mysocial.flipr.repositories.LoanApplicationRepo;

public class LoanApplicationViewModel extends ViewModel {

    public MutableLiveData<String> loanApplyMessage;
    public MutableLiveData<String> cibilMessage;
    public MutableLiveData<User> cibil;
    public LoanApplicationRepo repo;

    public LoanApplicationViewModel() {
        super();
        repo = LoanApplicationRepo.getInstance();
        loanApplyMessage = new MutableLiveData<>();
        cibilMessage = new MutableLiveData<>();
        cibil = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMessageUserObserver() {
        loanApplyMessage = repo.getMessageLoanApply();
        return loanApplyMessage;
    }

    public MutableLiveData<String> getCibilMessageObserver() {
        cibilMessage = repo.getMessageCibil();
        return cibilMessage;
    }

    public MutableLiveData<User> getCibil() {
        cibil = repo.getCibil();
        return cibil ;
    }

    public void createNewLoan(Loan model, Context context) {
        repo.loanapply(model, context);
    }

    public void get_cibil (DetailsModel detailsModel , Context context)
    {
        repo.get_cibil_details(context , detailsModel);
    }
}
