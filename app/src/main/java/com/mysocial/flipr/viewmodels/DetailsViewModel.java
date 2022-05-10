package com.mysocial.flipr.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.repositories.DetailsRepo;

public class DetailsViewModel extends ViewModel {

    public MutableLiveData<String> message;
    public DetailsRepo repo;

    public DetailsViewModel() {
        super();
        repo=DetailsRepo.getInstance();
        message=new MutableLiveData<>();
    }
    public MutableLiveData<String> getMessageUserObserver(){
        message=repo.getMessage();
        return message;
    }
    public void createdetails(DetailsModel model, Context context){
//        Log.d("paramsDetailsViewModel",model.toString());
        repo.detailscreate(model,context);}
}
