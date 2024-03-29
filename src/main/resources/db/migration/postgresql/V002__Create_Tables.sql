-- Use ordered UUID v1 (only v1 can be ordered sequentially), stored as BINARY(16) when the data is dynamically generated.
-- For reference table, stick to auto-incremented primary keys since the values are static and won't change that much either.

CREATE TABLE IF NOT EXISTS sale (
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  season_id CHARACTER(9) NOT NULL,
  community_id uuid NOT NULL,
  user_owner_id uuid NOT NULL,
  player_id uuid NOT NULL,
  initial_bid_amount NUMERIC(6) NOT NULL,
  transaction_status SMALLINT NOT NULL,

  created_at DATE NOT NULL DEFAULT CURRENT_DATE,
  updated_at TIMESTAMP NULL,
  deleted_at TIMESTAMP NULL,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bid (
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  user_bid_id uuid NOT NULL,
  bid_amount NUMERIC(6) NOT NULL,
  bid_status SMALLINT NOT NULL,
  sale_id INTEGER NOT NULL,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL,
  deleted_at TIMESTAMP NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES sale (id)
);

CREATE TABLE IF NOT EXISTS player_price (
  player_id uuid,
  season_id CHARACTER(9),
  month SMALLINT,
  price NUMERIC(6) NOT NULL,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (player_id, season_id, month)
);

CREATE TABLE IF NOT EXISTS change_owner (
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  player_id uuid NOT NULL,
  user_last_owner_id uuid NOT NULL,
  user_new_owner_id uuid NOT NULL,
  community_id uuid NOT NULL,
  change_date DATE NOT NULL DEFAULT CURRENT_DATE + 1,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id)
);

CREATE TRIGGER t_sale
  BEFORE UPDATE
  ON sale
  FOR EACH ROW
  EXECUTE PROCEDURE upd_timestamp();

CREATE TRIGGER t_bid
  BEFORE UPDATE
  ON bid
  FOR EACH ROW
  EXECUTE PROCEDURE upd_timestamp();
