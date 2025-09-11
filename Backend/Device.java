package org.example.cruddevice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deviceId;
    private static String status;
    private String type;

    public static String getDeviceStatus() {
        return status;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.status = deviceStatus;
    }

    public String getDeviceType() {
        return type;
    }

    public void setDeviceType(String deviceType) {
        this.type = deviceType;
    }

}
