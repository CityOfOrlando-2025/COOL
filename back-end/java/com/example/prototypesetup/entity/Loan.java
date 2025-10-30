package com.example.prototypesetup.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Integer loanId;

    @Column(name = "bin_id", nullable = false)
    private Integer binId;

    @Column(name = "loan_status_id", nullable = false)
    private Integer loanStatusId;

    @Column(name = "citizen_id", nullable = false)
    private Long citizenId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "start_at", nullable = false)
    private Timestamp startAt;

    @Column(name = "due_at", nullable = false)
    private Timestamp dueAt;

    @Column(name = "returned_at")
    private Timestamp returnedAt;

    @Column(name = "loan_condition_id", nullable = false)
    private Integer loanConditionId;

    @Column(name = "loan_condition_notes", columnDefinition = "TEXT")
    private String loanConditionNotes;

    @Column(name = "return_condition_id")
    private Integer returnConditionId;

    @Column(name = "return_condition_notes", columnDefinition = "TEXT")
    private String returnConditionNotes;

    @Column(name = "damage_fee", precision = 10, scale = 2)
    private BigDecimal damageFee;

    @Column(name = "all_accessories_returned")
    private Boolean allAccessoriesReturned;

    @Column(name = "missing_accessories", columnDefinition = "TEXT")
    private String missingAccessories;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bin_id", insertable = false, updatable = false)
    private Bin bin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_status_id", insertable = false, updatable = false)
    private LoanStatus loanStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", insertable = false, updatable = false)
    private AppUser citizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private AppUser employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_condition_id", insertable = false, updatable = false)
    private DeviceCondition loanCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_condition_id", insertable = false, updatable = false)
    private DeviceCondition returnCondition;


    public Loan() {}

    public Integer getLoanId() { return loanId; }
    public void setLoanId(Integer loanId) { this.loanId = loanId; }

    public Integer getBinId() { return binId; }
    public void setBinId(Integer binId) { this.binId = binId; }

    public Integer getLoanStatusId() { return loanStatusId; }
    public void setLoanStatusId(Integer loanStatusId) { this.loanStatusId = loanStatusId; }

    public Long getCitizenId() { return citizenId; }
    public void setCitizenId(Long citizenId) { this.citizenId = citizenId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Timestamp getStartAt() { return startAt; }
    public void setStartAt(Timestamp startAt) { this.startAt = startAt; }

    public Timestamp getDueAt() { return dueAt; }
    public void setDueAt(Timestamp dueAt) { this.dueAt = dueAt; }

    public Timestamp getReturnedAt() { return returnedAt; }
    public void setReturnedAt(Timestamp returnedAt) { this.returnedAt = returnedAt; }

    public Integer getLoanConditionId() { return loanConditionId; }
    public void setLoanConditionId(Integer loanConditionId) { this.loanConditionId = loanConditionId; }

    public String getLoanConditionNotes() { return loanConditionNotes; }
    public void setLoanConditionNotes(String loanConditionNotes) { this.loanConditionNotes = loanConditionNotes; }

    public Integer getReturnConditionId() { return returnConditionId; }
    public void setReturnConditionId(Integer returnConditionId) { this.returnConditionId = returnConditionId; }

    public String getReturnConditionNotes() { return returnConditionNotes; }
    public void setReturnConditionNotes(String returnConditionNotes) { this.returnConditionNotes = returnConditionNotes; }

    public BigDecimal getDamageFee() { return damageFee; }
    public void setDamageFee(BigDecimal damageFee) { this.damageFee = damageFee; }

    public Boolean getAllAccessoriesReturned() { return allAccessoriesReturned; }
    public void setAllAccessoriesReturned(Boolean allAccessoriesReturned) { this.allAccessoriesReturned = allAccessoriesReturned; }

    public String getMissingAccessories() { return missingAccessories; }
    public void setMissingAccessories(String missingAccessories) { this.missingAccessories = missingAccessories; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public Bin getBin() { return bin; }
    public void setBin(Bin bin) { this.bin = bin; }

    public LoanStatus getLoanStatus() { return loanStatus; }
    public void setLoanStatus(LoanStatus loanStatus) { this.loanStatus = loanStatus; }

    public AppUser getCitizen() { return citizen; }
    public void setCitizen(AppUser citizen) { this.citizen = citizen; }

    public AppUser getEmployee() { return employee; }
    public void setEmployee(AppUser employee) { this.employee = employee; }

    public DeviceCondition getLoanCondition() { return loanCondition; }
    public void setLoanCondition(DeviceCondition loanCondition) { this.loanCondition = loanCondition; }

    public DeviceCondition getReturnCondition() { return returnCondition; }
    public void setReturnCondition(DeviceCondition returnCondition) { this.returnCondition = returnCondition; }
}