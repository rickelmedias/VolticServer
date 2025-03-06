package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceRawData {

    @JsonProperty("_id")
    private String id; // Unique document identifier

    private String docType = "DeviceRawData"; // distinguishes the document type

    private String deviceId; // optional: to reference the device sending the data

    private Double current; // Amps
    private Long timestamp;

    public DeviceRawData() {
    }

    public DeviceRawData(String id, String deviceId, Double current, Long timestamp) {
        this.id = id;
        this.deviceId = deviceId;
        this.current = current;
        this.timestamp = timestamp;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    // Typically, you don't change docType after creation.

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}