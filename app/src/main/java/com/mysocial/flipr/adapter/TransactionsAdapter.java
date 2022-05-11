package com.mysocial.flipr.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.mysocial.flipr.ProfileActivity;
import com.mysocial.flipr.R;
import com.mysocial.flipr.dashboard.DashboardActivity;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.Loan;
import com.mysocial.flipr.viewmodels.DashboardViewModel;
import com.mysocial.flipr.viewmodels.DetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private List<Loan> loans = new ArrayList<>();
    private Context context ;

    private String username;
    private SharedPreferences sharedPreferences;

    public TransactionsAdapter(List<Loan> loans, Context context) {
        this.loans = loans;
        this.context = context;

        sharedPreferences = context.getSharedPreferences("Fundon", MODE_PRIVATE);
        username = sharedPreferences.getString("userName", "");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.heading.setVisibility(View.VISIBLE);
        holder.otherUserName.setVisibility(View.VISIBLE);
        holder.otherEmail.setVisibility(View.VISIBLE);

        holder.loanId.setText(String.valueOf(loans.get(position).getId()));
        holder.loanAmount.setText(String.valueOf(loans.get(position).getLoanAmount()));
        holder.loanDetail.setText( "at " +String.valueOf(loans.get(position).getInterestRate()) + "% for " +
                String.valueOf(loans.get(position).getLoanTenure()) + " months");
        String status = String.valueOf(loans.get(position).getStatus());
        if(status.equalsIgnoreCase("applied")){
            holder.status.setText("Applied");
            holder.heading.setVisibility(View.GONE);
            holder.otherUserName.setVisibility(View.GONE);
            holder.otherEmail.setVisibility(View.GONE);
            holder.action.setText("Modify");

        }else if(status.equalsIgnoreCase("accepted")){
            holder.status.setText("On Going");
            holder.statusColor.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            holder.loanAmount.setTextColor(context.getResources().getColor(R.color.red));
            String lender = loans.get(position).getLenderUserName();
            String borrower = loans.get(position).getBorrowerUserName();

            if(lender.equalsIgnoreCase(username)){
                holder.action.setText("Remind");
                holder.loanAmount.setTextColor(context.getResources().getColor(R.color.green));
                holder.heading.setText("Borrower Details");
                holder.otherUserName.setText(loans.get(position).getBorrowerUserName());
                holder.otherEmail.setText(loans.get(position).getBorrowerEmail());

            }else if(borrower.equalsIgnoreCase(username)){
                holder.action.setText("Pay");
                holder.loanAmount.setTextColor(context.getResources().getColor(R.color.red));
                holder.heading.setText("Borrowed From");
                holder.otherUserName.setText(loans.get(position).getLenderUserName());
                holder.otherEmail.setText(loans.get(position).getLenderEmail());
            }


        }else if(status.equalsIgnoreCase("finished")){

            holder.action.setBackgroundColor(context.getResources().getColor(R.color.primary_variant));
            holder.action.setText("Done");
            holder.status.setText("Completed");

        }
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView loanId , loanAmount ,loanDetail , otherUserName , otherEmail, status, heading;
        private Button action ;
        private CardView statusColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            loanId = itemView.findViewById(R.id.loanID);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            loanDetail = itemView.findViewById(R.id.loandetail);
            otherUserName = itemView.findViewById(R.id.requestUsername);
            otherEmail = itemView.findViewById(R.id.requestEmail);
            status = itemView.findViewById(R.id.status_text);
            heading = itemView.findViewById(R.id.borrower_lender_head);
            action = itemView.findViewById(R.id.requestAccept);
            statusColor = itemView.findViewById(R.id.status_color);
        }
    }
}
