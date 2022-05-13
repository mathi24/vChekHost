package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class V3GetAllActivitiesDataModel {
    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("RecentActivity")
    @Expose
    private List<RecentActivity> recentActivity = null;
    @SerializedName("AllActivity")
    @Expose
    private List<AllActivity> allActivity = null;
    @SerializedName("ActivityTypeMasterData")
    @Expose
    private List<ActivityTypeMasterDatum> activityTypeMasterData = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<RecentActivity> getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(List<RecentActivity> recentActivity) {
        this.recentActivity = recentActivity;
    }

    public List<AllActivity> getAllActivity() {
        return allActivity;
    }

    public void setAllActivity(List<AllActivity> allActivity) {
        this.allActivity = allActivity;
    }

    public List<ActivityTypeMasterDatum> getActivityTypeMasterData() {
        return activityTypeMasterData;
    }

    public void setActivityTypeMasterData(List<ActivityTypeMasterDatum> activityTypeMasterData) {
        this.activityTypeMasterData = activityTypeMasterData;
    }

    public class RecentActivity {

        @SerializedName("activity_id")
        @Expose
        private Integer activityId;
        @SerializedName("activity_name")
        @Expose
        private String activityName;
        @SerializedName("activity_short_description")
        @Expose
        private String activityShortDescription;
        @SerializedName("activity_thumbnail_url")
        @Expose
        private String activityThumbnailUrl;
        @SerializedName("is_live")
        @Expose
        private Integer isLive;
        @SerializedName("is_vin_mandatory")
        @Expose
        private Integer isVinMandatory;
        @SerializedName("vin_max_length")
        @Expose
        private Integer vinMaxLength;
        @SerializedName("ml_model_parameters")
        @Expose
        private List<MlModelParameters> mlModelParameters = null;

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

        public String getActivityThumbnailUrl() {
            return activityThumbnailUrl;
        }

        public void setActivityThumbnailUrl(String activityThumbnailUrl) {
            this.activityThumbnailUrl = activityThumbnailUrl;
        }

        public Integer getIsLive() {
            return isLive;
        }

        public void setIsLive(Integer isLive) {
            this.isLive = isLive;
        }

        public Integer getIsVinMandatory() {
            return isVinMandatory;
        }

        public void setIsVinMandatory(Integer isVinMandatory) {
            this.isVinMandatory = isVinMandatory;
        }

        public Integer getVinMaxLength() {
            return vinMaxLength;
        }

        public void setVinMaxLength(Integer vinMaxLength) {
            this.vinMaxLength = vinMaxLength;
        }

        public List<MlModelParameters> getMlModelParameters() {
            return mlModelParameters;
        }

        public void setMlModelParameters(List<MlModelParameters>  mlModelParameters) {
            this.mlModelParameters = mlModelParameters;
        }

    }

    public class MlModelParameters {

        @SerializedName("activity_id")
        @Expose
        private Integer activityId;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("default_value")
        @Expose
        private String defaultValue;

        public Integer getActivityId() {
            return activityId;
        }

        public void setActivityId(Integer activityId) {
            this.activityId = activityId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

    }

    public class AllActivity {

        @SerializedName("activity_id")
        @Expose
        private Integer activityId;
        @SerializedName("activity_name")
        @Expose
        private String activityName;
        @SerializedName("activity_short_description")
        @Expose
        private String activityShortDescription;
        @SerializedName("activity_thumbnail_url")
        @Expose
        private String activityThumbnailUrl;
        @SerializedName("is_live")
        @Expose
        private Integer isLive;
        @SerializedName("is_vin_mandatory")
        @Expose
        private Integer isVinMandatory;
        @SerializedName("vin_max_length")
        @Expose
        private Integer vinMaxLength;
        @SerializedName("activity_type_id")
        @Expose
        private Integer activityTypeId;
        @SerializedName("activity_type_name")
        @Expose
        private String activityTypeName;
        @SerializedName("ml_model_parameters")
        @Expose
        private List<MlModelParameters> mlModelParameters;

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

        public String getActivityThumbnailUrl() {
            return activityThumbnailUrl;
        }

        public void setActivityThumbnailUrl(String activityThumbnailUrl) {
            this.activityThumbnailUrl = activityThumbnailUrl;
        }

        public Integer getIsLive() {
            return isLive;
        }

        public void setIsLive(Integer isLive) {
            this.isLive = isLive;
        }

        public Integer getIsVinMandatory() {
            return isVinMandatory;
        }

        public void setIsVinMandatory(Integer isVinMandatory) {
            this.isVinMandatory = isVinMandatory;
        }

        public Integer getVinMaxLength() {
            return vinMaxLength;
        }

        public void setVinMaxLength(Integer vinMaxLength) {
            this.vinMaxLength = vinMaxLength;
        }

        public Integer getActivityTypeId() {
            return activityTypeId;
        }

        public void setActivityTypeId(Integer activityTypeId) {
            this.activityTypeId = activityTypeId;
        }

        public String getActivityTypeName() {
            return activityTypeName;
        }

        public void setActivityTypeName(String activityTypeName) {
            this.activityTypeName = activityTypeName;
        }

        public List<MlModelParameters> getMlModelParameters() {
            return mlModelParameters;
        }

        public void setMlModelParameters(List<MlModelParameters> mlModelParameters) {
            this.mlModelParameters = mlModelParameters;
        }

    }

    public class ActivityTypeMasterDatum {

        @SerializedName("activity_type_id")
        @Expose
        private Integer activityTypeId;
        @SerializedName("activity_type_name")
        @Expose
        private String activityTypeName;
        boolean isSelected=false;

        public Integer getActivityTypeId() {
            return activityTypeId;
        }

        public void setActivityTypeId(Integer activityTypeId) {
            this.activityTypeId = activityTypeId;
        }

        public String getActivityTypeName() {
            return activityTypeName;
        }

        public void setActivityTypeName(String activityTypeName) {
            this.activityTypeName = activityTypeName;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
