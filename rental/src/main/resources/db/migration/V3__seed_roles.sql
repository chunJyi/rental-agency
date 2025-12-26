-- Seeds default roles: ADMIN and AGENCY
-- This script is idempotent: it will insert each role only if it does not already exist.

INSERT INTO roles (name)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

INSERT INTO roles (name)
SELECT 'AGENCY'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'AGENCY');

