package com.v_chek_host.vcheckhost.V3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "train_models")
public class TrainModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imagePATH;
    private boolean isUploaded = false;
    private String uploadedURL = "";

    public TrainModel() {
    }

    public TrainModel(int id, String imagePATH) {
        this.id = id;
        this.imagePATH = imagePATH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePATH() {
        return imagePATH;
    }

    public void setImagePATH(String imagePATH) {
        this.imagePATH = imagePATH;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getUploadedURL() {
        return uploadedURL;
    }

    public void setUploadedURL(String uploadedURL) {
        this.uploadedURL = uploadedURL;
    }
}
