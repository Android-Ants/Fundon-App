package com.mysocial.flipr.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mysocial.flipr.ProfileActivity;
import com.mysocial.flipr.R;
import com.mysocial.flipr.models.DetailsModel;

import com.mysocial.flipr.models.User;

import java.io.Serializable;

public class ProfileFragment extends Fragment {

    DetailsModel detailsModel;
    public ProfileFragment(DetailsModel detailsModel) {
      this.detailsModel=detailsModel;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view=getView();
        if(view!=null)
        {
            TextView name=view.findViewById(R.id.disp_name);
            TextView username=view.findViewById(R.id.disp_username);
            TextView email=view.findViewById(R.id.disp_email);
            TextView mobile=view.findViewById(R.id.disp_mobile);
            TextView occupation=view.findViewById(R.id.disp_occupation);
            TextView accno=view.findViewById(R.id.disp_bankacc);
            TextView ifsc=view.findViewById(R.id.disp_ifsc);
            TextView aadhar=view.findViewById(R.id.disp_aadhar);
            TextView pan=view.findViewById(R.id.disp_pan);
            TextView adress=view.findViewById(R.id.disp_address);

            name.setText(detailsModel.getName());
            username.setText(detailsModel.getUserName());
            email.setText(detailsModel.getEmail());
            mobile.setText(detailsModel.getMobile());
            occupation.setText(detailsModel.getOccupation());
            accno.setText(detailsModel.getBankAccountNo());
            ifsc.setText(detailsModel.getIfscCode());
            aadhar.setText(detailsModel.getAadhaarNo());
            pan.setText(detailsModel.getPanNo());
            adress.setText(detailsModel.getAddress());

            Button edit=view.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getContext(), ProfileActivity.class);
                    Log.d("abba",detailsModel.toString());
                    intent.putExtra("abba",(Serializable) detailsModel);
                    startActivity(intent);
                }
            });

        }
    }
}