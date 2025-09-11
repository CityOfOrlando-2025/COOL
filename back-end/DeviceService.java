package org.example.cruddevice.service;

import org.example.cruddevice.entity.Device;
import java.util.List;

public interface DeviceService {
    Device saveDevice(Device device);

    List<Device> fetchDeviceList();

    Device updateDevice(Device device, Long deviceId);

    void deleteDeviceById(Long deviceId);
}


