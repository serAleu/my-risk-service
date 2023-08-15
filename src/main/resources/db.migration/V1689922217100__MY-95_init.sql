DROP TABLE IF EXISTS score_request;
CREATE TABLE IF NOT EXISTS score_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS basic_checks_request;
CREATE TABLE IF NOT EXISTS basic_checks_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS blacklist_checks_request;
CREATE TABLE IF NOT EXISTS blacklist_checks_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  phone_num varchar(100) NOT NULL
);

DROP TABLE IF EXISTS bureau_checks_request;
CREATE TABLE IF NOT EXISTS bureau_checks_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS cooldown_checks_request;
CREATE TABLE IF NOT EXISTS cooldown_checks_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS dedup_checks_request;
CREATE TABLE IF NOT EXISTS dedup_checks_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL,
  passport_number varchar(100),
  bank_account varchar(100),
  is_email_confirmed boolean default false
);

DROP TABLE IF EXISTS final_checks_request;
CREATE TABLE IF NOT EXISTS final_checks_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS seon_fraud_request;
CREATE TABLE IF NOT EXISTS seon_fraud_request (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL,
  original_request text,
);

DROP TABLE IF EXISTS score_response;
CREATE TABLE IF NOT EXISTS score_response (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL
);

DROP TABLE IF EXISTS risk_response;
CREATE TABLE IF NOT EXISTS risk_response (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL,
  final_decision varchar(30),
  rejection_reason_code varchar(30),
  check_type varchar(30),
  phone_num varchar(100),
  additional_fields text
);

DROP TABLE IF EXISTS seon_fraud_response;
CREATE TABLE IF NOT EXISTS seon_fraud_response (
  id serial PRIMARY KEY,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  application_id bigint,
  borrower_id bigint NOT NULL,
  phone varchar(100),
  response text,
  success boolean default false,
  phone_request boolean default false,
  email_request boolean default false,
  device_fingerprint_request boolean default false,
  original_response text
);

DROP TABLE IF EXISTS blacklist_bank_account;
CREATE TABLE IF NOT EXISTS blacklist_bank_account (
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

DROP TABLE IF EXISTS blacklist_passport_number;
CREATE TABLE IF NOT EXISTS blacklist_passport_number (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id int,
  source varchar(255),
  passport_number varchar(255),
  bl_reason varchar(255),
  added_by int,
  expired_at TIMESTAMP,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_bank_account;
CREATE TABLE IF NOT EXISTS blacklist_bank_account (
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
CREATE TABLE IF NOT EXISTS blacklist_rule (
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