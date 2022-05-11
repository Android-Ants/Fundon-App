package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.LoanApplicationModel;
import com.mysocial.flipr.models.User;
import com.mysocial.flipr.repositories.LoanApplicationRepo;
import com.mysocial.flipr.repositories.SignUpRepo;

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

    public void createNewLoan(LoanApplicationModel model, Context context) {
        repo.loanapply(model, context);
    }
}
