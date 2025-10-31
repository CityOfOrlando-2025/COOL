package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.*;
import com.example.prototypesetup.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private BinRepository binRepository;
    
    @Autowired
    private LoanStatusRepository loanStatusRepository;
    
    @Autowired
    private DeviceConditionRepository deviceConditionRepository;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody CreateLoanRequest request) {
        try {
            if (request.getCitizenId() == null || request.getEmployeeId() == null || 
                request.getDeviceId() == null || request.getStatusId() == null ||
                request.getDueAt() == null || request.getLoanCondition() == null ||
                request.getAllAccessoriesReturned() == null) {
                return ResponseEntity.status(400).body(new ErrorResponse("Missing required fields"));
            }

            Optional<Bin> binOpt = binRepository.findById(request.getDeviceId());
            if (!binOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid device ID"));
            }

            Optional<LoanStatus> statusOpt = loanStatusRepository.findById(request.getStatusId());
            if (!statusOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid status ID"));
            }

            Optional<AppUser> citizenOpt = appUserRepository.findById(request.getCitizenId());
            if (!citizenOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid citizen ID"));
            }

            Optional<AppUser> employeeOpt = appUserRepository.findById(request.getEmployeeId());
            if (!employeeOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid employee ID"));
            }

            Optional<DeviceCondition> conditionOpt = deviceConditionRepository.findById(request.getLoanCondition());
            if (!conditionOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid loan condition ID"));
            }

            Loan loan = new Loan();
            loan.setBin(binOpt.get());             
            loan.setLoanStatus(statusOpt.get());   
            loan.setCitizen(citizenOpt.get());     
            loan.setEmployee(employeeOpt.get());   
            loan.setLoanCondition(conditionOpt.get());

            loan.setStartAt(new Timestamp(System.currentTimeMillis()));
            loan.setDueAt(Timestamp.valueOf(request.getDueAt().atStartOfDay()));
            loan.setLoanConditionNotes(request.getLoanConditionNotes());
            loan.setAllAccessoriesReturned(request.getAllAccessoriesReturned());
            loan.setMissingAccessories(request.getMissingAccessories());
            loan.setNotes(request.getNotes());
            loan.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            loan.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Loan savedLoan = loanRepository.save(loan);
            return ResponseEntity.status(201).body(new SuccessResponse("Loan created successfully", savedLoan));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLoans() {
        try {
            List<Loan> loans = loanRepository.findAll();
            List<LoanResponseDTO> responseDTOs = loans.stream()
                    .map(LoanResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new SuccessResponse("Loans retrieved successfully", responseDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @GetMapping("/{loan_id}")
    public ResponseEntity<?> getLoanById(@PathVariable("loan_id") Integer loan_id) {
        try {
            Optional<Loan> loans = loanRepository.findById(loan_id);
            if (!loans.isPresent()) {
                return ResponseEntity.status(404).body(new ErrorResponse("Loan not found"));
            }
            Loan loan = loans.get();
            
            LoanResponseDTO responseDTO = new LoanResponseDTO(loan);
            return ResponseEntity.ok(new SuccessResponse("Loan retrieved successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @PutMapping("/{loan_id}")
    public ResponseEntity<?> replaceLoan(@PathVariable("loan_id") Integer loan_id, @RequestBody ReplaceLoanRequest request) {
        try {
            Optional<Loan> loanOpt = loanRepository.findById(loan_id);
            if (!loanOpt.isPresent()) {
                return ResponseEntity.status(404).body(new ErrorResponse("Loan not found"));
            }

            Optional<Bin> binOpt = binRepository.findById(request.getDeviceId());
            if (!binOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid device ID"));
            }

            Optional<LoanStatus> statusOpt = loanStatusRepository.findById(request.getStatusId());
            if (!statusOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid status ID"));
            }

            Optional<AppUser> citizenOpt = appUserRepository.findById(request.getCitizenId());
            if (!citizenOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid citizen ID"));
            }

            Optional<AppUser> employeeOpt = appUserRepository.findById(request.getEmployeeId());
            if (!employeeOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid employee ID"));
            }

            Optional<DeviceCondition> loanConditionOpt = deviceConditionRepository.findById(request.getLoanCondition());
            if (!loanConditionOpt.isPresent()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Invalid loan condition ID"));
            }

            Loan loan = loanOpt.get();
            
            loan.setCitizen(citizenOpt.get());
            loan.setEmployee(employeeOpt.get());
            loan.setBin(binOpt.get());
            loan.setLoanStatus(statusOpt.get());
            loan.setLoanCondition(loanConditionOpt.get());
            
            if (request.getReturnCondition() != null) {
                Optional<DeviceCondition> returnConditionOpt = deviceConditionRepository.findById(request.getReturnCondition());
                if (returnConditionOpt.isPresent()) {
                    loan.setReturnCondition(returnConditionOpt.get());
                } else {
                    return ResponseEntity.status(400).body(new ErrorResponse("Invalid return condition ID"));
                }
            } else {
                loan.setReturnCondition(null);
            }

            loan.setStartAt(Timestamp.valueOf(request.getStartAt().atStartOfDay()));
            loan.setDueAt(Timestamp.valueOf(request.getDueAt().atStartOfDay()));
            loan.setReturnedAt(request.getReturnedAt() != null ? Timestamp.valueOf(request.getReturnedAt().atStartOfDay()) : null);
            loan.setLoanConditionNotes(request.getLoanConditionNotes());
            loan.setReturnConditionNotes(request.getReturnConditionNotes());
            loan.setDamageFee(request.getDamageFee());
            loan.setAllAccessoriesReturned(request.getAllAccessoriesReturned());
            loan.setMissingAccessories(request.getMissingAccessories());
            loan.setNotes(request.getNotes());
            loan.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Loan savedLoan = loanRepository.save(loan);
            LoanResponseDTO responseDTO = new LoanResponseDTO(savedLoan);
            return ResponseEntity.ok(new SuccessResponse("Loan replaced successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @PatchMapping("/{loan_id}")
    public ResponseEntity<?> updateLoan(@PathVariable("loan_id") Integer loan_id, @RequestBody UpdateLoanRequest request) {
        try {
            Optional<Loan> loanOpt = loanRepository.findById(loan_id);
            if (!loanOpt.isPresent()) {
                return ResponseEntity.status(404).body(new ErrorResponse("Loan not found"));
            }

            Loan loan = loanOpt.get();
            if (request.getReturnedAt() != null) loan.setReturnedAt(Timestamp.valueOf(request.getReturnedAt().atStartOfDay()));
            
            if (request.getReturnCondition() != null) {
                DeviceCondition returnCondition = new DeviceCondition();
                returnCondition.setDeviceConditionId(request.getReturnCondition());
                loan.setReturnCondition(returnCondition);
            }
            
            if (request.getReturnConditionNotes() != null) loan.setReturnConditionNotes(request.getReturnConditionNotes());
            if (request.getDamageFee() != null) loan.setDamageFee(request.getDamageFee());
            if (request.getAllAccessoriesReturned() != null) loan.setAllAccessoriesReturned(request.getAllAccessoriesReturned());
            if (request.getMissingAccessories() != null) loan.setMissingAccessories(request.getMissingAccessories());
            if (request.getNotes() != null) loan.setNotes(request.getNotes());
            loan.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            loanRepository.save(loan);
            return ResponseEntity.ok(new SuccessResponse("Loan updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @DeleteMapping("/{loan_id}")
    public ResponseEntity<?> deleteLoan(@PathVariable("loan_id") Integer loan_id) {
        try {
            if (!loanRepository.existsById(loan_id)) {
                return ResponseEntity.status(404).body(new ErrorResponse("Loan not found"));
            }
            loanRepository.deleteById(loan_id);
            return ResponseEntity.status(204).body(new SuccessResponse("Loan deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    public static class CreateLoanRequest {
        private Long citizenId;
        private Long employeeId;
        private Integer deviceId;
        private Integer statusId;
        private java.time.LocalDate startAt;
        private java.time.LocalDate dueAt;
        private Integer loanCondition;
        private String loanConditionNotes;
        private Boolean allAccessoriesReturned;
        private String missingAccessories;
        private String notes;

        public Long getCitizenId() { return citizenId; }
        public void setCitizenId(Long citizenId) { this.citizenId = citizenId; }
        public Long getEmployeeId() { return employeeId; }
        public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
        public Integer getDeviceId() { return deviceId; }
        public void setDeviceId(Integer deviceId) { this.deviceId = deviceId; }
        public Integer getStatusId() { return statusId; }
        public void setStatusId(Integer statusId) { this.statusId = statusId; }
        public java.time.LocalDate getStartAt() { return startAt; }
        public void setStartAt(java.time.LocalDate startAt) { this.startAt = startAt; }
        public java.time.LocalDate getDueAt() { return dueAt; }
        public void setDueAt(java.time.LocalDate dueAt) { this.dueAt = dueAt; }
        public Integer getLoanCondition() { return loanCondition; }
        public void setLoanCondition(Integer loanCondition) { this.loanCondition = loanCondition; }
        public String getLoanConditionNotes() { return loanConditionNotes; }
        public void setLoanConditionNotes(String loanConditionNotes) { this.loanConditionNotes = loanConditionNotes; }
        public Boolean getAllAccessoriesReturned() { return allAccessoriesReturned; }
        public void setAllAccessoriesReturned(Boolean allAccessoriesReturned) { this.allAccessoriesReturned = allAccessoriesReturned; }
        public String getMissingAccessories() { return missingAccessories; }
        public void setMissingAccessories(String missingAccessories) { this.missingAccessories = missingAccessories; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class UpdateLoanRequest {
        private java.time.LocalDate returnedAt;
        private Integer returnCondition;
        private String returnConditionNotes;
        private BigDecimal damageFee;
        private Boolean allAccessoriesReturned;
        private String missingAccessories;
        private String notes;

        public java.time.LocalDate getReturnedAt() { return returnedAt; }
        public void setReturnedAt(java.time.LocalDate returnedAt) { this.returnedAt = returnedAt; }
        public Integer getReturnCondition() { return returnCondition; }
        public void setReturnCondition(Integer returnCondition) { this.returnCondition = returnCondition; }
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
    }

    public static class ReplaceLoanRequest {
        private Long citizenId;
        private Long employeeId;
        private Integer deviceId;
        private Integer statusId;
        private java.time.LocalDate startAt;
        private java.time.LocalDate dueAt;
        private java.time.LocalDate returnedAt;
        private Integer loanCondition;
        private String loanConditionNotes;
        private Integer returnCondition;
        private String returnConditionNotes;
        private BigDecimal damageFee;
        private Boolean allAccessoriesReturned;
        private String missingAccessories;
        private String notes;

        public Long getCitizenId() { return citizenId; }
        public void setCitizenId(Long citizenId) { this.citizenId = citizenId; }
        public Long getEmployeeId() { return employeeId; }
        public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
        public Integer getDeviceId() { return deviceId; }
        public void setDeviceId(Integer deviceId) { this.deviceId = deviceId; }
        public Integer getStatusId() { return statusId; }
        public void setStatusId(Integer statusId) { this.statusId = statusId; }
        public java.time.LocalDate getStartAt() { return startAt; }
        public void setStartAt(java.time.LocalDate startAt) { this.startAt = startAt; }
        public java.time.LocalDate getDueAt() { return dueAt; }
        public void setDueAt(java.time.LocalDate dueAt) { this.dueAt = dueAt; }
        public java.time.LocalDate getReturnedAt() { return returnedAt; }
        public void setReturnedAt(java.time.LocalDate returnedAt) { this.returnedAt = returnedAt; }
        public Integer getLoanCondition() { return loanCondition; }
        public void setLoanCondition(Integer loanCondition) { this.loanCondition = loanCondition; }
        public String getLoanConditionNotes() { return loanConditionNotes; }
        public void setLoanConditionNotes(String loanConditionNotes) { this.loanConditionNotes = loanConditionNotes; }
        public Integer getReturnCondition() { return returnCondition; }
        public void setReturnCondition(Integer returnCondition) { this.returnCondition = returnCondition; }
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
    }

    public static class LoanResponseDTO {
        private Integer loan_id;
        private Long citizen_id;
        private Long employee_id;
        private Integer device_id;
        private Integer status_id;
        private String start_at;
        private String due_at;
        private Integer loan_condition;
        private String loan_condition_notes;
        private Boolean all_accessories_returned;
        private String missing_accessories;
        private String notes;

        public LoanResponseDTO(Loan loan) {
            this.loan_id = loan.getLoanId();
            this.citizen_id = loan.getCitizenId();
            this.employee_id = loan.getEmployeeId();
            this.device_id = loan.getBinId();
            this.status_id = loan.getLoanStatusId();
            this.start_at = loan.getStartAt() != null ? loan.getStartAt().toString() : null;
            this.due_at = loan.getDueAt() != null ? loan.getDueAt().toString() : null;
            this.loan_condition = loan.getLoanConditionId();
            this.loan_condition_notes = loan.getLoanConditionNotes();
            this.all_accessories_returned = loan.getAllAccessoriesReturned();
            this.missing_accessories = loan.getMissingAccessories();
            this.notes = loan.getNotes();
        }

        public Integer getLoan_id() { return loan_id; }
        public Long getCitizen_id() { return citizen_id; }
        public Long getEmployee_id() { return employee_id; }
        public Integer getDevice_id() { return device_id; }
        public Integer getStatus_id() { return status_id; }
        public String getStart_at() { return start_at; }
        public String getDue_at() { return due_at; }
        public Integer getLoan_condition() { return loan_condition; }
        public String getLoan_condition_notes() { return loan_condition_notes; }
        public Boolean getAll_accessories_returned() { return all_accessories_returned; }
        public String getMissing_accessories() { return missing_accessories; }
        public String getNotes() { return notes; }
    }

    public static class ErrorResponse {
        private final String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
    }

    public static class SuccessResponse {
        private final String message;
        private final Object data;

        public SuccessResponse(String message) {
            this.message = message;
            this.data = null;
        }

        public SuccessResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() { return message; }
        public Object getData() { return data; }
    }
}