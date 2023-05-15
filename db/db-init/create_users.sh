#!/bin/bash

set -e
POSTGRES_USER=postgres
POSTGRES_DB=pricingdb

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER rodzers WITH PASSWORD 'rodzerspassword';
    GRANT ALL PRIVILEGES ON SCHEMA public TO rodzers;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO rodzers;
    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO rodzers;
    GRANT ALL PRIVILEGES ON DATABASE pricingdb TO rodzers;
EOSQL