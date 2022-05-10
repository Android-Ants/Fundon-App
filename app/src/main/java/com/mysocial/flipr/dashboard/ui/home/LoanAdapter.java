package com.mysocial.flipr.dashboard.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysocial.flipr.R;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {
    Context context;

    public LoanAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoanAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView loanID;
        TextView loanAmount;
        TextView detail;
        TextView userName;
        TextView email;
        Button acceptButton;

        public ViewHolder(View view) {
            super(view);
            loanID = view.findViewById(R.id.loanID);
            loanAmount = view.findViewById(R.id.loanAmount);
            detail = view.findViewById(R.id.loandetail);
            userName = view.findViewById(R.id.requestUsername);
            email = view.findViewById(R.id.requestEmail);
            acceptButton = view.findViewById(R.id.requestAccept);
        }
    }
}
