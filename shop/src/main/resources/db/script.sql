-- Define a new UUID for Harald's shop
INSERT INTO shop (id, shop_name, description, location, location_description, contact_info, created_at, updated_at)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 'Haralds', 'A shop selling a variety of seafood', 'Oslo, Norway', 'Located in the heart of the city', 'contact@haraldsfishmarket.no', NOW(), NOW());

-- Insert Harald as the owner of the new shop
INSERT INTO shop_owners (id, shop_id, user_id, role)
VALUES
    ('6345084b-084d-473a-9465-e74ac2ab5a6b', '123e4567-e89b-12d3-a456-426614174000', '6345084b-084d-473a-9465-e74ac2ab5a6b', 'OWNER');
