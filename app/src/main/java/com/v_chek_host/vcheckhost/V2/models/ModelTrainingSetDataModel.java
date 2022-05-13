package com.v_chek_host.vcheckhost.V2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelTrainingSetDataModel {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Message")
    @Expose
    private List<Message> message = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public class Message implements Serializable {

        @SerializedName("status_code")
        @Expose
        private Integer statusCode;
        @SerializedName("ml_model_id")
        @Expose
        private Integer mlModelId;
        @SerializedName("ml_model_image_url")
        @Expose
        private String mlModelImageUrl;
        @SerializedName("is_trained")
        @Expose
        private Integer isTrained;
        @SerializedName("image_result")
        @Expose
        private Integer imageResult;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public Integer getMlModelId() {
            return mlModelId;
        }

        public void setMlModelId(Integer mlModelId) {
            this.mlModelId = mlModelId;
        }

        public String getMlModelImageUrl() {
            return mlModelImageUrl;
        }

        public void setMlModelImageUrl(String mlModelImageUrl) {
            this.mlModelImageUrl = mlModelImageUrl;
        }

        public Integer getIsTrained() {
            return isTrained;
        }

        public void setIsTrained(Integer isTrained) {
            this.isTrained = isTrained;
        }

        public Integer getImageResult() {
            return imageResult;
        }

        public void setImageResult(Integer imageResult) {
            this.imageResult = imageResult;
        }
    }
}
