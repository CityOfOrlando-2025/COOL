-- ===================================================
-- Seed Data for COOL: City of Orlando Loaners
-- ===================================================
-- Purpose: Populate the database with initial data for development and testing.
-- Execution: Automatically runs when Docker container is created.
-- Order: Follows dependency chain (lookups, core entities, relationships).
-- ===================================================

USE cool_db;

-- ==================================================
-- SECTION 1: LOOKUP TABLES
-- ==================================================
-- These tables define the allowed values for various system attributes.
-- They must be populated first since the core entity tables depend on them.
-- ==================================================

-- ------------------------------------------------
-- 1.1 User Roles
-- ------------------------------------------------
-- Defines the three types of users in the system: Admin, Employee, Citizen.
INSERT INTO user_role (user_role_name, dl_required, is_active) VALUES
('Admin', 0, 1),    -- (no DL required)
('Employee', 0, 1), -- (no DL required) 
('Citizen', 1, 1);  -- (DL required)

-- ------------------------------------------------
-- 1.2 Device Types
-- ------------------------------------------------
-- Defines the categories of devices available for loan.
INSERT INTO device_type (device_type_name, is_active) VALUES
('Tablet', 1),
('Laptop', 1),
('Hotspot', 1);

-- ------------------------------------------------
-- 1.3 Device Statuses
-- ------------------------------------------------
-- Defines the current availability/status of devices.
INSERT INTO device_status (device_status_name) VALUES
('Available'),
('Loaned'),
('Maintenance'),
('Retired'),
('Lost');

-- ------------------------------------------------
-- 1.4 Device Conditions
-- ------------------------------------------------
-- Defines the physical conition of devices.
INSERT INTO device_condition (device_condition_name) VALUES
('Excellent'),
('Good'),
('Fair'),
('Poor'),
('Damaged');

-- ------------------------------------------------
-- 1.5 Loan Statuses
-- ------------------------------------------------
-- Defines the lifecycle states for loan transactions.
INSERT INTO loan_status (loan_status_name) VALUES
('Open'),        -- Currently loaned out
('Returned'),    -- Successfully returned
('Overdue'),     -- Past due date
('Lost'),        -- Reported lost
('Cancelled');   -- Loan cancelled before pickup

-- ------------------------------------------------
-- 1.6 User Action Types
-- ------------------------------------------------
-- Defines the CRUD operations logged in action_log.
INSERT INTO user_action_type (user_action_type_name, is_active) VALUES
('CREATE', 1),
('READ', 1),
('UPDATE', 1),
('DELETE', 1);

-- ------------------------------------------------
-- 1.7 Loan Action Types
-- ------------------------------------------------
-- Defines the specific actions that can occur during a loan.
INSERT INTO loan_action_type (loan_action_name, is_active) VALUES
('Checkout', 1), -- Device checked out to citizen
('Return', 1), -- Device returned by citizen
('Status_Change', 1); -- Manual status update (e.g., marked lost)

-- ------------------------------------------------
-- 1.8 Transaction Statuses
-- ------------------------------------------------
-- Defines the outcome of logged actions
INSERT INTO transaction_status (transaction_status_name) VALUES
('Success'), -- Action completed successfully
('Failure'), -- Action failed
('Pending'); -- Action in progress

-- ==================================================
-- SECTION 2: CORE ENTITY TABLES
-- ==================================================
-- These tables store the primary business data. 
-- They depend on lookkup tables for foreign key references.
-- ==================================================

-- ------------------------------------------------
-- 2.1 Users
-- ------------------------------------------------
-- Test users for each role type

-- Admin user 
INSERT INTO app_user (
    app_user_full_name, 
    email, 
    password_hash, 
    user_role_id
)
VALUES (
    'Test Admin', 
    'admin@workemail.com', 
    'hashed_pw_here', 
    1 -- Admin role
);

-- Employee user
INSERT INTO app_user (
    app_user_full_name, 
    email, 
    password_hash, 
    user_role_id
)
VALUES (
    'Test Employee', 
    'employee@workemail.com', 
    'hashed_pw_here', 
    2 -- Employee role
);

-- Test user (borrower with full contact info)
INSERT INTO app_user (
    app_user_full_name,
    email,
    password_hash,
    user_role_id,
    dl_num,
    dl_state,
    street_address,
    city,
    state,
    zip_code,
    date_of_birth,
    contact_number
)
VALUES (
    'Test Citizen',
    'testcitizen@personalemail.com',
    'hashed_pw_here',
    3, -- Citizen role
    'D1234567',
    'FL',
    '123 Main St',
    'Orlando',
    'FL',
    '32801',
    '1990-01-01',
    '407-123-4567'
);

-- Test Location
INSERT INTO location (
    location_name, 
    street_address, 
    city, 
    state, 
    zip_code, 
    contact_number
)
VALUES (
    'Callahan Neighborhood Center', 
    '101 N. Parramore Ave Ste. 1713', 
    'Orlando', 
    'FL', 
    '32801', 
    '407-246-4442'
);