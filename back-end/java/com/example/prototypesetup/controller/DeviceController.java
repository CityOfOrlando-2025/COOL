package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.Device;
import com.example.prototypesetup.entity.AppUser;
import com.example.prototypesetup.repository.DeviceRepository;
import com.example.prototypesetup.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    //GET all devices
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    //GET device by ID
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") Long id) {
        return deviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

 @PostMapping
public ResponseEntity<Device> createDevice(@RequestBody Device device) {
    if (device.getUser() != null) {
        Optional<AppUser> user = appUserRepository.findById(device.getUser().getUserId());
        if (!user.isPresent()) {  // use this instead of isEmpty()
            return ResponseEntity.badRequest().build();
        }
        device.setUser(user.get());
    }
    Device savedDevice = deviceRepository.save(device);
    return ResponseEntity.ok(savedDevice);
}


    //PUT - Update existing device
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(
            @PathVariable("id") Long id,
            @RequestBody Device updatedDevice) {

        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (!optionalDevice.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Device device = optionalDevice.get();
        device.setStatus(updatedDevice.getStatus());
        device.setType(updatedDevice.getType());

        if (updatedDevice.getUser() != null) {
            AppUser user = appUserRepository.findById(updatedDevice.getUser().getUserId()).orElse(null);
            if (user != null) {
                device.setUser(user);
            }
        }

        return ResponseEntity.ok(deviceRepository.save(device));
    }

    //DELETE - Remove device by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("id") Long id) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent()) {
            deviceRepository.delete(optionalDevice.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
