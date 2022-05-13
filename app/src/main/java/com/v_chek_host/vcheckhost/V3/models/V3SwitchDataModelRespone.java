package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class V3SwitchDataModelRespone {
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

        @SerializedName("site_user_site_map_id")
        @Expose
        private Integer siteUserSiteMapId;
        @SerializedName("site_user_id")
        @Expose
        private Integer siteUserId;
        @SerializedName("site_id")
        @Expose
        private Integer siteId;
        @SerializedName("last_access")
        @Expose
        private Integer lastAccess;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("is_employee_id")
        @Expose
        private Integer isEmployeeId;
        @SerializedName("site_code")
        @Expose
        private String siteCode;
        @SerializedName("timezone_duration")
        @Expose
        private String timezoneDuration;
        @SerializedName("timezone")
        @Expose
        private String timezone;
        private boolean isSelected;

        public Integer getSiteUserSiteMapId() {
            return siteUserSiteMapId;
        }

        public void setSiteUserSiteMapId(Integer siteUserSiteMapId) {
            this.siteUserSiteMapId = siteUserSiteMapId;
        }

        public Integer getSiteUserId() {
            return siteUserId;
        }

        public void setSiteUserId(Integer siteUserId) {
            this.siteUserId = siteUserId;
        }

        public Integer getSiteId() {
            return siteId;
        }

        public void setSiteId(Integer siteId) {
            this.siteId = siteId;
        }

        public Integer getLastAccess() {
            return lastAccess;
        }

        public void setLastAccess(Integer lastAccess) {
            this.lastAccess = lastAccess;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Integer getIsEmployeeId() {
            return isEmployeeId;
        }

        public void setIsEmployeeId(Integer isEmployeeId) {
            this.isEmployeeId = isEmployeeId;
        }

        public String getSiteCode() {
            return siteCode;
        }

        public void setSiteCode(String siteCode) {
            this.siteCode = siteCode;
        }

        public String getTimezoneDuration() {
            return timezoneDuration;
        }

        public void setTimezoneDuration(String timezoneDuration) {
            this.timezoneDuration = timezoneDuration;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
