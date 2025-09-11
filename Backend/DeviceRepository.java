package org.example.cruddevice.repository;

import org.example.cruddevice.entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {
}
