package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.User;
import com.mysocial.flipr.repositories.SignUpRepo;

public class SignUpViewModel extends ViewModel {

    public MutableLiveData<String> message;
    public MutableLiveData<String> token;
    public SignUpRepo repo;

    public SignUpViewModel() {
        super();
        repo = SignUpRepo.getInstance();
        message = new MutableLiveData<>();
        token = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMessageUserObserver() {
        message = repo.getMessage();
        return message;
    }

    public MutableLiveData<String> getTokenUserObserver() {
        token = repo.getToken();
        return token;
    }

    public void createNewUser(User user, Context context) {
        repo.usercreate(user, context);
    }
}
