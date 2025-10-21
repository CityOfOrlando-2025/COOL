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
-- They depend on lookup tables for foreign key references.
-- ==================================================

-- ------------------------------------------------
-- 2.1 Users
-- ------------------------------------------------
-- Test users for each role type

-- Admin users
INSERT INTO app_user (app_user_full_name, email, password_hash, user_role_id)
VALUES 
    ('Dev Admin', 'dev@workemail.com', 'hashed_pw_here', 1), -- Dev Admin role (1)
    ('Mgr Admin', 'admin@workemail.com', 'hashed_pw_here', 1), -- Mgr Admin role (1)
    ('Test Employee', 'emp1@workemail.com', 'hashed_pw_here', 2), -- Employee role (2)
    ('Second Employee', 'emp2@workemail.com', 'hashed_pw_here', 2); -- Employee role (2)

-- Citizen user (borrowers with full contact info)
INSERT INTO app_user (app_user_full_name, email, password_hash, user_role_id, dl_num, dl_state, street_address, city, state, zip_code, date_of_birth, contact_number)
VALUES 
    ('Alex Martinez', 'alex.martinez@personalemail.com', 'hashed_pw_here', 3, 'D1234567', 'FL', '500 W Livingston St', 'Orlando', 'FL', '32801', '1992-03-14', '407-555-1010'), -- Citizen role (3)
    ('Jamie Nguyen', 'jamie.nguyen@personalemail.com', 'hashed_pw_here', 3, 'D2345678', 'FL', '701 N Econlockhatchee Trl', 'Orlando', 'FL', '32825', '1994-07-22', '407-555-2020'), 
    ('Taylor Johnson', 'taylor.johnson@personalemail.com', 'hashed_pw_here', 3, 'D3456789', 'FL', '12350 Narcoossee Rd', 'Orlando', 'FL', '32832', '1990-11-05', '407-555-3030'),   
    ('Casey Rivera', 'casey.rivera@personalemail.com', 'hashed_pw_here', 3, 'D4567890', 'FL', '3255 Pleasant Hill Rd', 'Kissimmee', 'FL', '34746', '1996-05-18', '407-555-4040');   
    ('Morgan Reyes', 'morgan.reyes@personalemail.com', 'hashed_pw_here', 3, 'D5678901', 'FL', '4000 Central Florida Blvd', 'Orlando', 'FL', '32816', '1998-09-10', '407-555-5050'),
   -- Under 18 (not eligible to borrow)
    ('Riley Carter', 'riley.carter@personalemail.com', 'hashed_pw_here', 3, 'D6789012', 'FL', '500 W Livingston St', 'Orlando', 'FL', '32801', '2010-04-15', '407-555-6060');

-- ------------------------------------------------
-- 2.2 Locations
-- ------------------------------------------------
-- Community center where devices are stored and loaned

-- Test Location
INSERT INTO location (location_name, street_address, city, state, zip_code, contact_number)
VALUES 
    ('Callahan Neighborhood Center', '101 N Parramore Ave Suite 1713', 'Orlando', 'FL', '32801', '407-246-4442'),
    ('Hankins Park Neighborhood Center', '1340 Lake Park Ct', 'Orlando', 'FL', '32805', '407-246-4455'),
    ('Northwest Neighborhood Center', '3955 W D Judge Dr', 'Orlando', 'FL', '32808', '407-246-4465'),
    ('Rosemont Neighborhood Center', '4872 Rose Bay Dr', 'Orlando', 'FL', '32808', '407-246-4475'),
    ('Smith Neighborhood Center', '1723 Bruton Blvd', 'Orlando', 'FL', '32805', '407-246-4477'),
    ('Citrus Square Neighborhood Center', '5624 Hickey Drive', 'Orlando', 'FL', '32822', '407-246-4445'),
    ('Engelwood Neighborhood Center', '6123 La Costa Dr Suite 2931', 'Orlando', 'FL', '32807', '407-246-4453'),
    ('Jackson Neighborhood Center', '1002 Carter St', 'Orlando', 'FL', '32805', '407-246-4459'),
    ('L Claudia Allen Senior Center', '1840 Mable Butler Ave Suite 4261', 'Orlando', 'FL', '32805', '407-246-4461'),
    ('Grand Avenue Neighborhood Center', '800 Grand St', 'Orlando', 'FL', '32805', '407-246-4467'),
    ('Ivey Lane Neighborhood Center', '5151 Raleigh St Ste C', 'Orlando', 'FL', '32811', '407.246.4457'),
    ('Langford Park Neighborhood Center', '1808 E Central Blvd', 'Orlando', 'FL', '32803', '407-246-4471'),
    ('Rock Lake Neighborhood Center', '440 N Tampa Ave', 'Orlando', 'FL', '32805', '407-246-4473'),
    ('Wadeview Neighborhood Center', '2177 S Summerlin Ave', 'Orlando', 'FL', '32806', '407-246-4479'),
    ('Dover Shores Neighborhood Center', '1400 Gaston Foster Rd', 'Orlando', 'FL', '32812', '407-246-4451'),
    ('Hispanic Office for Local Assistance', '595 North Primrose Drive', 'Orlando', 'FL', '32803', '407-246-4310');

