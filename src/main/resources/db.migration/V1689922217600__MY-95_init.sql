DROP TABLE IF EXISTS risk_request;
CREATE TABLE IF NOT EXISTS risk_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  score_node_id int,
  check_type varchar(30),
  phone_num varchar(100)
);

DROP TABLE IF EXISTS risk_response;
CREATE TABLE IF NOT EXISTS risk_response (
  id serial PRIMARY KEY,
  risk_request_id bigint,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  borrower_id bigint NOT NULL,
  final_decision varchar(30),
  rejection_reason_code varchar(30),
  check_type varchar(30),
  phone_num varchar(100),
  additional_fields text
);

DROP TABLE IF EXISTS score_response;
CREATE TABLE IF NOT EXISTS score_response (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS seon_fraud_response;
CREATE TABLE IF NOT EXISTS seon_fraud_response (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  borrower_id bigint NOT NULL,
  phone varchar(100),
  response text,
  success boolean default false,
  phone_request boolean default false,
  email_request boolean default false,
  device_fingerprint_request boolean default false,
  original_response text
);

DROP TABLE IF EXISTS score_checks_response;
CREATE TABLE IF NOT EXISTS score_checks_response (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  score_node_id int NOT NULL,
  decision int NOT NULL,
  "limit" bigint,
  term int,
  grade varchar(50),
  score bigint,
  probability bigserial,
  model_id varchar(50) NOT NULL,
  model_version varchar(50) NOT NULL,
  status int,
  predictors text
);

DROP TABLE IF EXISTS blacklist_bank_account;
CREATE TABLE IF NOT EXISTS blacklist_bank_account (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  source varchar(255),
  bank_account varchar(255),
  bl_reason varchar(255),
  added_by bigint,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_passport_number;
CREATE TABLE IF NOT EXISTS blacklist_passport_number (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  source varchar(255),
  passport_number varchar(255),
  bl_reason varchar(255),
  added_by bigint,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_bank_account;
CREATE TABLE IF NOT EXISTS blacklist_bank_account (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  source varchar(255),
  bank_account varchar(255),
  bl_reason varchar(255),
  added_by bigint,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_rule;
CREATE TABLE IF NOT EXISTS blacklist_rule (
  id serial PRIMARY KEY,
  add_bank_account bool DEFAULT false NOT NULL,
  add_id_number bool DEFAULT false NOT NULL,
  add_phone bool DEFAULT false NOT NULL,
  created_at timestamp(6),
  days int,
  rule_id int,
  rule_version varchar(255),
  status varchar(255),
  created_by bigint
);