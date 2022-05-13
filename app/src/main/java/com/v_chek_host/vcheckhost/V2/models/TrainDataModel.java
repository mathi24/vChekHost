package com.v_chek_host.vcheckhost.V2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainDataModel {
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

    public static class Message {

        @SerializedName("status_code")
        @Expose
        private Integer statusCode;
        @SerializedName("ml_model_id")
        @Expose
        private Integer mlModelId;
        @SerializedName("model_name")
        @Expose
        private String modelName;
        @SerializedName("model_short_description")
        @Expose
        private String modelShortDescription;
        @SerializedName("ml_model_status_id")
        @Expose
        private Integer mlModelStatusId;
        @SerializedName("status_name")
        @Expose
        private String statusName;
        private boolean isSelected = false;

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

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getModelShortDescription() {
            return modelShortDescription;
        }

        public void setModelShortDescription(String modelShortDescription) {
            this.modelShortDescription = modelShortDescription;
        }

        public Integer getMlModelStatusId() {
            return mlModelStatusId;
        }

        public void setMlModelStatusId(Integer mlModelStatusId) {
            this.mlModelStatusId = mlModelStatusId;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
