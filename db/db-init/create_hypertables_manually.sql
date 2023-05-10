CREATE UNIQUE INDEX idx_id_timestamp ON price (timestamp DESC, id);
-- DROP INDEX idx_id_timestamp;
ALTER TABLE price
    DROP CONSTRAINT price_pkey;
ALTER TABLE price
    ADD PRIMARY KEY (id, timestamp);
DROP INDEX price_pkey;

SELECT create_hypertable('price', 'timestamp',
                         partitioning_column := 'id',
                         number_partitions => 4);

SELECT * FROM timescaledb_information.hypertables;