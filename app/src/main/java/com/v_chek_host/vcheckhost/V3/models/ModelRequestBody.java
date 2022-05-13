package com.v_chek_host.vcheckhost.V3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRequestBody {


    @SerializedName("v_tenant_id")
    @Expose
    private Integer vTenantId;
    @SerializedName("v_site_id")
    @Expose
    private Integer vSiteId;
    @SerializedName("v_site_user_id")
    @Expose
    private Integer vSiteUserId;

    public ModelRequestBody() {
    }

    public ModelRequestBody(Integer vTenantId, Integer vSiteId, Integer vSiteUserId) {
        this.vTenantId = vTenantId;
        this.vSiteId = vSiteId;
        this.vSiteUserId = vSiteUserId;
    }

    public Integer getvTenantId() {
        return vTenantId;
    }

    public void setvTenantId(Integer vTenantId) {
        this.vTenantId = vTenantId;
    }

    public Integer getvSiteId() {
        return vSiteId;
    }

    public void setvSiteId(Integer vSiteId) {
        this.vSiteId = vSiteId;
    }

    public Integer getvSiteUserId() {
        return vSiteUserId;
    }

    public void setvSiteUserId(Integer vSiteUserId) {
        this.vSiteUserId = vSiteUserId;
    }

}
