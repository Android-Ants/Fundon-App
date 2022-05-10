package com.mysocial.flipr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysocial.flipr.R;
import com.mysocial.flipr.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoanViewHolder> {

    private List<Loan> loans = new ArrayList<>();
    private Context context ;
    private On_Click onClick ;
    private String string ;

    public LoanAdapter ( Context context , List<Loan> loans , On_Click onClick  , String string )
    {
        this.context = context ;
        this.loans = loans ;
        this.onClick = onClick ;
        this.string = string ;
    }

    public LoanAdapter ( Context context , List<Loan> loans , String string  )
    {
        this.context = context ;
        this.loans = loans ;
        this.string = string ;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.loan_item, parent ,false);
        return new LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {

        holder.loanId.setText(String.valueOf(loans.get(position).getId()));
        holder.loanAmount.setText(String.valueOf(loans.get(position).getLoanAmount()));
        holder.loanDetail.setText( "at " +String.valueOf(loans.get(position).getInterestRate()) + "% for " +
                String.valueOf(loans.get(position).getLoanTenure()) + " months");
        holder.borrowerUserName.setText(loans.get(position).getBorrowerUserName());
        holder.borrowerEmail.setText(loans.get(position).getBorrowerEmail());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.accept_loan(holder.getAdapterPosition());
            }
        });

        if ( loans.get(position).getStatus().equalsIgnoreCase("accepted") )
        {
            holder.textView.setText("On Going");
        }else
        {
            holder.textView.setText("applied");
        }

        if ( string.equalsIgnoreCase("dashboard") )
        {
            holder.linearLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.linearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public class LoanViewHolder extends RecyclerView.ViewHolder{

        private TextView loanId , loanAmount ,loanDetail , borrowerUserName , borrowerEmail  , textView;
        private Button accept ;
        private LinearLayout linearLayout ;

        public LoanViewHolder(@NonNull View itemView) {
            super(itemView);
            loanId = itemView.findViewById(R.id.loanID);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            loanDetail = itemView.findViewById(R.id.loandetail);
            borrowerUserName = itemView.findViewById(R.id.requestUsername);
            borrowerEmail = itemView.findViewById(R.id.requestEmail);
            accept = itemView.findViewById(R.id.requestAccept);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public interface On_Click {
        void accept_loan (int a) ;
    }

}
