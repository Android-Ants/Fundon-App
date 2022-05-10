package com.mysocial.flipr.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mysocial.flipr.R;
import com.mysocial.flipr.databinding.ActivityUploadDocumentsBinding;

import java.io.File;

public class UploadDocumentsActivity extends AppCompatActivity {

    ActivityUploadDocumentsBinding binding;

    Uri aadharFile, panFile, banKDetailsFile;
    String aadharFileLink, panFileLink, banKDetailsFileLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                        Toast.makeText(UploadDocumentsActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(UploadDocumentsActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

}