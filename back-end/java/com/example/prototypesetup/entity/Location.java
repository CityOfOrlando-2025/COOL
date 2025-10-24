package com.example.prototypesetup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id") // maps to MySQL column
    private Long locationId;

    @Column(nullable = false, unique = true, length = 100)
    private String locationName;

    private String address;

    // Prevent infinite recursion when serializing
    @ManyToMany(mappedBy = "locations")
    @JsonIgnore
    private Set<AppUser> users = new HashSet<>();

    // Getters and setters
    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Set<AppUser> getUsers() { return users; }
    public void setUsers(Set<AppUser> users) { this.users = users; }
}
