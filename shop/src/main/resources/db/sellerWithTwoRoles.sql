-- Inserting two shops
INSERT INTO shop (id, shop_name, description, location, location_description, contact_info, created_at, updated_at)
VALUES
    ('123e4567-e89b-12d3-a456-426655440000', 'Seafood Delight', 'A shop specializing in seafood.', 'Location1', 'Near the coast', '123456789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('123e4567-e89b-12d3-a456-426655440001', 'Ocean's Bounty', 'Fresh ocean produce.', 'Location2', 'Downtown by the pier', '987654321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserting roles for a user in these shops
INSERT INTO shop_roles (id, shop_id, user_id, role)
VALUES
('123e4567-e89b-12d3-a456-426655440002', '123e4567-e89b-12d3-a456-426655440000', '123e4567-e89b-12d3-a456-426655440003', 'OWNER'),
('123e4567-e89b-12d3-a456-426655440004', '123e4567-e89b-12d3-a456-426655440001', '123e4567-e89b-12d3-a456-426655440003', 'STAFF');
