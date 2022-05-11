package com.mysocial.flipr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mysocial.flipr.dashboard.DashboardActivity;
import com.mysocial.flipr.databinding.ActivityDocumentsBinding;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.viewmodels.DetailsViewModel;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    DetailsViewModel viewModel;
    ActivityDocumentsBinding binding;

    Uri aadharFile, panFile, banKDetailsFile;
    String aadharFileLink, panFileLink, banKDetailsFileLink;
    boolean isVerified=false;
    DetailsModel detailsmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        detailsmodel=(DetailsModel) getIntent().getSerializableExtra("abba");

        initViewModel();

        HashMap<String, String> params = (HashMap<String, String>) getIntent().getSerializableExtra("2nd page");

        binding.aadharImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

        binding.panImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 2);
            }
        });

        binding.bankDetailImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 3);
            }
        });

        binding.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDetails(params);

                Intent intent =new Intent(DetailsActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
    ProgressDialog dialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.setCancelable(false);

            Uri imageuri = data.getData();
            File file = new File(imageuri.getPath());
            String fileName = file.getName();

            switch (requestCode){
                case 1:
                    aadharFile = imageuri;
                    binding.aadharImageUploadText.setText(fileName+".pdf");
                    break;
                case 2:
                    panFile = imageuri;
                    binding.panImageUploadText.setText(fileName+".pdf");
                    break;
                case 3:
                    banKDetailsFile = imageuri;
                    binding.bankDetailImageUploadText.setText(fileName+".pdf");
                    break;
            }

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            filepath.putFile(imageuri).continueWithTask((Continuation) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Drawable img = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_check ,null);

                        dialog.dismiss();
                        Uri uri = task.getResult();
                        switch (requestCode){
                            case 1:
                                aadharFileLink = task.getResult().toString();
                                binding.aadharImageUploadText.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);
                                break;
                            case 2:
                                panFileLink = task.getResult().toString();
                                binding.panImageUploadText.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                                break;
                            case 3:
                                banKDetailsFileLink = task.getResult().toString();
                                binding.bankDetailImageUploadText.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                                break;
                        }
                        Log.d("files","Sucess");
                        Toast.makeText(DetailsActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Log.d("files","fail");
                        Toast.makeText(DetailsActivity.this, "Uploaded Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        viewModel.getMessageUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if ( s.equalsIgnoreCase("Profile saved Successfully") )
                {
                    Intent intent = new Intent(DetailsActivity.this , DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void addDetails(Map<String, String> params) {
        DetailsModel model = new DetailsModel(
                params.get("email"),params.get("userName"),
                params.get("name"), params.get("mobile"),
                params.get("address"), params.get("occupation"),
                params.get("aadhaarNo"),params.get("panNo"),
                params.get("bankAccountNo"),params.get("ifscCode"),
                params.get("imgUrl"), aadharFileLink,
                panFileLink,banKDetailsFileLink, isVerified);

        viewModel.createdetails(model, DetailsActivity.this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(DetailsActivity.this,BankDetailsActivity.class);
        intent.putExtra("abba",(Serializable)detailsmodel);
        startActivity(intent);
        finish();
    }
}