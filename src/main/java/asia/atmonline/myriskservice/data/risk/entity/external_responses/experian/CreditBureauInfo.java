package asia.atmonline.myriskservice.data.risk.entity.external_responses.experian;

import asia.atmonline.myriskservice.data.risk.entity.BaseRiskJpaEntity;
import asia.atmonline.myriskservice.enums.risk.ExperianCallStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@Entity
@Table(name = "credit_bureau_info")
@SequenceGenerator(name = "sequence-generator", sequenceName = "credit_bureau_info_id_seq", allocationSize = 1)
public class CreditBureauInfo extends BaseRiskJpaEntity {

  @Column(name = "application_id", nullable = false)
  private Long applicationId;

  @Column(name = "bureau_id")
  private Long bureauId;

  @Column(name = "ccris_search_request_dttm")
  private LocalDateTime ccrisSearchRequestDttm;

  @Column(name = "ccris_search_request_json")
  private String ccrisSearchRequestJson;

  @Column(name = "ccris_search_response_status")
  @Enumerated(EnumType.STRING)
  private ExperianCallStatus ccrisSearchResponseStatus;

  @Column(name = "ccris_search_error_code")
  private String ccrisSearchErrorCode;

  @Column(name = "ccris_search_response_dttm")
  private LocalDateTime ccrisSearchResponseDttm;

  @Column(name = "ccris_search_response_json")
  private String ccrisSearchResponseJson;

  @Column(name = "ccris_search_crefid")
  private Long ccrisSearchCrefid;

  @Column(name = "ccris_search_entitykey")
  private String ccrisSearchEntitykey;

  @Column(name = "ccris_confirm_request_dttm")
  private LocalDateTime ccrisConfirmRequestDttm;

  @Column(name = "ccris_confirm_request_json")
  private String ccrisConfirmRequestJson;

  @Column(name = "ccris_confirm_response_status")
  @Enumerated(EnumType.STRING)
  private ExperianCallStatus ccrisConfirmResponseStatus;

  @Column(name = "ccris_confirm_error_code")
  private String ccrisConfirmErrorCode;

  @Column(name = "ccris_confirm_response_dttm")
  private LocalDateTime ccrisConfirmResponseDttm;

  @Column(name = "ccris_confirm_response_json")
  private String ccrisConfirmResponseJson;

  @Column(name = "ccris_confirm_token1")
  private String ccrisConfirmToken1;

  @Column(name = "ccris_confirm_token2")
  private String ccrisConfirmToken2;

  @Column(name = "ccris_report_request_dttm")
  private LocalDateTime ccrisReportRequestDttm;

  @Column(name = "ccris_report_request_json")
  private String ccrisReportRequestJson;

  @Column(name = "ccris_report_response_status")
  @Enumerated(EnumType.STRING)
  private ExperianCallStatus ccrisReportResponseStatus;

  @Column(name = "ccris_report_error_code")
  private String ccrisReportErrorCode;

  @Column(name = "ccris_report_response_dttm")
  private LocalDateTime ccrisReportResponseDttm;

  @Column(name = "ccris_report_response_json")
  private String ccrisReportResponseJson;

  @Column(name = "score")
  private String score;

  @Column(name = "score_grade")
  private String scoreGrade;

  @Override
  public String repositoryName() {
    return "creditBureauInfoJpaRepository";
  }
}
