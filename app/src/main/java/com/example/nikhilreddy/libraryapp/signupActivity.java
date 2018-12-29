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
import java.util.HashMap;
import java.util.Map;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EventListener;

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

//                if(collegeId not exists) {
//                    collegeidwrong.setText("ID does not exist");
//                    collegeidwrong.setVisibility(View.VISIBLE);
//                    everythingOk = false;
//                } else if(collegeId already there) {
//                    collegeidwrong.setText("ID does not exist");
//                    collegeidwrong.setVisibility(View.VISIBLE);
//                    everythingOk = false;
//                } else if(username is not valid) {
//                    usernamewrong.setVisibility(View.VISIBLE);
//                    everythingOk = false;
//                } else if(newpassword is not valid) {
//                    newpasswordwrong.setVisibility(View.VISIBLE);
//                    everythingOk = false;
//                } else if(confirmpassword does not match) {
//                    confirmpasswordwrong.setVisibility(View.VISIBLE);
//                    everythingOk = false;
//                }

                if(everythingOk) {
                    //String newUserId = userDatabase.push().getKey();
                    //User newUser = new User(collegeId, username, newpassword);
                    //userDatabase.child(newUserId).setValue(newUser);
                    registerUser();
                    finish();
                }
            }
        });
    }

    private void registerUser() {
        final String collegeid = collegeidInput.getText().toString().trim();
        final String email = emailidInput.getText().toString().trim();
        final String username = usernameInput.getText().toString().trim();
        final String password = createnewpasswordInput.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            collegeidInput.setError("Please enter username");
            collegeidInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("Please enter username");
            usernameInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailidInput.setError("Please enter your email");
            emailidInput.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailidInput.setError("Enter a valid email");
            emailidInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            createnewpasswordInput.setError("Enter a password");
            createnewpasswordInput.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("collegeid"),
                                        userJson.getString("email"),
                                        userJson.getString("username")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), homePageActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("collegeid", collegeid);
                params.put("emailid", email);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    
//    public void InsertSV() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.53/test/registeruser.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Toast.makeText(signupActivity.this, response.toString(), Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Toast.makeText(signupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            protected Map<String ,String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                String collegeid = collegeidInput.getText().toString();
//                String username  = usernameInput.getText().toString();
//                String email = "email@gmail.com";
//                String password = createnewpasswordInput.getText().toString();
//                params.put("collegeid", collegeid);
//                params.put("emailid", email);
//                params.put("username", username);
//                params.put("password", password);
//                return params;
//            }
//        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
}
