package com.v_chek_host.vcheckhost.V2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelData {
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


    public static class Message {

        @SerializedName("ml_model_data")
        @Expose
        private List<ApiModelData> modelData;

        public List<ApiModelData> getModelData() {
            return modelData;
        }

    }


    public static class ApiModelData {


        @SerializedName("ml_model_id")
        @Expose
        private Integer mlModelId;
        @SerializedName("model_name")
        @Expose
        private String modelName;

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


        public int getMlModelId() {
            return mlModelId;
        }

        public void setMlModelId(int mlModelId) {
            this.mlModelId = mlModelId;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
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
}
