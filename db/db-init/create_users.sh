#!/bin/bash

set -e
POSTGRES_USER=postgres
POSTGRES_DB=pricingdb

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER rodzers WITH PASSWORD 'rodzerspassword';
    GRANT CONNECT ON DATABASE pricingdb TO rodzers;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA PUBLIC TO rodzers;
EOSQL