package com.v_chek_host.vcheckhost.V3.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class V3MobileDashBoardDataResponce {
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
        @SerializedName("activity_name")
        @Expose
        private String activityName;
        @SerializedName("user_data")
        @Expose
        private UserData userData;
        @SerializedName("week_wise_dashboard")
        @Expose
        private WeekWiseDashboard weekWiseDashboard;
        @SerializedName("year_wise_dashboard")
        @Expose
        private YearWiseDashboard yearWiseDashboard;
        @SerializedName("all_wise_dashboard")
        @Expose
        private AllWiseDashboard allWiseDashboard;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public UserData getUserData() {
            return userData;
        }

        public void setUserData(UserData userData) {
            this.userData = userData;
        }

        public WeekWiseDashboard getWeekWiseDashboard() {
            return weekWiseDashboard;
        }

        public void setWeekWiseDashboard(WeekWiseDashboard weekWiseDashboard) {
            this.weekWiseDashboard = weekWiseDashboard;
        }

        public YearWiseDashboard getYearWiseDashboard() {
            return yearWiseDashboard;
        }

        public void setYearWiseDashboard(YearWiseDashboard yearWiseDashboard) {
            this.yearWiseDashboard = yearWiseDashboard;
        }

        public AllWiseDashboard getAllWiseDashboard() {
            return allWiseDashboard;
        }

        public void setAllWiseDashboard(AllWiseDashboard allWiseDashboard) {
            this.allWiseDashboard = allWiseDashboard;
        }

    }

    public class UserData {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("profile_image_blob")
        @Expose
        private String profileImageBlob;

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

        public String getProfileImageBlob() {
            return profileImageBlob;
        }

        public void setProfileImageBlob(String profileImageBlob) {
            this.profileImageBlob = profileImageBlob;
        }

    }

    public class WeekWiseDashboard {

        @SerializedName("total_inspections_count")
        @Expose
        private Integer totalInspectionsCount;
        @SerializedName("pass_inspections_count")
        @Expose
        private Integer passInspectionsCount;
        @SerializedName("fail_inspections_count")
        @Expose
        private Integer failInspectionsCount;
        @SerializedName("data")
        @Expose
        private List<WeeklyWiseData> data = null;

        public Integer getTotalInspectionsCount() {
            return totalInspectionsCount;
        }

        public void setTotalInspectionsCount(Integer totalInspectionsCount) {
            this.totalInspectionsCount = totalInspectionsCount;
        }

        public Integer getPassInspectionsCount() {
            return passInspectionsCount;
        }

        public void setPassInspectionsCount(Integer passInspectionsCount) {
            this.passInspectionsCount = passInspectionsCount;
        }

        public Integer getFailInspectionsCount() {
            return failInspectionsCount;
        }

        public void setFailInspectionsCount(Integer failInspectionsCount) {
            this.failInspectionsCount = failInspectionsCount;
        }

        public List<WeeklyWiseData> getData() {
            return data;
        }

        public void setData(List<WeeklyWiseData> data) {
            this.data = data;
        }

    }

    public class YearWiseDashboard {

        @SerializedName("total_inspections_count")
        @Expose
        private Integer totalInspectionsCount;
        @SerializedName("pass_inspections_count")
        @Expose
        private Integer passInspectionsCount;
        @SerializedName("fail_inspections_count")
        @Expose
        private Integer failInspectionsCount;
        @SerializedName("data")
        @Expose
        private List<YearWiseData> data = null;

        public Integer getTotalInspectionsCount() {
            return totalInspectionsCount;
        }

        public void setTotalInspectionsCount(Integer totalInspectionsCount) {
            this.totalInspectionsCount = totalInspectionsCount;
        }

        public Integer getPassInspectionsCount() {
            return passInspectionsCount;
        }

        public void setPassInspectionsCount(Integer passInspectionsCount) {
            this.passInspectionsCount = passInspectionsCount;
        }

        public Integer getFailInspectionsCount() {
            return failInspectionsCount;
        }

        public void setFailInspectionsCount(Integer failInspectionsCount) {
            this.failInspectionsCount = failInspectionsCount;
        }

        public List<YearWiseData> getData() {
            return data;
        }

        public void setData(List<YearWiseData> data) {
            this.data = data;
        }

    }

    public class AllWiseDashboard {

        @SerializedName("total_inspections_count")
        @Expose
        private Integer totalInspectionsCount;
        @SerializedName("pass_inspections_count")
        @Expose
        private Integer passInspectionsCount;
        @SerializedName("fail_inspections_count")
        @Expose
        private Integer failInspectionsCount;
        @SerializedName("data")
        @Expose
        private List<AllWiseData> data = null;

        public Integer getTotalInspectionsCount() {
            return totalInspectionsCount;
        }

        public void setTotalInspectionsCount(Integer totalInspectionsCount) {
            this.totalInspectionsCount = totalInspectionsCount;
        }

        public Integer getPassInspectionsCount() {
            return passInspectionsCount;
        }

        public void setPassInspectionsCount(Integer passInspectionsCount) {
            this.passInspectionsCount = passInspectionsCount;
        }

        public Integer getFailInspectionsCount() {
            return failInspectionsCount;
        }

        public void setFailInspectionsCount(Integer failInspectionsCount) {
            this.failInspectionsCount = failInspectionsCount;
        }

        public List<AllWiseData> getData() {
            return data;
        }

        public void setData(List<AllWiseData> data) {
            this.data = data;
        }

    }

    public class WeeklyWiseData {

        @SerializedName("total_inspections_count")
        @Expose
        private Integer totalInspectionsCount;
        @SerializedName("pass_inspections_count")
        @Expose
        private Integer passInspectionsCount;
        @SerializedName("fail_inspections_count")
        @Expose
        private Integer failInspectionsCount;
        @SerializedName("day_name")
        @Expose
        private String dayName;
        @SerializedName("week_date")
        @Expose
        private String weekDate;
        private boolean isSelected = false;

        public Integer getTotalInspectionsCount() {
            return totalInspectionsCount;
        }

        public void setTotalInspectionsCount(Integer totalInspectionsCount) {
            this.totalInspectionsCount = totalInspectionsCount;
        }

        public Integer getPassInspectionsCount() {
            return passInspectionsCount;
        }

        public void setPassInspectionsCount(Integer passInspectionsCount) {
            this.passInspectionsCount = passInspectionsCount;
        }

        public Integer getFailInspectionsCount() {
            return failInspectionsCount;
        }

        public void setFailInspectionsCount(Integer failInspectionsCount) {
            this.failInspectionsCount = failInspectionsCount;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public String getWeekDate() {
            return weekDate;
        }

        public void setWeekDate(String weekDate) {
            this.weekDate = weekDate;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class YearWiseData {

        @SerializedName("month_name")
        @Expose
        private String monthName;
        @SerializedName("year")
        @Expose
        private Integer year;
        @SerializedName("total_inspections_count")
        @Expose
        private Integer totalInspectionsCount;
        @SerializedName("pass_inspections_count")
        @Expose
        private Integer passInspectionsCount;
        @SerializedName("fail_inspections_count")
        @Expose
        private Integer failInspectionsCount;
        private boolean isSelected;

        public String getMonthName() {
            return monthName;
        }

        public void setMonthName(String monthName) {
            this.monthName = monthName;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getTotalInspectionsCount() {
            return totalInspectionsCount;
        }

        public void setTotalInspectionsCount(Integer totalInspectionsCount) {
            this.totalInspectionsCount = totalInspectionsCount;
        }

        public Integer getPassInspectionsCount() {
            return passInspectionsCount;
        }

        public void setPassInspectionsCount(Integer passInspectionsCount) {
            this.passInspectionsCount = passInspectionsCount;
        }

        public Integer getFailInspectionsCount() {
            return failInspectionsCount;
        }

        public void setFailInspectionsCount(Integer failInspectionsCount) {
            this.failInspectionsCount = failInspectionsCount;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class AllWiseData {

        @SerializedName("year")
        @Expose
        private Integer year;
        @SerializedName("total_inspections_count")
        @Expose
        private Integer totalInspectionsCount;
        @SerializedName("pass_inspections_count")
        @Expose
        private Integer passInspectionsCount;
        @SerializedName("fail_inspections_count")
        @Expose
        private Integer failInspectionsCount;
        private boolean isSelected;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getTotalInspectionsCount() {
            return totalInspectionsCount;
        }

        public void setTotalInspectionsCount(Integer totalInspectionsCount) {
            this.totalInspectionsCount = totalInspectionsCount;
        }

        public Integer getPassInspectionsCount() {
            return passInspectionsCount;
        }

        public void setPassInspectionsCount(Integer passInspectionsCount) {
            this.passInspectionsCount = passInspectionsCount;
        }

        public Integer getFailInspectionsCount() {
            return failInspectionsCount;
        }

        public void setFailInspectionsCount(Integer failInspectionsCount) {
            this.failInspectionsCount = failInspectionsCount;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
