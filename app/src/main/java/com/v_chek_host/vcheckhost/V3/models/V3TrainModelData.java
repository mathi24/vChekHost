package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class V3TrainModelData {
    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("recent_ml_models")
    @Expose
    private List<RecentMlModel> recentMlModels = null;
    @SerializedName("ml_model_type_master_data")
    @Expose
    private List<MlModelTypeMasterDatum> mlModelTypeMasterData = null;
    @SerializedName("ml_models_data")
    @Expose
    private List<MlModelsDatum> mlModelsData = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<RecentMlModel> getRecentMlModels() {
        return recentMlModels;
    }

    public void setRecentMlModels(List<RecentMlModel> recentMlModels) {
        this.recentMlModels = recentMlModels;
    }

    public List<MlModelTypeMasterDatum> getMlModelTypeMasterData() {
        return mlModelTypeMasterData;
    }

    public void setMlModelTypeMasterData(List<MlModelTypeMasterDatum> mlModelTypeMasterData) {
        this.mlModelTypeMasterData = mlModelTypeMasterData;
    }

    public List<MlModelsDatum> getMlModelsData() {
        return mlModelsData;
    }

    public void setMlModelsData(List<MlModelsDatum> mlModelsData) {
        this.mlModelsData = mlModelsData;
    }

    public class RecentMlModel {

        @SerializedName("ml_model_id")
        @Expose
        private Integer mlModelId;
        @SerializedName("model_name")
        @Expose
        private String modelName;
        @SerializedName("model_thumbnail_url")
        @Expose
        private String modelThumbnailUrl;
        @SerializedName("training_image_width")
        @Expose
        private Integer trainingImageWidth;
        @SerializedName("training_image_height")
        @Expose
        private Integer trainingImageHeight;
        @SerializedName("training_video_width")
        @Expose
        private Integer trainingVideoWidth;
        @SerializedName("training_video_height")
        @Expose
        private Integer trainingVideoHeight;
        @SerializedName("training_video_max_time")
        @Expose
        private Integer trainingVideoMaxTime;
        @SerializedName("training_overlay_image_url")
        @Expose
        private String trainingOverlayImageUrl;
        @SerializedName("training_bottom_crop_height")
        @Expose
        private Integer trainingBottomCropHeight;
        @SerializedName("ml_model_type_id")
        @Expose
        private Integer mlModelTypeId;
        @SerializedName("ml_model_type_name")
        @Expose
        private String mlModelTypeName;

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

        public String getModelThumbnailUrl() {
            return modelThumbnailUrl;
        }

        public void setModelThumbnailUrl(String modelThumbnailUrl) {
            this.modelThumbnailUrl = modelThumbnailUrl;
        }

        public Integer getTrainingImageWidth() {
            return trainingImageWidth;
        }

        public void setTrainingImageWidth(Integer trainingImageWidth) {
            this.trainingImageWidth = trainingImageWidth;
        }

        public Integer getTrainingImageHeight() {
            return trainingImageHeight;
        }

        public void setTrainingImageHeight(Integer trainingImageHeight) {
            this.trainingImageHeight = trainingImageHeight;
        }

        public Integer getTrainingVideoWidth() {
            return trainingVideoWidth;
        }

        public void setTrainingVideoWidth(Integer trainingVideoWidth) {
            this.trainingVideoWidth = trainingVideoWidth;
        }

        public Integer getTrainingVideoHeight() {
            return trainingVideoHeight;
        }

        public void setTrainingVideoHeight(Integer trainingVideoHeight) {
            this.trainingVideoHeight = trainingVideoHeight;
        }

        public Integer getTrainingVideoMaxTime() {
            return trainingVideoMaxTime;
        }

        public void setTrainingVideoMaxTime(Integer trainingVideoMaxTime) {
            this.trainingVideoMaxTime = trainingVideoMaxTime;
        }

        public String getTrainingOverlayImageUrl() {
            return trainingOverlayImageUrl;
        }

        public void setTrainingOverlayImageUrl(String trainingOverlayImageUrl) {
            this.trainingOverlayImageUrl = trainingOverlayImageUrl;
        }

        public Integer getTrainingBottomCropHeight() {
            return trainingBottomCropHeight;
        }

        public void setTrainingBottomCropHeight(Integer trainingBottomCropHeight) {
            this.trainingBottomCropHeight = trainingBottomCropHeight;
        }

        public Integer getMlModelTypeId() {
            return mlModelTypeId;
        }

        public void setMlModelTypeId(Integer mlModelTypeId) {
            this.mlModelTypeId = mlModelTypeId;
        }

        public String getMlModelTypeName() {
            return mlModelTypeName;
        }

        public void setMlModelTypeName(String mlModelTypeName) {
            this.mlModelTypeName = mlModelTypeName;
        }

    }

    public class MlModelTypeMasterDatum {

        @SerializedName("ml_model_type_id")
        @Expose
        private Integer mlModelTypeId;
        @SerializedName("ml_model_type_name")
        @Expose
        private String mlModelTypeName;
        public boolean isSelected=false;

        public Integer getMlModelTypeId() {
            return mlModelTypeId;
        }

        public void setMlModelTypeId(Integer mlModelTypeId) {
            this.mlModelTypeId = mlModelTypeId;
        }

        public String getMlModelTypeName() {
            return mlModelTypeName;
        }

        public void setMlModelTypeName(String mlModelTypeName) {
            this.mlModelTypeName = mlModelTypeName;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class MlModelsDatum {

        @SerializedName("ml_model_id")
        @Expose
        private Integer mlModelId;
        @SerializedName("model_name")
        @Expose
        private String modelName;
        @SerializedName("model_thumbnail_url")
        @Expose
        private String modelThumbnailUrl;
        @SerializedName("training_image_width")
        @Expose
        private Integer trainingImageWidth;
        @SerializedName("training_image_height")
        @Expose
        private Integer trainingImageHeight;
        @SerializedName("training_video_width")
        @Expose
        private Integer trainingVideoWidth;
        @SerializedName("training_video_height")
        @Expose
        private Integer trainingVideoHeight;
        @SerializedName("training_video_max_time")
        @Expose
        private Integer trainingVideoMaxTime;
        @SerializedName("training_overlay_image_url")
        @Expose
        private String trainingOverlayImageUrl;
        @SerializedName("training_bottom_crop_height")
        @Expose
        private Integer trainingBottomCropHeight;
        @SerializedName("ml_model_type_id")
        @Expose
        private Integer mlModelTypeId;
        @SerializedName("ml_model_type_name")
        @Expose
        private String mlModelTypeName;

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

        public String getModelThumbnailUrl() {
            return modelThumbnailUrl;
        }

        public void setModelThumbnailUrl(String modelThumbnailUrl) {
            this.modelThumbnailUrl = modelThumbnailUrl;
        }

        public Integer getTrainingImageWidth() {
            return trainingImageWidth;
        }

        public void setTrainingImageWidth(Integer trainingImageWidth) {
            this.trainingImageWidth = trainingImageWidth;
        }

        public Integer getTrainingImageHeight() {
            return trainingImageHeight;
        }

        public void setTrainingImageHeight(Integer trainingImageHeight) {
            this.trainingImageHeight = trainingImageHeight;
        }

        public Integer getTrainingVideoWidth() {
            return trainingVideoWidth;
        }

        public void setTrainingVideoWidth(Integer trainingVideoWidth) {
            this.trainingVideoWidth = trainingVideoWidth;
        }

        public Integer getTrainingVideoHeight() {
            return trainingVideoHeight;
        }

        public void setTrainingVideoHeight(Integer trainingVideoHeight) {
            this.trainingVideoHeight = trainingVideoHeight;
        }

        public Integer getTrainingVideoMaxTime() {
            return trainingVideoMaxTime;
        }

        public void setTrainingVideoMaxTime(Integer trainingVideoMaxTime) {
            this.trainingVideoMaxTime = trainingVideoMaxTime;
        }

        public String getTrainingOverlayImageUrl() {
            return trainingOverlayImageUrl;
        }

        public void setTrainingOverlayImageUrl(String trainingOverlayImageUrl) {
            this.trainingOverlayImageUrl = trainingOverlayImageUrl;
        }

        public Integer getTrainingBottomCropHeight() {
            return trainingBottomCropHeight;
        }

        public void setTrainingBottomCropHeight(Integer trainingBottomCropHeight) {
            this.trainingBottomCropHeight = trainingBottomCropHeight;
        }

        public Integer getMlModelTypeId() {
            return mlModelTypeId;
        }

        public void setMlModelTypeId(Integer mlModelTypeId) {
            this.mlModelTypeId = mlModelTypeId;
        }

        public String getMlModelTypeName() {
            return mlModelTypeName;
        }

        public void setMlModelTypeName(String mlModelTypeName) {
            this.mlModelTypeName = mlModelTypeName;
        }

    }
}
