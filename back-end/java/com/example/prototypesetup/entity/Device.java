package com.example.prototypesetup.entity;

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
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    // Foreign key relation to AppUser table
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
