package com.v_chek_host.vcheckhost.V2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("DatabaseConnectionString")
    @Expose
    private String connString;
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

    public String getConnString() {
        return connString;
    }

    public void setConnString(String connString) {
        this.connString = connString;
    }


    public class Message implements Serializable{

        @SerializedName("status_code")
        @Expose
        private Integer statusCode;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("tenant_id")
        @Expose
        private Integer tenantId;
        @SerializedName("parent_id")
        @Expose
        private Integer parentId;
        @SerializedName("language_id")
        @Expose
        private Integer languageId;
        @SerializedName("language_code")
        @Expose
        private String languageCode;
        @SerializedName("site_id")
        @Expose
        private Integer siteId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("nick_name")
        @Expose
        private String nickName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("is_employee_id")
        @Expose
        private String isEmployeeId;
        @SerializedName("language_name")
        @Expose
        private String languageName;
        @SerializedName("timezone_duration")
        @Expose
        private String timezoneDuration;
        @SerializedName("timezone")
        @Expose
        private String timezone;

        @SerializedName("site_code")
        @Expose
        private String siteCode;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getTenantId() {
            return tenantId;
        }

        public void setTenantId(Integer tenantId) {
            this.tenantId = tenantId;
        }

        public Integer getLanguageId() {
            return languageId;
        }

        public void setLanguageId(Integer languageId) {
            this.languageId = languageId;
        }

        public Integer getSiteId() {
            return siteId;
        }

        public void setSiteId(Integer siteId) {
            this.siteId = siteId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getIsEmployeeId() {
            return isEmployeeId;
        }

        public void setIsEmployeeId(String isEmployeeId) {
            this.isEmployeeId = isEmployeeId;
        }

        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
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

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public String getSiteCode() {
            return siteCode;
        }

        public void setSiteCode(String siteCode) {
            this.siteCode = siteCode;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }


    }
}
