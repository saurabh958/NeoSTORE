package com.example.saurabhneostore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginmModelz {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private DataLogin data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_msg")
    @Expose
    private String userMsg;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginmModelz() {
    }

    /**
     *
     * @param data
     * @param message
     * @param userMsg
     * @param status
     */
    public LoginmModelz(String status, DataLogin data, String message, String userMsg) {
        super();
        this.status = status;
        this.data = data;
        this.message = message;
        this.userMsg = userMsg;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataLogin getData() {
        return data;
    }

    public void setData(DataLogin data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

}



