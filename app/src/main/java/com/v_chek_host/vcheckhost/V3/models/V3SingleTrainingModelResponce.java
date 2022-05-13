package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class V3SingleTrainingModelResponce {
    @SerializedName("Message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
