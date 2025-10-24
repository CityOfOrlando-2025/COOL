package com.example.prototypesetup.repository;

import com.example.prototypesetup.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    // Custom finder methods (optional but helpful)
    List<Device> findByStatus(String status);

    List<Device> findByType(String type);
}
