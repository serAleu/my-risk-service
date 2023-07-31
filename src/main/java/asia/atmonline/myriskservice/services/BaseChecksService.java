package asia.atmonline.myriskservice.services;

import asia.atmonline.myriskservice.messages.request.BaseRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;

public abstract class BaseChecksService<R extends BaseRequest> {

  public abstract RiskResponse process(R request);
  public abstract boolean accept(R request);
  public abstract void saveRequest(R request);
  public abstract void saveResponse(RiskResponse riskResponse);

}