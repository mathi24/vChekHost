package com.v_chek_host.vcheckhost.V3.models;

public class V3MasterActivityNameModel {
    String activityName;
    boolean isChecked;
    int activityID;

    public V3MasterActivityNameModel() {
    }

    public V3MasterActivityNameModel(String activityName, boolean isChecked,int activityID) {
        this.activityName = activityName;
        this.isChecked = isChecked;
        this.activityID=activityID;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }
}
