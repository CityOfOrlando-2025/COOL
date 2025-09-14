package org.example.cruddevice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="deviceId")
    private long deviceId;

    private String status;
    private String type;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String deviceStatus) {
        this.status = deviceStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String deviceType) {
        this.type = deviceType;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
