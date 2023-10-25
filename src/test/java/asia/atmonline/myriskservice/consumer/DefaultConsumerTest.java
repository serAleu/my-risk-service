//package asia.atmonline.myriskservice.consumer;
//
//import static asia.atmonline.myriskservice.enums.risk.CheckType.SCORE;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//
//import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
//import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
//import asia.atmonline.myriskservice.producers.DefaultProducer;
//import com.amazonaws.services.sqs.model.AmazonSQSException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest
//@ActiveProfiles("preprod")
//@ExtendWith(MockitoExtension.class)
//class DefaultConsumerTest {
//
//  @Autowired
//  private DefaultConsumer consumer;
//  //  @Autowired
//  @Mock
//  private QueueMessagingTemplate template;
//  @InjectMocks
//  private DefaultProducer producer;
//
//  @BeforeEach
//  void setMockOutput() {
//    doThrow(new AmazonSQSException("Error occurred")).when(template)
//        .convertAndSend(anyString(), any(ResponsePayload.class), any(MessageHeaders.class));
//  }
//
//  @Test
//  void listen() {
//    RequestPayload requestPayload = new RequestPayload();
//    requestPayload.setApplicationId(745L);
//    requestPayload.setCheckType(SCORE);
//    requestPayload.setScoreNodeId("3");
//    requestPayload.setPhone(null);
//
//    consumer.listen(requestPayload);
//    assertThrows(AmazonSQSException.class, () -> template.convertAndSend(anyString(), any(ResponsePayload.class), any(MessageHeaders.class)));
//    verify(producer).send(any(ResponsePayload.class), anyString());
//  }
//}