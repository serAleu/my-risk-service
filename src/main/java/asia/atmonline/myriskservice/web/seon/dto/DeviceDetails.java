package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDetails {

  @JsonProperty("session_id")
  private String sessionId;
  private String type;

  @JsonProperty("dns_ip")
  private String dnsIp;

  @JsonProperty("dns_ip_isp")
  private String dnsIpIsp;
  private String source;

  @JsonProperty("cookie_hash")
  private String cookieHash;

  @JsonProperty("region_timeZone")
  private String regionTimeZone;

  @JsonProperty("cookie_enabled")
  private Boolean cookieEnabled;
  private String os;

  @JsonProperty("flash_enabled")
  private Boolean flashEnabled;

  @JsonProperty("java_enabled")
  private Boolean javaEnabled;

  @JsonProperty("device_type")
  private String deviceType;

  @JsonProperty("private")
  private Boolean isPrivate;

  @JsonProperty("webrtc_activated")
  private Boolean webrtcActived;

  @JsonProperty("webrtc_count")
  private Integer webrtcCount;

  @JsonProperty("webrtc_ips")
  private List<String> webrtcIps;

  @JsonProperty("user_agent")
  private String userAgent;

  @JsonProperty("window_size")
  private String windowSize;

  @JsonProperty("screen_resolution")
  private String screenResolution;

  @JsonProperty("screen_available_resolution")
  private String screenAvailableResolution;

  @JsonProperty("screen_color_depth")
  private Integer screenColorDepth;

  @JsonProperty("screen_pixel_ratio")
  private Integer screenPixelRatio;

  @JsonProperty("plugin_count")
  private Integer pluginCount;

  @JsonProperty("plugin_list")
  private List<String> pluginList;

  @JsonProperty("plugin_hash")
  private String pluginHash;

  @JsonProperty("browser_hash")
  private String browserHash;

  private String browser;

  @JsonProperty("browser_version")
  private String browserVersion;

  @JsonProperty("font_count")
  private Integer fontCount;

  @JsonProperty("font_list")
  private List<String> fontList;

  @JsonProperty("font_hash")
  private String fontHash;

  @JsonProperty("device_hash")
  private String deviceHash;

  @JsonProperty("touch_support")
  private Boolean touchSupport;

  @JsonProperty("device_memory")
  private Integer deviceMemory;

  @JsonProperty("hardware_concurrency")
  private Integer hardwareConcurrency;

  private String platform;

  @JsonProperty("region_language")
  private String regionLanguage;

  @JsonProperty("webgl_hash")
  private String webglHash;

  @JsonProperty("webgl_vendor")
  private String webglVendor;

  @JsonProperty("audio_hash")
  private String audioHash;

  @JsonProperty("do_not_track")
  private Boolean doNotTrack;

  private Boolean adblock;

  @JsonProperty("battery_level")
  private Integer batteryLevel;

  @JsonProperty("battery_charging")
  private Boolean batteryCharging;

  @JsonProperty("canvas_hash")
  private String canvasHash;

  @JsonProperty("device_ip_address")
  private String deviceIpAddress;

  @JsonProperty("device_ip_country")
  private String deviceIpCountry;

  @JsonProperty("device_ip_isp")
  private String deviceIpIsp;

  @JsonProperty("social_logins")
  private List<String> socialLogins;

  @JsonProperty("accept_language")
  private List<String> acceptLanguage;

}
