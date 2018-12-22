package com.example.nikhilreddy.libraryapp;

/**
 * Created by Nikhil Reddy on 12/20/2018.
 */

public class User {
    public String collegeId;
    public String username;
    public String password;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String collegeId, String username, String password) {
        this.collegeId = collegeId;
        this.username = username;
        this.password = password;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
