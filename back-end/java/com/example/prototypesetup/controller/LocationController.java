package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.Location;
import com.example.prototypesetup.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    // GET all locations
    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

// DTO class inside the controller (or a separate file)
public static class LocationDTO {
    private Long locationId;
    private String locationName;
    private String address;

      // No-args constructor (needed for Jackson serialization)
    public LocationDTO() {}

    public LocationDTO(Long locationId, String locationName, String address) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.address = address;
    }

    // Getters
    public Long getLocationId() { return locationId; }
    public String getLocationName() { return locationName; }
    public String getAddress() { return address; }

  // Setters
    public void setLocationId(Long locationId) { this.locationId = locationId; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public void setAddress(String address) { this.address = address; }


}


@GetMapping("/{id}")
public ResponseEntity<?> getLocationTest(@PathVariable("id") Long id) {
    Location location = locationRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with id " + id));

    Map<String, Object> result = new HashMap<>();
    result.put("locationId", location.getLocationId());
    result.put("locationName", location.getLocationName());
    result.put("address", location.getAddress());

    return ResponseEntity.ok(result);
}

    // CREATE location
    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

// UPDATE location
@PutMapping("/{id}")
public ResponseEntity<Location> updateLocation(@PathVariable("id") Long id, @RequestBody Location updatedLocation) {
    Location location = locationRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with id " + id));

    location.setLocationName(updatedLocation.getLocationName());
    location.setAddress(updatedLocation.getAddress());

    Location savedLocation = locationRepository.save(location);
    return ResponseEntity.ok(savedLocation);
}

// DELETE location safely
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteLocation(@PathVariable("id") Long id) {
    Location location = locationRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with id " + id));

    // Remove associations with users to avoid foreign key constraint issues
    location.getUsers().forEach(user -> user.getLocations().remove(location));

    locationRepository.delete(location);
    return ResponseEntity.noContent().build(); // HTTP 204 No Content
}

}
