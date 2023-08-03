package asia.atmonline.myriskservice.web.seon.dto.email;

import asia.atmonline.myriskservice.web.seon.dto.social.Airbnb;
import asia.atmonline.myriskservice.web.seon.dto.social.Amazon;
import asia.atmonline.myriskservice.web.seon.dto.social.Apple;
import asia.atmonline.myriskservice.web.seon.dto.social.Booking;
import asia.atmonline.myriskservice.web.seon.dto.social.Discord;
import asia.atmonline.myriskservice.web.seon.dto.social.Ebay;
import asia.atmonline.myriskservice.web.seon.dto.social.Facebook;
import asia.atmonline.myriskservice.web.seon.dto.social.Flickr;
import asia.atmonline.myriskservice.web.seon.dto.social.Foursquare;
import asia.atmonline.myriskservice.web.seon.dto.social.Github;
import asia.atmonline.myriskservice.web.seon.dto.social.Google;
import asia.atmonline.myriskservice.web.seon.dto.social.Gravatar;
import asia.atmonline.myriskservice.web.seon.dto.social.Instagram;
import asia.atmonline.myriskservice.web.seon.dto.social.Kakao;
import asia.atmonline.myriskservice.web.seon.dto.social.Lastfm;
import asia.atmonline.myriskservice.web.seon.dto.social.Linkedin;
import asia.atmonline.myriskservice.web.seon.dto.social.Microsoft;
import asia.atmonline.myriskservice.web.seon.dto.social.Myspace;
import asia.atmonline.myriskservice.web.seon.dto.social.Ok;
import asia.atmonline.myriskservice.web.seon.dto.social.Pinterest;
import asia.atmonline.myriskservice.web.seon.dto.social.Qzone;
import asia.atmonline.myriskservice.web.seon.dto.social.Skype;
import asia.atmonline.myriskservice.web.seon.dto.social.Spotify;
import asia.atmonline.myriskservice.web.seon.dto.social.Tumblr;
import asia.atmonline.myriskservice.web.seon.dto.social.Twitter;
import asia.atmonline.myriskservice.web.seon.dto.social.Vimeo;
import asia.atmonline.myriskservice.web.seon.dto.social.Weibo;
import asia.atmonline.myriskservice.web.seon.dto.social.Yahoo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {
    private Apple apple;
    private Ebay ebay;
    private Facebook facebook;
    private Flickr flickr;
    private Foursquare foursquare;
    private Github github;
    private Google google;
    private Gravatar gravatar;
    private Instagram instagram;
    private Lastfm lastfm;
    private Linkedin linkedin;
    private Microsoft microsoft;
    private Myspace myspace;
    private Pinterest pinterest;
    private Skype skype;
    private Spotify spotify;
    private Tumblr tumblr;
    private Twitter twitter;
    private Vimeo vimeo;
    private Weibo weibo;
    private Yahoo yahoo;
    private Discord discord;
    private Ok ok;
    private Kakao kakao;
    private Booking booking;
    private Airbnb airbnb;
    private Amazon amazon;
    private Qzone qzone;
}
