package com.mysocial.flipr.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.repositories.DashboardRepo;

public class DashboardViewModel extends ViewModel {

    public MutableLiveData<String> message;
    public MutableLiveData<DetailsModel> profile;
    public DashboardRepo repo;

    public DashboardViewModel ()
    {
        super();
        repo = DashboardRepo.getInstance();
        message = new MutableLiveData<>();
        profile = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMessageUserObserver() {
        message = repo.getMessage();
        return message;
    }

    public MutableLiveData<DetailsModel> getProfileObserver() {
        profile = repo.getProfileDetails();
        return profile;
    }

    public void createNewUser(String username, Context context , String token ) {
        repo.get_profile(username.trim(), context , token);
    }

}
