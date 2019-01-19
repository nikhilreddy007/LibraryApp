package com.example.nikhilreddy.libraryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class signupActivity extends AppCompatActivity {

    EditText collegeidInput;
    TextView collegeidwrong;
    EditText emailidInput;
    TextView emailidwrong;
    EditText usernameInput;
    TextView usernamewrong;
    EditText createnewpasswordInput;
    TextView newpasswordwrong;
    EditText confirmpasswordInput;
    TextView confirmpasswordwrong;
    Button mainsignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();
        collegeidInput = (EditText) findViewById(R.id.collegeidInput);
        collegeidwrong = (TextView) findViewById(R.id.collegeidwrong);
        emailidInput = (EditText) findViewById(R.id.emailidInput);
        emailidwrong = (TextView) findViewById(R.id.emailidwrong);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        usernamewrong = (TextView) findViewById(R.id.usernamewrong);
        createnewpasswordInput = (EditText) findViewById(R.id.createnewpasswordInput);
        newpasswordwrong = (TextView) findViewById(R.id.newpasswordwrong);
        confirmpasswordInput = (EditText) findViewById(R.id.confirmpasswordInput);
        confirmpasswordwrong = (TextView) findViewById(R.id.confirmpasswordwrong);
        mainsignupButton = (Button) findViewById(R.id._mainsignupbutton);

        mainsignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean everythingOk = true;

                collegeidwrong.setVisibility(View.GONE);
                usernamewrong.setVisibility(View.GONE);
                newpasswordwrong.setVisibility(View.GONE);
                confirmpasswordwrong.setVisibility(View.GONE);

                String collegeId = collegeidInput.getText().toString();
                String username = usernameInput.getText().toString();
                String newpassword = createnewpasswordInput.getText().toString();
                String confirmpassword = confirmpasswordInput.getText().toString();

                if(everythingOk) {
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        final String collegeid = collegeidInput.getText().toString().trim();
        final String email = emailidInput.getText().toString().trim();
        final String username = usernameInput.getText().toString().trim();
        final String newpassword = createnewpasswordInput.getText().toString().trim();
        final String confirmpassword = confirmpasswordInput.getText().toString().trim();

        if (TextUtils.isEmpty(collegeid)) {
            collegeidInput.setError("Enter college id");
            collegeidInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailidInput.setError("Enter your email");
            emailidInput.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailidInput.setError("Enter a valid email");
            emailidInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("Enter a username");
            usernameInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newpassword)) {
            createnewpasswordInput.setError("Enter a password");
            createnewpasswordInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            confirmpasswordInput.setError("Enter password again");
            confirmpasswordInput.requestFocus();
            return;
        }

        if(!newpassword.equals(confirmpassword)) {
            confirmpasswordInput.setError("Passwords do not match");
            confirmpasswordInput.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            JSONObject status = responseObject.getJSONObject("status");
                            //if no error in response
                            if (status.getInt("success") == 1) {
                                Log.d("msg", "success");
                                Toast.makeText(getApplicationContext(), status.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Log.d("msg", "not success");
                                Toast.makeText(getApplicationContext(), status.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("msg", "error");
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("collegeid", collegeid);
                params.put("emailid", email);
                params.put("username", username);
                params.put("password", newpassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
