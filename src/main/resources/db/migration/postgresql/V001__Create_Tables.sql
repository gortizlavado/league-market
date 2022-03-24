CREATE TABLE IF NOT EXISTS sales (
  id serial PRIMARY KEY,
  season_id CHARACTER(9) NOT NULL,
  community_id INTEGER NOT NULL,
  user_owner_id INTEGER NOT NULL,
  player_id INTEGER NOT NULL,
  initial_bid_amount NUMERIC(6) NOT NULL,
  transaction_status VARCHAR(25) NOT NULL,
  created_at DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS bids (
  id serial PRIMARY KEY,
  user_bid_id INTEGER NOT NULL,
  bid_amount NUMERIC(6) NOT NULL,
  bid_status VARCHAR(25) NOT NULL,
  sale_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL,

  CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES sales (id)
);
