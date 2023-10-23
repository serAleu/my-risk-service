alter table blacklist_bank_account drop column rule_id;
alter table blacklist_phone drop column rule_id;
alter table blacklist_passport_number drop column rule_id;

drop table if exists blacklist_rule;
create table if not exists blacklist_rule (
  id varchar(255) primary key,
  add_bank_account bool default false not null,
  add_id_number bool default false not null,
  add_phone bool default false not null,
  created_at timestamp default current_timestamp,
  days int,
  rule_version varchar(255),
  status varchar(255),
  created_by bigint
);

INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('BL_PHONE',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('ACTIVE_LOAN',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('ACTIVE_APP',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('APPLIM_2D',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('APPLIM_5W',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('APPLIM_9M',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('BL_ACCOUNT',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('BL_PASSPORT',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_MAXDPD',true, true, true, 9999, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_ACTLOAN',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_ACTAPPL',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_REJECTS',true, true, true, 9999, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('AGE2LOW',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('AGE2HIGH',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('REGION',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('OCCUPATION',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('INDUSTRY',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('INCOME2LOW',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('INCOME2HIGH',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('EXPR_DPD1CRNT',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('EXPR_DPD31M6',true, true, true, 90, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('EXPR_DPD61M12',true, true, true, 90, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('EXPR_LITIGATION',true, true, true, 90, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('EXPR_LOWSCORE',true, true, true, 90, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('SCORECALL1',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('SCORECALL2',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('SCORECALL3',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('SCORECALL1ERR',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('SCORECALL2ERR',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('SCORECALL3ERR',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('NOPICKUP',true, true, true, 7, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('NODEMAND',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DISAGREE',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('HIDEMAND',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('NODOСIMG',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('NOBANKAC',true, true, true, 1, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('NOEMAIL',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('NOVIDCALL',true, true, true, 7, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('FAKEDOC',true, true, true, 9999, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('FAILKYC',true, true, true, 9999, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('OTHER',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('UNFINVER',true, true, true, 7, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('BL_ACCOUNT_F',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('BL_PASSPORT_F',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_MAXDPD_F',true, true, true, 9999, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_ACTLOAN_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_ACTAPPL_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('DD_REJECTS_F',true, true, true, 9999, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('AGE2LOW_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('AGE2HIGH_F',true, true, true, 0, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('REGION_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('OCCUPATION_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('INDUSTRY_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('INCOME2LOW_F',true, true, true, 30, 1, 314);
INSERT INTO "public"."blacklist_rule"("id","add_bank_account","add_id_number","add_phone","days","rule_version","created_by")
VALUES ('CNCLAGR',true, true, true, 9999, 1, 314);
