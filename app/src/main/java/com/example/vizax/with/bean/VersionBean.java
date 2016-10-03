package com.example.vizax.with.bean;

public class VersionBean {
    private Integer versionId;

    private Integer versionCode;

    private Double versionName;

    private String versionContent;

    private Integer versionForceUpdate;

    private String versionUrl;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Double getVersionName() {
        return versionName;
    }

    public void setVersionName(Double versionName) {
        this.versionName = versionName;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent == null ? null : versionContent.trim();
    }

    public Integer getVersionForceUpdate() {
        return versionForceUpdate;
    }

    public void setVersionForceUpdate(Integer versionForceUpdate) {
        this.versionForceUpdate = versionForceUpdate;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl == null ? null : versionUrl.trim();
    }
}