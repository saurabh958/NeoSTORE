package com.example.saurabhneostore.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel
{
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("last_name")
            @Expose
    private String last_name;
    @SerializedName("email")
            @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirm_passwrod")
    @Expose
    private String confirm_password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("phone_no")
            @Expose
    private String phone_no;

    public RegisterModel(String first_name, String last_name, String email, String password, String confirm_password, String gender, String phone_no)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.gender = gender;
        this.phone_no = phone_no;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
