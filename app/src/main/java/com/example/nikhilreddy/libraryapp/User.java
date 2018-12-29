package com.example.nikhilreddy.libraryapp;

/**
 * Created by Nikhil Reddy on 12/20/2018.
 */

public class User {

    private String collegeId, emailId, username, password;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String collegeId, String emailId, String username) {
        this.collegeId = collegeId;
        this.emailId = emailId;
        this.username = username;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getUsername() {
        return username;
    }
}
