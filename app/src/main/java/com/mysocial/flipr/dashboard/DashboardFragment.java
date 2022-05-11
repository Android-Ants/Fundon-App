package com.mysocial.flipr.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysocial.flipr.LoanApplicationActivity;
import com.mysocial.flipr.R;
import com.mysocial.flipr.adapter.LoanAdapter;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.Loan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DashboardFragment extends Fragment {

    private DetailsModel detailsModel ;
    private final String APPLIED_URL = "https://codeq-flipr.herokuapp.com/api/loan/appliedLoans";
    private final String ACCEPTED_URL = "https://codeq-flipr.herokuapp.com/api/loan/acceptedLoans";
    private Context context ;
    private List<Loan> loans = new ArrayList<>();
    private String token ;
    private RequestQueue requestQueue ;
    private LoanAdapter adapter ;


    public DashboardFragment(DetailsModel detailsModel , Context context , String token ) {
        // Required empty public constructor
        this.detailsModel = detailsModel ;
        this.context = context ;
        this.token = token ;
        requestQueue = Volley.newRequestQueue(context) ;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_applied_loans();
        get_accepted_loans();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LoanAdapter(context , loans , "dashboard");
        recyclerView.setAdapter(adapter);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , LoanApplicationActivity.class);
                intent.putExtra("detail" , detailsModel);
                startActivity(intent);
            }
        });

        get_applied_loans();
        get_accepted_loans();

        return view ;
    }

    private void get_applied_loans ()
    {
        Map<String, String> params = new HashMap<>();
        params.put("borrowerUserName", detailsModel.getUserName() );
        params.put("borrowerEmail", detailsModel.getEmail() );
        JSONObject object = new JSONObject(params) ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APPLIED_URL,
                object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {

                    JSONArray array = response.getJSONArray("response");
                    for ( int i=0 ; i < array.length() ; i++ )
                    {
                        JSONObject object2 = array.getJSONObject(i);
                        Loan loan = new Loan();
                        loan.setId(object2.getString("id"));
                        loan.setBorrowerUserName(object2.getString("borrowerUserName"));
                        loan.setBorrowerEmail(object2.getString("borrowerEmail"));
                        loan.setLenderUserName(object2.getString("lenderUserName"));
                        loan.setLenderEmail(object2.getString("lenderEmail"));
                        loan.setStatus(object2.getString("status"));
                        loan.setDate(object2.getString("date"));
                        loan.setLoanAmount(object2.getInt("loanAmount"));
                        loan.setLoanTenure(object2.getInt("loanTenure"));
                        loan.setInterestRate(object2.getDouble("interestRate"));
                        loan.setSecured(object2.getBoolean("secured"));
                        loans.add(loan);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                params.put("Content-Type", "application/json; charset=utf-8");
                return params ;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    private void get_accepted_loans ()
    {
        Map<String, String> params = new HashMap<>();
        params.put("lenderUserName", detailsModel.getUserName() );
        params.put("lenderEmail", detailsModel.getEmail() );
        JSONObject object = new JSONObject(params) ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ACCEPTED_URL,
                object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {

                    JSONArray array = response.getJSONArray("response");
                    for ( int i=0 ; i < array.length() ; i++ )
                    {
                        JSONObject object2 = array.getJSONObject(i);
                        Loan loan = new Loan();
                        loan.setId(object2.getString("id"));
                        loan.setBorrowerUserName(object2.getString("borrowerUserName"));
                        loan.setBorrowerEmail(object2.getString("borrowerEmail"));
                        loan.setLenderUserName(object2.getString("lenderUserName"));
                        loan.setLenderEmail(object2.getString("lenderEmail"));
                        loan.setStatus(object2.getString("status"));
                        loan.setDate(object2.getString("date"));
                        loan.setLoanAmount(object2.getInt("loanAmount"));
                        loan.setLoanTenure(object2.getInt("loanTenure"));
                        loan.setInterestRate(object2.getDouble("interestRate"));
                        loan.setSecured(object2.getBoolean("secured"));
                        loans.add(loan);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                params.put("Content-Type", "application/json; charset=utf-8");
                return params ;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}