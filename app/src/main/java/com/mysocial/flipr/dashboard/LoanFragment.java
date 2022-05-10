package com.mysocial.flipr.dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mysocial.flipr.R;
import com.mysocial.flipr.adapter.LoanAdapter;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.Loan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class LoanFragment extends Fragment implements LoanAdapter.On_Click {

    private RequestQueue requestQueue ;
    private Context context ;
    private final String URL = "https://codeq-flipr.herokuapp.com/api/loan/allLoans";
    private final String ACCEPT_URL = "https://codeq-flipr.herokuapp.com/api/loan/accept";
    private String token ;
    private DetailsModel detailsModel ;
    private final String TAG = "LoanFragment";
    private List<Loan> loans = new ArrayList<>();
    private Boolean secured = true ;
    private LoanAdapter adapter ;


    public LoanFragment ()
    {

    }

    public LoanFragment( Context context , String token , DetailsModel detailsModel ) {
        this.context = context ;
        this.token = token ;
        this.detailsModel = detailsModel ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan, container, false);
        get_all_loans();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LoanAdapter(context , loans , this::accept_loan , "loan");
        recyclerView.setAdapter(adapter);


        return view ;
    }

    private void get_all_loans ()
    {
        requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG , response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("response");

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                params.put("Content-Type", "application/json; charset=utf-8");
                return params ;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void accept_loan(int a) {

        Loan loan = loans.get(a);
        show_alert(loan);
    }

    private void show_alert( Loan loan )
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Confirmation Message");
        alert.setMessage("Are you sure you want to accept this loan ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                set_secured(loan);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    private void set_secured( Loan loan )
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Security");
        alert.setMessage("Is collateral provided to you ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                secured = true ;
                call_api(loan);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                secured = false ;
                call_api(loan);
            }
        });
        alert.show() ;
    }

    private void call_api ( Loan loan )
    {
        Map<String, String> params = new HashMap<>();
        params.put("id",loan.getId() );
        params.put("borrowerUserName", loan.getBorrowerUserName() );
        params.put("borrowerEmail", loan.getBorrowerEmail() );
        params.put("lenderUserName", detailsModel.getUserName() );
        params.put("lenderEmail", detailsModel.getEmail() );
        params.put("status" , "accepted");
        params.put("date" , new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        JSONObject object = new JSONObject(params) ;

        try {
            object.put("secured" , secured);
            object.put("loanAmount" , loan.getLoanAmount());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ACCEPT_URL,
                object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();

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