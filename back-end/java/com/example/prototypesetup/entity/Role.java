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

    @Column(name = "dl_required")
    private boolean dlFlag;          // requires Driver's License
    
    @Column(name = "is_active")
    private boolean otherPermFlag;   // extra permissions

    // Getters and setters
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public boolean isDlFlag() { return dlFlag; }
    public void setDlFlag(boolean dlFlag) { this.dlFlag = dlFlag; }

    public boolean isOtherPermFlag() { return otherPermFlag; }
    public void setOtherPermFlag(boolean otherPermFlag) { this.otherPermFlag = otherPermFlag; }
}
