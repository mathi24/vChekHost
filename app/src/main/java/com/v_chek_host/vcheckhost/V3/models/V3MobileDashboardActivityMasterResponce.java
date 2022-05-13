package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class V3MobileDashboardActivityMasterResponce {
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

    public class Message {

        @SerializedName("activity_id")
        @Expose
        private Integer activityId;
        @SerializedName("activity_name")
        @Expose
        private String activityName;
        @SerializedName("activity_short_description")
        @Expose
        private String activityShortDescription;
        private boolean isChecked=false;

        public Integer getActivityId() {
            return activityId;
        }

        public void setActivityId(Integer activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getActivityShortDescription() {
            return activityShortDescription;
        }

        public void setActivityShortDescription(String activityShortDescription) {
            this.activityShortDescription = activityShortDescription;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
