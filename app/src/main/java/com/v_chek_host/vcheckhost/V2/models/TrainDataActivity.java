package com.v_chek_host.vcheckhost.V2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrainDataActivity {
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

        @SerializedName("is_live")
        @Expose
        private Integer isLive;

        @SerializedName("is_vin_mandatory")
        @Expose
        private Integer vinMandate;

        @SerializedName("vin_max_length")
        @Expose
        private Integer vinLength;

        @SerializedName("ml_model_parameters")
        @Expose
        private List<ModelParams> modelParams;

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
        @SerializedName("status_name")
        @Expose
        private String statusName;
        @SerializedName("ml_model_data")
        @Expose
        private List<MLModelData> modelData;
        private boolean isSelected = false;

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

        public List<MLModelData> getModelData() {
            return modelData;
        }

        public void setModelData(List<MLModelData> modelData) {
            this.modelData = modelData;
        }

        public Integer getVinMandate() {
            return vinMandate;
        }

        public void setVinMandate(Integer vinMandate) {
            this.vinMandate = vinMandate;
        }

        public Integer getVinLength() {
            return vinLength;
        }

        public void setVinLength(Integer vinLength) {
            this.vinLength = vinLength;
        }

        public List<ModelParams> getModelParams() {
            return modelParams;
        }

        public void setModelParams(List<ModelParams> modelParams) {
            this.modelParams = modelParams;
        }

    }


    public static class MLModelData {


        @SerializedName("ml_model_id")
        @Expose
        private Integer mlModelId;
        @SerializedName("model_name")
        @Expose
        private String modelName;
        @SerializedName("model_seq")
        @Expose
        private Integer modelSeq;
        @SerializedName("model_thumbnail_url")
        @Expose
        private String modelImgUrl;

        @SerializedName("training_overlay_image_url")
        @Expose
        private String train_overlay_url;

        @SerializedName("training_image_width")
        @Expose
        private int trainingImageWidth;

        @SerializedName("training_image_height")
        @Expose
        private int trainingImageHeight;

        @SerializedName("training_video_width")
        @Expose
        private int trainingVideoWidth;

        @SerializedName("training_video_height")
        @Expose
        private int trainingVideoHeight;

        @SerializedName("training_video_max_time")
        @Expose
        private int trainingVideoTime;

        @SerializedName("training_bottom_crop_height")
        @Expose
        private int bottomCropHeight;

        private boolean isSelected = false;
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

        public Integer getModelSeq() {
            return modelSeq;
        }

        public void setModelSeq(Integer modelSeq) {
            this.modelSeq = modelSeq;
        }

        public String getModelImgUrl() {
            return modelImgUrl;
        }

        public void setModelImgUrl(String modelImgUrl) {
            this.modelImgUrl = modelImgUrl;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getTrainingImageWidth() {
            return trainingImageWidth;
        }

        public void setTrainingImageWidth(int trainingImageWidth) {
            this.trainingImageWidth = trainingImageWidth;
        }

        public int getTrainingImageHeight() {
            return trainingImageHeight;
        }

        public void setTrainingImageHeight(int trainingImageHeight) {
            this.trainingImageHeight = trainingImageHeight;
        }

        public String getTrain_overlay_url() {
            return train_overlay_url;
        }

        public void setTrain_overlay_url(String train_overlay_url) {
            this.train_overlay_url = train_overlay_url;
        }

        public int getBottomCropHeight() {
            return bottomCropHeight;
        }

        public void setBottomCropHeight(int bottomCropHeight) {
            this.bottomCropHeight = bottomCropHeight;
        }

        public int getTrainingVideoWidth() {
            return trainingVideoWidth;
        }

        public int getTrainingVideoHeight() {
            return trainingVideoHeight;
        }

        public int getTrainingVideoTime() {
            return trainingVideoTime;
        }
    }

    public static class ModelParams implements Serializable {

        @SerializedName("display_name")
        public String name;
        @SerializedName("default_value")
        public String value;

        public ModelParams (String name,String value){
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
