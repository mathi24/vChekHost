package com.v_chek_host.vcheckhost.V2.models;

public class UploadVideoData {

    private int keyId;
    private int modelId;
    private int uploadType;
    private String videoLocation;
    private int uploadStatus;

    public UploadVideoData(int modelId,int uploadType,String videoLocation,int uploadStatus){
        this.modelId = modelId;
        this.uploadType = uploadType;
        this.videoLocation = videoLocation;
        this.uploadStatus = uploadStatus;
    }

    public UploadVideoData(int keyId,int modelId,int uploadType,String videoLocation,int uploadStatus){
        this.keyId = keyId;
        this.modelId = modelId;
        this.uploadType = uploadType;
        this.videoLocation = videoLocation;
        this.uploadStatus = uploadStatus;
    }

    public int getKeyId() {
        return keyId;
    }

    public int getModelId() {
        return modelId;
    }

    public int getUploadType() {
        return uploadType;
    }

    public String getVideoLocation() {
        return videoLocation;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }
}
