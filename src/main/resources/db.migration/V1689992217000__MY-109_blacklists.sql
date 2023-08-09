DROP TABLE IF EXISTS blacklist_bank_account;
CREATE TABLE blacklist_bank_account (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id int,
  source varchar(255),
  bank_account varchar(255),
  bl_reason varchar(255),
  added_by int,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_id_number;
CREATE TABLE blacklist_id_number (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id int,
  source varchar(255),
  id_number varchar(255),
  bl_reason varchar(255),
  added_by int,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_bank_account;
CREATE TABLE blacklist_bank_account (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id int,
  source varchar(255),
  bank_account varchar(255),
  bl_reason varchar(255),
  added_by int,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_rule;
CREATE TABLE blacklist_rule (
  id serial PRIMARY KEY,
  add_bank_account bool DEFAULT false NOT NULL,
  add_id_number bool DEFAULT false NOT NULL,
  add_phone bool DEFAULT false NOT NULL,
  created_at timestamp(6),
  days int,
  rule_id varchar(255),
  rule_version varchar(255),
  status varchar(255),
  created_by int
);