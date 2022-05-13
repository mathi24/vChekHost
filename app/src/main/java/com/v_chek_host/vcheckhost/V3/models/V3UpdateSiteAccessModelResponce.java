package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class V3UpdateSiteAccessModelResponce {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Message")
    @Expose
    private Message message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public class Message {

        @SerializedName("status_code")
        @Expose
        private Integer statusCode;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

    }
}
