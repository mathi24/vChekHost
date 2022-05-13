package com.v_chek_host.vcheckhost;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllImagesDataModel implements Serializable{
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("GetImageCollectionCount")
    @Expose
    private String getImageCollectionCount;
    @SerializedName("Message")
    @Expose
    private List<Message> message = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getGetImageCollectionCount() {
        return getImageCollectionCount;
    }

    public void setGetImageCollectionCount(String getImageCollectionCount) {
        this.getImageCollectionCount = getImageCollectionCount;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public class Message implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("collection_image")
        @Expose
        private String collectionImage;
        @SerializedName("part_tag")
        @Expose
        private String partTag;
        @SerializedName("damage_tag")
        @Expose
        private String damageTag;
        @SerializedName("created_on")
        @Expose
        private String createdOn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCollectionImage() {
            return collectionImage;
        }

        public void setCollectionImage(String collectionImage) {
            this.collectionImage = collectionImage;
        }

        public String getPartTag() {
            return partTag;
        }

        public void setPartTag(String partTag) {
            this.partTag = partTag;
        }

        public String getDamageTag() {
            return damageTag;
        }

        public void setDamageTag(String damageTag) {
            this.damageTag = damageTag;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

    }
}
