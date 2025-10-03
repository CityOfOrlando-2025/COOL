-- ===================================================
-- Seed Data for COOL: City of Orlando Loaners
-- ===================================================
-- Purpose: Populate the database with initial data for development and testing.
-- Execution: Automatically runs when Docker container is created.
-- Order: Follows dependency chain (lookups, core entities, relationships).
-- ===================================================

USE cool_db;

-- Section 1: Lookup Tables
-- These tables define the allowed values for various system attributes.
-- They must be populated first since the core entity tables depend on them.

-- 1.1 User Roles
-- Defines the three types of users in the system: Admin, Employee, Citizen.
INSERT INTO user_role (user_role_name, dl_required, is_active) VALUES
('Admin', 0, 1),    -- (no DL required)
('Employee', 0, 1), -- (no DL required) 
('Citizen', 1, 1);  -- (DL required)

-- 1.2 Device Types
-- Defines the categories of devices available for loan.
INSERT INTO device_type (device_type_name, is_active) VALUES
('Laptop', 1),
('Tablet', 1),
('Hotspot', 1);

-- 1.3 Device Statuses
-- Defines the current availability/status of devices.
INSERT INTO device_status (device_status_name) VALUES
('Available'),
('Loaned'),
('Maintenance'),
('Retired'),
('Lost');

-- 1.4 Device Conditions
-- Defines the physical conition of devices.
INSERT INTO device_condition (device_condition_name) VALUES
('Excellent'),
('Good'),
('Fair'),
('Poor')
('Damaged');

-- 1.5 Loan Statuses
-- Defines the lifecycle states for loan transactions.
INSERT INTO loan_status (loan_status_name) VALUES
('Open'),        -- Currently loaned out
('Returned'),    -- Successfully returned
('Overdue'),     -- Past due date
('Lost'),        -- Reported lost
('Cancelled');   -- Loan cancelled before pickup

-- 1.6 User Action Types
-- Defines the CRUD operations logged in action_log.
INSERT INTO

-- Test Users "Admin" and "Employee"
INSERT INTO app_user (
    app_user_full_name, 
    email, 
    password_hash, 
    password_salt, 
    user_role_id
)
VALUES (
    'Test Admin', 
    'admin@workemail.com', 
    'hashed_pw_here', 
    'salt_here', 
    1 -- Admin role
);

INSERT INTO app_user (
    app_user_full_name, 
    email, 
    password_hash, 
    password_salt, 
    user_role_id
)
VALUES (
    'Test Employee', 
    'employee@workemail.com', 
    'hashed_pw_here', 
    'salt_here', 
    2 -- Employee role
);

-- Test Citizen with full details
INSERT INTO app_user (
    app_user_full_name,
    email,
    password_hash,
    password_salt,
    user_role_id,
    dl_num,
    dl_state,
    street_address,
    city,
    state,
    zip_code,
    date_of_birth,
    phone_number
)
VALUES (
    'Test Citizen',
    'testcitizen@personalemail.com',
    'hashed_pw_here',
    'salt_here',
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
    contact_phone
)
VALUES (
    'Callahan Neighborhood Center', 
    '101 N. Parramore Ave Ste. 1713', 
    'Orlando', 
    'FL', 
    '32801', 
    '407-246-4442'
);