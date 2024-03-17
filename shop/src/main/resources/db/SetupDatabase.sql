DROP TABLE IF EXISTS shop_owners CASCADE;
DROP TABLE IF EXISTS shop_roles CASCADE;
DROP TABLE IF EXISTS opening_hours CASCADE;
DROP TABLE IF EXISTS shop CASCADE;

-- Create 'shop' table
CREATE TABLE shop (
                      id UUID PRIMARY KEY,
                      shop_name VARCHAR(20) NOT NULL,
                      description VARCHAR(200),
                      location VARCHAR(255),
                      location_description VARCHAR(200),
                      contact_info VARCHAR(255),
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP
);

-- Create 'opening_hours' table
CREATE TABLE opening_hours (
                               id UUID PRIMARY KEY, -- Use UUID as primary key
                               date DATE,
                               start_time TIME,
                               end_time TIME,
                               notes VARCHAR(200),
                               shop_id UUID REFERENCES shop(id) -- Foreign key to 'shop' table
);

-- not sure if this one under works

INSERT INTO shop (id, shop_name, description, location, location_description, contact_info, created_at, updated_at)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 'Seaside Fish Market', 'Fresh seafood directly from the sea', 'Waterfront Dock', 'Next to the old lighthouse', 'contact@seasidefish.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('234f5678-fa9c-23e4-b567-527725174001', 'Ocean Delights', 'Wide variety of oceanic delights', 'Marina Bay', 'Opposite Marina Park', 'info@oceandelights.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('345a6789-fb0d-34f5-c678-638836275002', 'Harbor Fish Co', 'The best fish in town', 'Harbor Avenue', 'Near the Harbor Bridge', 'sales@harborfishco.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('456b789a-1c2e-45f6-d789-749947376003', 'Blue Wave Seafoods', 'Delicious seafood from blue waters', 'Blue Beach', 'Blue Beach Entrance', 'support@bluewaveseafoods.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('567c891b-2d3f-56f7-e890-85a058478004', 'Mystic Marine', 'Exotic seafood from mysterious depths', 'Mystic Street', 'End of Mystic Street', 'contact@mysticmarine.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
