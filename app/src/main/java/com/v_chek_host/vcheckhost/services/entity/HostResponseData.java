package com.v_chek_host.vcheckhost.services.entity;

import com.google.gson.annotations.SerializedName;
import com.v_chek_host.vcheckhostsdk.model.entity.ParentMetaData;

import java.io.Serializable;
import java.util.List;

public class HostResponseData implements Serializable {

    @SerializedName("MetaData")
    public ParentMetaData.MetaData metaData;


    @SerializedName("Result")
    public ActivityResult activityResult;

    @SerializedName("Primary")
    public ParentMetaData.PrimaryData primaryData;

    public HostResponseData(ParentMetaData.MetaData metaData, ActivityResult activityResult, ParentMetaData.PrimaryData primaryData){
        this.metaData = metaData;
        this.activityResult = activityResult;
        this.primaryData = primaryData;
    }

    public HostResponseData(ParentMetaData.MetaData metaData, ActivityResult activityResult){
        this.metaData = metaData;
        this.activityResult = activityResult;
    }

    public ParentMetaData.MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ParentMetaData.MetaData metaData) {
        this.metaData = metaData;
    }

    public ParentMetaData.PrimaryData getPrimaryData() {
        return primaryData;
    }

    public void setPrimaryData(ParentMetaData.PrimaryData primaryData) {
        this.primaryData = primaryData;
    }

    public ActivityResult getActivityResult() {
        return activityResult;
    }

    /*public class MetaData implements Serializable{

        @SerializedName("ActivityId")
        public String activityId;
        @SerializedName("LanguageID")
        public String languageId;
        @SerializedName("ConnString")
        public String connStr;
        @SerializedName("Site-ID")
        public String siteId;
        @SerializedName("Emp_ID")
        public String empId;
        @SerializedName("Emp_Name")
        public String empName;
        @SerializedName("User-ID")
        public String userId;
        @SerializedName("User_Name")
        public String userName;
        @SerializedName("ObjectIDName")
        public String objectName;
        @SerializedName("ObjectIDValue")
        public String objectId;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getLanguageId() {
            return languageId;
        }

        public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }

        public String getConnStr() {
            return connStr;
        }

        public void setConnStr(String connStr) {
            this.connStr = connStr;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

    }*/

    /*public class ModelResult implements Serializable{


        @SerializedName("ActivityResult")
        public String activityResult;

        @SerializedName("Message")
        public String resultMessage;

        @SerializedName("Result")
        public List<ActivityResult> activityResults;

        public String getActivityResult() {
            return activityResult;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public List<ActivityResult> getActivityResults() {
            return activityResults;
        }

    }*/

    public static class ActivityResult implements Serializable{

        @SerializedName("ActivityResult")
        public String activityResult;
        @SerializedName("Message")
        public String activityResultMsg;
        @SerializedName("Models")
        public List<ModelResult> modelList;

        public ActivityResult(String activityResult, String activityResultMsg, List<ModelResult> modelList) {
            this.activityResult = activityResult;
            this.activityResultMsg = activityResultMsg;
            this.modelList = modelList;
        }


        public String getActivityResult() {
            return activityResult;
        }

        public String getActivityResultMsg() {
            return activityResultMsg;
        }

        public List<ModelResult> getModelList() {
            return modelList;
        }
    }

    public static class ModelResult implements Serializable{

        @SerializedName("ID")
        public String modelId;

        @SerializedName("Name")
        public String modelName;

        @SerializedName("SeqID")
        public String seqId ;

        @SerializedName("Version")
        public String modelVersion;

        @SerializedName("AOI")
        public String areaOfInterest;

        @SerializedName("Result")
        public String inspktResult;

        @SerializedName("ResultImg")
        public String inspktResultImage;

        @SerializedName("Message")
        public String resultMessage;

        @SerializedName("Attributes")
        public List<Attributes> resultAttribute;

        public ModelResult(String modelId, String modelName, String seqId, String modelVersion,
                           String areaOfInterest, String inspktResult, String resultMessage,String inspktResultImage/*, List<Attributes> resultAttribute*/) {
            this.modelId = modelId;
            this.modelName = modelName;
            this.seqId = seqId;
            this.modelVersion = modelVersion;
            this.areaOfInterest = areaOfInterest;
            this.inspktResult = inspktResult;
            this.resultMessage = resultMessage;
            this.inspktResultImage = inspktResultImage;
           // this.resultAttribute = resultAttribute;
        }


        public String getActivityResult() {
            return modelId;
        }

        public String getModelName() {
            return modelName;
        }

        public String getSeqId() {
            return seqId;
        }

        public String getModelVersion() {
            return modelVersion;
        }

        public String getAreaOfInterest() {
            return areaOfInterest;
        }

        public String getInspktResult() {
            return inspktResult;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public List<Attributes> getResultAttribute() {
            return resultAttribute;
        }

        public String getInspktResultImage() {
            return inspktResultImage;
        }
    }

    public class Attributes implements Serializable{

        @SerializedName("Name")
        public String name;

        @SerializedName("Value")
        public String value;

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


    public class PrimaryData implements Serializable{

        @SerializedName("Params")
        public List<ParentMetaData.PrimaryParams> primaryParams;


        public List<ParentMetaData.PrimaryParams> getPrimaryParams() {
            return primaryParams;
        }

    }

    public class PrimaryParams implements Serializable{

        @SerializedName("Name")
        public String name;
        @SerializedName("Value")
        public String value;

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
