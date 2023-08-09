package asia.atmonline.myriskservice.web.blacklist;

import static asia.atmonline.myriskservice.enums.risk.BlacklistSource.MANUAL;

import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import asia.atmonline.myriskservice.web.blacklist.dto.BlacklistRecordForm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/blacklist")
public class BlacklistController {

  @Autowired
  private final BlacklistChecksService blackListService;

  @PostMapping(value = "/add")
  public ResponseEntity<String> save(@RequestBody BlacklistRecordForm form, Long backOfficeUserId) {
    try {
      blackListService.save(form, MANUAL, backOfficeUserId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}