package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.User;
import com.mysocial.flipr.repositories.LoginRepo;

public class SignInViewModel extends ViewModel {

    public MutableLiveData<User> user;
    public MutableLiveData<String>  token;
    public LoginRepo repo;

    public SignInViewModel() {
        super();
        repo = LoginRepo.getInstance();
        user = new MutableLiveData<>();
        token=new MutableLiveData<>();
    }

    public MutableLiveData<User> getUserObserver() {
        user = repo.getUser() ;
        return user;
    }
    public MutableLiveData<String> getTokenObserver() {
        token = repo.getToken() ;
        return token;
    }

    public void signInUser(User user, Context context) {
        repo.userSignIn(user,context);
    }
}