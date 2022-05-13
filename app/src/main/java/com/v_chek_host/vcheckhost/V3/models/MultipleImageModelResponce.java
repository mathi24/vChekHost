package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultipleImageModelResponce {
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
        @SerializedName("get_ml_model_image_id")
        @Expose
        private Integer getMlModelImageId;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public Integer getGetMlModelImageId() {
            return getMlModelImageId;
        }

        public void setGetMlModelImageId(Integer getMlModelImageId) {
            this.getMlModelImageId = getMlModelImageId;
        }

    }
}
