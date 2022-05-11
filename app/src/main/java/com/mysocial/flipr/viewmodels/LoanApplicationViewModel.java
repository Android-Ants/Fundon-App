package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.Loan;
import com.mysocial.flipr.repositories.LoanApplicationRepo;

public class LoanApplicationViewModel extends ViewModel {

    public MutableLiveData<String> message;
    public LoanApplicationRepo repo;

    public LoanApplicationViewModel() {
        super();
        repo = LoanApplicationRepo.getInstance();
        message = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMessageUserObserver() {
        message = repo.getMessage();
        return message;
    }

    public void createNewLoan(Loan model, Context context) {
        repo.loanapply(model, context);
    }
}
