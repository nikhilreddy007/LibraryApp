package com.example.nikhilreddy.libraryapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class signupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();

        final EditText collegeidInput = (EditText) findViewById(R.id.collegeidInput);
        final TextView collegeidwrong = (TextView) findViewById(R.id.collegeidwrong);
        final EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        final TextView usernamewrong = (TextView) findViewById(R.id.usernamewrong);
        final EditText createnewpasswordInput = (EditText) findViewById(R.id.createnewpasswordInput);
        final TextView newpasswordwrong = (TextView) findViewById(R.id.newpasswordwrong);
        final EditText confirmpasswordInput = (EditText) findViewById(R.id.confirmpasswordInput);
        final TextView confirmpasswordwrong = (TextView) findViewById(R.id.confirmpasswordwrong);
        Button mainsignupButton = (Button) findViewById(R.id._mainsignupbutton);

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
                    String newUserId = userDatabase.push().getKey();
                    User newUser = new User(collegeId, username, newpassword);
                    userDatabase.child(newUserId).setValue(newUser);
                    Toast.makeText(signupActivity.this,"Account successfully created!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
