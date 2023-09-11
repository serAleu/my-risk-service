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
  borrower_id bigint,
  final_decision varchar(30),
  rejection_reason_code varchar(30),
  check_type varchar(30),
  phone_num varchar(100)
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
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  days int,
  rule_id int,
  rule_version varchar(255),
  status varchar(255),
  created_by bigint
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
  expired_at timestamp,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS blacklist_phone;
CREATE TABLE blacklist_phone (
  id serial PRIMARY KEY,
  added_at timestamp DEFAULT CURRENT_TIMESTAMP,
  credit_application_id bigint,
  expired_at timestamp,
  source varchar(255),
  phone varchar(255) NOT NULL,
  bl_reason varchar(255),
  added_by bigint,
  rule_id int,
  bl_level int
);

DROP TABLE IF EXISTS client_bl_level;
CREATE TABLE IF NOT EXISTS client_bl_level (
  id serial PRIMARY KEY,
  phone varchar(255),
  bl_level int NOT NULL,
  borrower_id bigint NOT NULL,
  created_at  timestamp DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 1, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 2, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 3, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 4, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 5, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 6, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 7, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 8, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 9, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 10, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 11, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 12, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 13, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 14, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 15, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 16, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 17, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 18, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 19, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 20, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 21, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 22, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 23, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 23, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 24, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 25, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 26, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 27, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 28, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 29, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 30, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 31, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 32, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 33, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 34, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 35, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 36, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 37, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 38, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 39, 1, 777);
INSERT INTO "public"."blacklist_rule"("add_bank_account","add_id_number","add_phone","days","rule_id","rule_version","created_by")
VALUES (true, true, true, 100, 40, 1, 777);