-- ------------------------------------------------
-- 2.3 bins
-- ------------------------------------------------
-- Storage bins within locations for organizing devices
INSERT INTO bin (asset_tag, bin_contents, created_by_user_id, location_id)
VALUES 
('BIN-0001', 'Laptop + Hotspot', 2, 1),
('BIN-0002', 'Tablet + Hotspot', 2, 2),
('BIN-0003', 'Laptop', 2, 3),
('BIN-0004', 'Tablet', 1, 5),
('BIN-0005', 'Hotspot', 1, 4);

-- ------------------------------------------------
-- 2.4 Devices
-- ------------------------------------------------
-- Sample devices
-- Tablets are device_type_id = 1, Laptops = 2, Hotspots = 3, tablets + hotspots =1 & 3 etc.
INSERT INTO device (device_name, device_type_id, serial_number, device_status_id, location_id, created_by_user_id)
VALUES
('Lenovo Tablet Series A', 1, 'TAB-001', 1, 1, 3), -- Available Tablet
('Dell Laptop Series A', 2, 'LAP-001', 1, 1, 3), -- Available Laptop
('Lenovo Tablet Series B', 1, 'TAB-002', 1, 1, 3),  -- Available Tablet
('Dell Laptop Series B',    2, 'LAP-002', 3, 3, 3),  -- Maintenance Laptop
('Verizon Hotspot Series A', 3, 'HOT-001', 1, 1, 4),  -- Available Hotspot
('Dell Laptop Series C',      2, 'LAP-003', 2, 2, 4),  -- Loaned Laptop
('Samsung Tablet Series C',   1, 'TAB-003', 4, 2, 3),  -- Retired Tablet
('Verizon Hotspot Series B', 3,'HOT-002', 1, 2, 3),  -- Available Hotspot

-- ------------------------------------------------
-- 2.5 Bin-Device Relationships
-- -------------------------------------------------
-- NOTE: After testing MVP 1, change to subquery dynamically linked devices to bins 
INSERT INTO bin_device (bin_id, device_id) 
VALUES
(1, 1),  -- BIN-0001 holds Lenovo Tablet Series A
(1, 2),  -- BIN-0001 holds Dell Laptop Series A
(2, 3),  -- BIN-0002 holds Lenovo Tablet Series B
(2, 8),  -- BIN-0002 holds Verizon Hotspot Series B
(3, 4),  -- BIN-0003 holds Dell Laptop Series B (Maintenance)
(4, 7),  -- BIN-0004 holds Samsung Tablet Series C (Retired)
(5, 5);  -- BIN-0005 holds Verizon Hotspot Series A

-- =================================================
-- END OF SEED DATA 
-- =================================================
-- Password hashes here are only placeholders. 
-- Actual hashed passwords should be handled at the application level. 

-- Follow the insert order (lookups first, then core entities, then relationships) when adding additional test data.

-- MVP 2 
-- Goals are to have subqueries for foreign keys to avoid hardcoding IDs
-- Add more test users, locations, devices, and loans as needed for testing.
-- =================================================