#!/bin/bash
set -e
POSTGRES_USER=postgres
POSTGRES_DB=pricingdb

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER rodzers WITH PASSWORD 'rodzerspassword';
    CREATE USER dominik WITH PASSWORD 'dominikpassword';
    REVOKE ALL ON DATABASE postgres FROM public;
    REVOKE ALL ON DATABASE pricingdb FROM public;
    REVOKE ALL ON DATABASE postgres FROM public;
    REVOKE ALL ON DATABASE template0 FROM public;
    REVOKE ALL ON DATABASE template1 FROM public;
    GRANT CONNECT ON DATABASE pricingdb TO rodzers;
    GRANT CONNECT ON DATABASE pricingdb TO dominik;
    GRANT USAGE ON SCHEMA public TO rodzers;
    GRANT USAGE ON SCHEMA public TO dominik;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO rodzers;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO dominik;
EOSQL