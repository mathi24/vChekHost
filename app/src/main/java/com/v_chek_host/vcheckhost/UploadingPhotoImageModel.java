package com.v_chek_host.vcheckhost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadingPhotoImageModel {

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("BlobName")
    @Expose
    private String blobName;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

}
