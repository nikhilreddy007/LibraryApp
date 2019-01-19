package com.example.nikhilreddy.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText collegeidInput;
    EditText passwordInput;
    Button loginButton;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, homePageActivity.class));
            return;
        }

        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton  = (Button) findViewById(R.id._signupbutton);
        collegeidInput = (EditText) findViewById(R.id.LoginCollegeidInput);
        passwordInput = (EditText) findViewById(R.id.LoginPasswordInput);

        final Intent signup_intent = new Intent(this, signupActivity.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                userLogin();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(signup_intent);
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String collegeid = collegeidInput.getText().toString();
        final String password = passwordInput.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(collegeid)) {
            collegeidInput.setError("Enter your username");
            collegeidInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Enter your password");
            passwordInput.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            JSONObject status = responseObject.getJSONObject("status");

                            //if no error in response
                            if (status.getInt("success") == 1) {
                                Log.d("msg", "success");
                                Toast.makeText(getApplicationContext(), status.getString("message"), Toast.LENGTH_SHORT).show();
                                //getting the user from the response
                                JSONObject userJson = responseObject.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("collegeid"),
                                        userJson.getString("emailid"),
                                        userJson.getString("username")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                finish();
                                startActivity(new Intent(getApplicationContext(), homePageActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), status.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag", "error");
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("collegeid", collegeid);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
