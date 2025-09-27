USE cool_db;

-- Roles
INSERT INTO user_role (user_role_name, dl_required, is_active) VALUES
('Admin', 0, 1),
('Employee', 0, 1),
('Citizen', 1, 1);

-- Device Types
INSERT INTO device_type (device_type_name, is_active) VALUES
('Laptop', 1),
('Tablet', 1),
('Hotspot', 1);

-- Device Statuses
INSERT INTO device_status (device_status_name) VALUES
('Available'),
('Loaned'),
('Maintenance'),
('Retired'),
('Lost');

-- Test Users
INSERT INTO app_user (app_user_full_name, email, password_hash, password_salt, user_role_id)
VALUES ('Test Admin', 'admin@example.com', 'hashed_pw_here', 'salt_here', 1);

INSERT INTO app_user (app_user_full_name, email, password_hash, password_salt, user_role_id)
VALUE ('Test Employee', 'employee@example.com', 'hashed_pw_here', 'salt_here', 2);


-- Test Location
INSERT INTO location (location_name, street_address, city, state, zip_code, contact_phone)
VALUES ('Downtown Community Center', '123 Main St', 'Orlando', 'FL', '32801', '407-555-1234');