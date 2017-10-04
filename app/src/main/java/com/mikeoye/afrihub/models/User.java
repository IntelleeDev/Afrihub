package com.mikeoye.afrihub.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lami on 2/26/2017.
 */

public class User {

    private String userId;
    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private String profileImage;

    public User() {
    }

    public User(String userId, String username, String emailAddress, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public User(
            String userId,
            String username,
            String password,
            String emailAddress,
            String phoneNumber,
            String profileImage)
    {
        this.userId         =   userId;
        this.username       =   username;
        this.password       =   password;
        this.emailAddress   =   emailAddress;
        this.phoneNumber    =   phoneNumber;
        this.profileImage   =   profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("username", username);
        result.put("emailAddress", emailAddress);
        result.put("phoneNumber", phoneNumber);
        return result;
    }
}
