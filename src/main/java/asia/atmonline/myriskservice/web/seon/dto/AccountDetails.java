package asia.atmonline.myriskservice.web.seon.dto;

import asia.atmonline.myriskservice.web.seon.dto.social.Facebook;
import asia.atmonline.myriskservice.web.seon.dto.social.Google;
import asia.atmonline.myriskservice.web.seon.dto.social.Instagram;
import asia.atmonline.myriskservice.web.seon.dto.social.Kakao;
import asia.atmonline.myriskservice.web.seon.dto.social.Line;
import asia.atmonline.myriskservice.web.seon.dto.social.Microsoft;
import asia.atmonline.myriskservice.web.seon.dto.social.Ok;
import asia.atmonline.myriskservice.web.seon.dto.social.Skype;
import asia.atmonline.myriskservice.web.seon.dto.social.Snapchat;
import asia.atmonline.myriskservice.web.seon.dto.social.Telegram;
import asia.atmonline.myriskservice.web.seon.dto.social.Twitter;
import asia.atmonline.myriskservice.web.seon.dto.social.Viber;
import asia.atmonline.myriskservice.web.seon.dto.social.Whatsapp;
import asia.atmonline.myriskservice.web.seon.dto.social.Yahoo;
import asia.atmonline.myriskservice.web.seon.dto.social.Zalo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {
  private Whatsapp whatsapp;
  private Twitter twitter;
  private Yahoo yahoo;
  private Facebook facebook;
  private Telegram telegram;
  private Google google;
  private Instagram instagram;
  private Viber viber;
  private Kakao kakao;
  private Zalo zalo;
  private Ok ok;
  private Line line;
  private Microsoft microsoft;
  private Snapchat snapchat;
  private Skype skype;
}
