package com.mysocial.flipr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.mysocial.flipr.cibil.Cibil;
import com.mysocial.flipr.cibil.User;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.Loan;
import com.mysocial.flipr.viewmodels.LoanApplicationViewModel;

public class LoanApplicationActivity extends AppCompatActivity {
    LoanApplicationViewModel viewModel;
    TextInputEditText loanamount, loantenure, loaninterest;
    Button apply;
    DetailsModel detailsModel;
    TextView credit_score, credit_limit;
    ProgressDialog progressDialog;
    User modelUser;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");

        detailsModel = new DetailsModel();
        detailsModel = (DetailsModel) getIntent().getSerializableExtra("detail");

        initViewModel();

        get_cibil();

        credit_score = findViewById(R.id.textView5);
        credit_limit = findViewById(R.id.credit_limit);

        loanamount = findViewById(R.id.loanamount);
        loantenure = findViewById(R.id.loantenure);
        loaninterest = findViewById(R.id.loaninterest);
        checkBox = findViewById(R.id.checkBox);
        apply = findViewById(R.id.apply);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createloan();
            }
        });
    }

    private void createloan() {

        if (check_amount() && checkBox.isChecked()) {
            progressDialog.show();
            Loan model = new Loan(randomid(), detailsModel.getUserName(), detailsModel.getEmail(),
                    "__", "__", "applied", "a", Integer.valueOf(loanamount.getText().toString())
                    , Integer.valueOf(loantenure.getText().toString()), Integer.valueOf(loaninterest.getText().toString()), true);
            viewModel.createNewLoan(model, LoanApplicationActivity.this);
        }
    }

    private Boolean check_amount() {
        if (Integer.valueOf(loanamount.getText().toString()) > modelUser.getMaxCredit()) {
            loanamount.setError("Should be Less than Max Credit");
            return false;
        }
        if (!checkBox.isChecked()) {
            checkBox.setError("Accept T & C");
            return false;
        }

        return true;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoanApplicationViewModel.class);
        viewModel.getMessageUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("Successfully Applied for Loan")) {
                    progressDialog.dismiss();
                    Toast.makeText(LoanApplicationActivity.this, "Successfully Applied for Loan", Toast.LENGTH_SHORT).show();
                    loanamount.getText().clear();
                    loantenure.getText().clear();
                    loaninterest.getText().clear();
                    checkBox.setChecked(false);
                }
            }
        });

        viewModel.getCibilMessageObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("Cibil Details")) {
                    viewModel.getCibil().observe(LoanApplicationActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            Cibil cibil = new Cibil();
                            modelUser = cibil.setProfile(user);
                            if ( modelUser.getCIBIL() == -1 )
                            credit_score.setText(String.valueOf("Not Applicable"));

                            else
                            credit_score.setText(String.valueOf(modelUser.getCIBIL()));

                            credit_limit.setText("Rs. " + String.valueOf(modelUser.getMaxCredit()));
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    modelUser = new User() ;
                    credit_score.setText(String.valueOf("Not Applicable"));
                    credit_limit.setText("Rs. 1000" );
                    Toast.makeText(LoanApplicationActivity.this, "No Cibil Exists", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

    private String randomid() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void get_cibil() {
        progressDialog.show();
        viewModel.get_cibil(detailsModel, this);
    }
}
