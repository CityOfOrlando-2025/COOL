package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.Role;
import com.example.prototypesetup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@RequestMapping("/api/user_roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    // GET all roles----------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> roles = roleRepository.findAll();
            List<Map<String, Object>> roleList = new ArrayList<>();

            for (Role role : roles) {
                Map<String, Object> item = new HashMap<>();
                item.put("user_role_id", role.getRoleId());
                item.put("user_role_name", role.getRoleName());
                item.put("dl_required", role.getDlRequired());
                item.put("is_active", role.getIsActive());
                roleList.add(item);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "User roles retrieved successfully");
            response.put("roles", roleList);

            return ResponseEntity.ok(response);

        }catch (Exception e){
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "An unexpected error has occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody);
        }

    }

    // GET role by ID safely--------------------------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id " + id));

        Map<String, Object> result = new HashMap<>();
        result.put("user_role_id", role.getRoleId());
        result.put("user_role_name", role.getRoleName());
        result.put("dl_required", role.getDlRequired());
        result.put("is_active",role.getIsActive());


        return ResponseEntity.ok(result);
    }

    // CREATE role------------------------------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role newRole) {
        try {
         Role savedRole = roleRepository.save(newRole);

         Map<String, Object> result = new HashMap<>();
         result.put("user_role_id", savedRole.getRoleId());
         result.put("user_role_name", savedRole.getRoleName());
         result.put("dl_required", savedRole.getDlRequired());
         result.put("is_active", savedRole.getIsActive());

         return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } catch (Exception e){
           Map<String, Object> errorBody= new HashMap<>();
           errorBody.put("error", "An unexpected error has occurred.");
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    // UPDATE role -----------------------------------------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @RequestBody Role updatedRole) {
        try{
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id " + id));

        role.setRoleName(updatedRole.getRoleName());
        role.setDlRequired(updatedRole.getDlRequired());
        role.setIsActive(updatedRole.getIsActive());

        Role saved = roleRepository.save(role);

        Map<String, Object> result = new HashMap<>();
        result.put("user_role_id", saved.getRoleId());
        result.put("user_role_name", saved.getRoleName());
        result.put("dl_required",saved.getDlRequired());
        result.put("is_active", saved.getIsActive());

        return ResponseEntity.ok(result);

    } catch (Exception e){
            Map<String, Object> errorBody= new HashMap<>();
            errorBody.put("error", "Failed to update user role.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    // DELETE role safely ----------------------------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id) {
        try {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id " + id));

        roleRepository.delete(role);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 204);
        result.put("message", "Role deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);

    } catch (Exception e){
        Map<String, Object> errorBody= new HashMap<>();
        errorBody.put("error", "Failed to delete user role.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }
}
