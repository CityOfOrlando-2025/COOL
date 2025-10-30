package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.Loan;
import com.example.prototypesetup.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody CreateLoanRequest request) {
        try {
            Loan loan = new Loan();
            loan.setCitizenId(request.getCitizenId());
            loan.setEmployeeId(request.getEmployeeId());
            loan.setBinId(request.getDeviceId());
            loan.setLoanStatusId(request.getStatusId());
            loan.setStartAt(new Timestamp(System.currentTimeMillis()));
            loan.setDueAt(Timestamp.valueOf(request.getDueAt().atStartOfDay()));
            loan.setLoanConditionId(request.getLoanCondition());
            loan.setLoanConditionNotes(request.getLoanConditionNotes());
            loan.setAllAccessoriesReturned(request.getAllAccessoriesReturned());
            loan.setMissingAccessories(request.getMissingAccessories());
            loan.setNotes(request.getNotes());

            loanRepository.save(loan);
            return ResponseEntity.status(201).body(new SuccessResponse("Loan created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLoans() {
        try {
            List<Loan> loans = loanRepository.findAll();
            return ResponseEntity.ok(new SuccessResponse("Loans retrieved successfully", loans));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("An unexpected error has occurred."));
        }
    }

    @GetMapping("/{loan_id}")
    public ResponseEntity<?> getLoanById(@PathVariable("loan_id") Integer loan_id) {
        try {
            Optional<Loan> loanOpt = loanRepository.findById(loan_id);
            if (!loanOpt.isPresent()) {
                return ResponseEntity.status(404).body(new ErrorResponse("Loan not found"));
            }
            Loan loan = loanOpt.get();
            return ResponseEntity.ok(new SuccessResponse("Loan retrieved successfully", loan));
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

            Loan loan = loanOpt.get();
            loan.setCitizenId(request.getCitizenId());
            loan.setEmployeeId(request.getEmployeeId());
            loan.setBinId(request.getDeviceId());
            loan.setLoanStatusId(request.getStatusId());
            loan.setStartAt(Timestamp.valueOf(request.getStartAt().atStartOfDay()));
            loan.setDueAt(Timestamp.valueOf(request.getDueAt().atStartOfDay()));
            loan.setLoanConditionId(request.getLoanCondition());
            loan.setLoanConditionNotes(request.getLoanConditionNotes());
            loan.setReturnConditionId(request.getReturnCondition());
            loan.setReturnConditionNotes(request.getReturnConditionNotes());
            loan.setDamageFee(request.getDamageFee());
            loan.setAllAccessoriesReturned(request.getAllAccessoriesReturned());
            loan.setMissingAccessories(request.getMissingAccessories());
            loan.setNotes(request.getNotes());

            loanRepository.save(loan);
            return ResponseEntity.ok(new SuccessResponse("Loan replaced successfully"));
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
            if (request.getReturnCondition() != null) loan.setReturnConditionId(request.getReturnCondition());
            if (request.getReturnConditionNotes() != null) loan.setReturnConditionNotes(request.getReturnConditionNotes());
            if (request.getDamageFee() != null) loan.setDamageFee(request.getDamageFee());
            if (request.getAllAccessoriesReturned() != null) loan.setAllAccessoriesReturned(request.getAllAccessoriesReturned());
            if (request.getMissingAccessories() != null) loan.setMissingAccessories(request.getMissingAccessories());
            if (request.getNotes() != null) loan.setNotes(request.getNotes());

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