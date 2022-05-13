package com.v_chek_host.vcheckhost.V3.models;

public class MultipleImageUploadModel {
    String localImagePath = "";
    String serverImagePath = "";
    boolean isUploaded = false;
    String mlModelImageID = "";

    public MultipleImageUploadModel(String localImagePath, String serverImagePath, boolean isUploaded,
                                    String mlModelImageID) {
        this.localImagePath = localImagePath;
        this.serverImagePath = serverImagePath;
        this.isUploaded = isUploaded;
        this.mlModelImageID = mlModelImageID;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public String getServerImagePath() {
        return serverImagePath;
    }

    public void setServerImagePath(String serverImagePath) {
        this.serverImagePath = serverImagePath;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getMlModelImageID() {
        return mlModelImageID;
    }

    public void setMlModelImageID(String mlModelImageID) {
        this.mlModelImageID = mlModelImageID;
    }
}
