package com.mysocial.flipr;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.mysocial.flipr.authentication.SignUpActivity;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.LoanApplicationModel;
import com.mysocial.flipr.models.User;
import com.mysocial.flipr.viewmodels.LoanApplicationViewModel;
import com.mysocial.flipr.viewmodels.SignInViewModel;
import com.mysocial.flipr.viewmodels.SignUpViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoanApplicationActivity extends AppCompatActivity {
    LoanApplicationViewModel viewModel;
    TextInputEditText loanamount,loantenure;
    TextView loaninterest;
    Button apply;
    DetailsModel detailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        loanamount=findViewById(R.id.loanamount);
        loantenure=findViewById(R.id.loantenure);
        loaninterest=findViewById(R.id.loaninterest);
        apply=findViewById(R.id.apply);
        
        initViewModel();
        detailsModel=new DetailsModel();
        detailsModel = (DetailsModel) getIntent().getSerializableExtra("detail");
        
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createloan();
            }
        });
    }

    private void createloan() {
        LoanApplicationModel model = new LoanApplicationModel(randomid(),detailsModel.getUserName(),detailsModel.getEmail(),
                "__","__","applied","a",Integer.valueOf(loanamount.getText().toString())
                ,Integer.valueOf(loantenure.getText().toString()),5,true);
        viewModel.createNewLoan(model, LoanApplicationActivity.this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoanApplicationViewModel.class);
        viewModel.getMessageUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if ( s.equalsIgnoreCase("Successfully Applied for Loan") )
                {
                    Toast.makeText(LoanApplicationActivity.this, "Successfully Applied for Loan", Toast.LENGTH_SHORT).show();
                    loanamount.getText().clear();
                    loantenure.getText().clear();
                }
            }
        });

    }
    private String randomid(){
        return String.valueOf(System.currentTimeMillis());
    }
}
