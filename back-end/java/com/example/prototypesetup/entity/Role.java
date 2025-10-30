package com.example.prototypesetup.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long roleId;

    @Column(name = "user_role_name", nullable = false, unique = true, length = 50)
    private String roleName;

    @Column(name = "dl_required", nullable = false) // requires Driver’s License?
    private boolean dlRequired;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    private boolean otherPermFlag;   // extra permissions

    // Getters and setters
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }


    public boolean getDlRequired() { return dlRequired; }
    public void setDlRequired(boolean dlRequired) { this.dlRequired = dlRequired; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public boolean isOtherPermFlag() { return otherPermFlag; }
    public void setOtherPermFlag(boolean otherPermFlag) { this.otherPermFlag = otherPermFlag; }
}
