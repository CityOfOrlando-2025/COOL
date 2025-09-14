package org.example.cruddevice.controller;

import org.example.cruddevice.entity.Device;
import org.example.cruddevice.service.DeviceService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/my-devices")

public class DeviceController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationContext context;
    @Autowired private DeviceService deviceService;

    @PostMapping(path="/device", consumes="application/json",produces="application/json")
    public ResponseEntity<Device> saveDevice(
            @RequestBody Device device)
    {
        System.out.println(device);
        device= deviceService.saveDevice(device);
        return ResponseEntity.status(200).body(device);
    }

    @GetMapping("/device")

    public ResponseEntity<List<Device>> fetchDeviceList()
    {
        return ResponseEntity.status(200).body((deviceService.fetchDeviceList()));
    }

    //@PutMapping("/device/{id}")
    @PutMapping(path="/device/{id}", consumes="application/json",produces="application/json")
    public ResponseEntity<Device>
    updateDevice(@RequestBody Device device,
                     @PathVariable("id") Long deviceId)
    {
        device= deviceService.updateDevice(
                device, deviceId);

        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/device/{id}")

    public String deleteDeviceById(@PathVariable("id") Long deviceId)
    {
        deviceService.deleteDeviceById(deviceId);
        return "Deleted Successfully";
    }
}
