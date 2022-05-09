package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.OTPModel;
import com.mysocial.flipr.repositories.OTPRepo;

public class OTPViewModel extends ViewModel {
    public MutableLiveData<String> message;
    public MutableLiveData<String> user;
    public OTPRepo repo;

    public OTPViewModel() {
        super();
        repo = OTPRepo.getInstance();
        message = new MutableLiveData<>();
        user = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMessageObserver() {
        message = repo.getMessage();
        return message;
    }

    public MutableLiveData<String> getUserObserver() {
        user = repo.getUser();
        return user;
    }

    public void verifyUser(OTPModel otpModel, Context context) {
        repo.VerifyUser(otpModel, context);
    }
}
