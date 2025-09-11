package org.example.cruddevice.controller;

import org.example.cruddevice.entity.Device;
import org.example.cruddevice.service.DeviceService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/my-devices")

public class DeviceController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private DeviceService deviceService;

    @PostMapping("/device")

    public Device saveDevice(
            @Valid @RequestBody Device device)
    {
        return deviceService.saveDevice(device);
    }

    @GetMapping("/device")

    public List<Device> fetchDeviceList()
    {
        return deviceService.fetchDeviceList();
    }

    @PutMapping("/device/{id}")

    public Device
    updateDevice(@RequestBody Device device,
                     @PathVariable("id") Long deviceId)
    {
        return deviceService.updateDevice(
                device, deviceId);
    }

    @DeleteMapping("/device/{id}")

    public String deleteDeviceById(@PathVariable("id") Long deviceId)
    {
        deviceService.deleteDeviceById(deviceId);
        return "Deleted Successfully";
    }
}


