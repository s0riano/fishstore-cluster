-- Drop existing tables if they exist
DROP TABLE IF EXISTS sale CASCADE;
DROP TABLE IF EXISTS "catch" CASCADE;

-- Create catch table
CREATE TABLE "catch" (
                         catch_id UUID PRIMARY KEY,
                         shop_id UUID NOT NULL, -- Assuming this ID is managed externally
                         seafood_type VARCHAR(255) NOT NULL,
                         kilos NUMERIC(10, 2) NOT NULL,
                         catch_date DATE NOT NULL,
                         expiry_date DATE NOT NULL,
                         last_modified TIMESTAMP NOT NULL
);

-- Insert multiple catches for the provided shop ID with different seafood types, including two catches of SALMON
INSERT INTO "catch" (catch_id, shop_id, seafood_type, kilos, catch_date, expiry_date, last_modified) VALUES
                                                                                                         ('c1e4567e-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174000', 'SALMON', 100.00, '2023-01-01', '2023-03-01', NOW()),
                                                                                                         ('c1e4567e-e89b-12d3-a456-426614174007', '123e4567-e89b-12d3-a456-426614174000', 'SALMON', 150.00, '2023-02-01', '2023-04-01', NOW()),
                                                                                                         ('c2e4567e-e89b-12d3-a456-426614174002', '123e4567-e89b-12d3-a456-426614174000', 'COD', 60.00, '2023-01-05', '2023-03-05', NOW()),
                                                                                                         ('c3e4567e-e89b-12d3-a456-426614174003', '123e4567-e89b-12d3-a456-426614174000', 'TUNA', 200.00, '2023-01-10', '2023-03-10', NOW()),
                                                                                                         ('c4e4567e-e89b-12d3-a456-426614174004', '123e4567-e89b-12d3-a456-426614174000', 'SHRIMP', 120.00, '2023-01-15', '2023-03-15', NOW()),
                                                                                                         ('c5e4567e-e89b-12d3-a456-426614174005', '123e4567-e89b-12d3-a456-426614174000', 'HERRING', 80.00, '2023-01-20', '2023-03-20', NOW());

-- Create sale table
CREATE TABLE sale (
                      sale_id UUID PRIMARY KEY,
                      catch_id UUID NOT NULL REFERENCES "catch"(catch_id),
                      kilos NUMERIC(10, 2) NOT NULL,
                      sale_date DATE NOT NULL
);

-- Insert sales for the catches including sales from one of the SALMON catches
INSERT INTO sale (sale_id, catch_id, kilos, sale_date) VALUES
                                                           ('123e4567-e89b-12d3-a456-426614174000', 'c1e4567e-e89b-12d3-a456-426614174001', 30.00, '2023-01-02'),
                                                           ('122e4567-e89b-12d3-a456-426614174000', 'c1e4567e-e89b-12d3-a456-426614174001', 20.00, '2023-01-03'),
                                                           ('223e4567-e89b-12d3-a456-426614174000', 'c2e4567e-e89b-12d3-a456-426614174002', 15.00, '2023-01-06'),
                                                           ('123e4567-e89b-12d3-a456-426614174004', 'c2e4567e-e89b-12d3-a456-426614174002', 15.00, '2023-01-07'),
                                                           ('123e4567-e89b-12d3-a456-426614174011', 'c2e4567e-e89b-12d3-a456-426614174002', 15.00, '2023-01-08'),
                                                           ('123e4567-e89b-12d3-a456-426614174002', 'c2e4567e-e89b-12d3-a456-426614174002', 15.00, '2023-01-09'),
                                                           ('123e4567-e89b-12d3-a456-433614174000', 'c4e4567e-e89b-12d3-a456-426614174004', 50.00, '2023-01-16'),
                                                           ('123e4567-e89b-12d3-a326-426614174000', 'c4e4567e-e89b-12d3-a456-426614174004', 20.00, '2023-01-17');
ALTER TABLE sale
    ADD COLUMN transaction_id UUID NOT NULL;
