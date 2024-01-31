package kafka;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

  @Autowired
  private Sender sender;

  private static OwnerInfo getOwnerInfo(String licencePlate) {
    return new OwnerInfo(licencePlate, "Owner" + licencePlate);
  }

  @KafkaListener(topics = {"toofastTopic"})
  public void receive(
      @Payload TooFastCar tooFastCar,
      @Headers MessageHeaders headers) {
    String licencePlate = tooFastCar.getLicencePlate();
    OwnerInfo ownerInfo = getOwnerInfo(licencePlate);
    FeeInfo feeInfo = new FeeInfo(ownerInfo,
        tooFastCar.getSpeed());
    sender.send("OwnerTopic", feeInfo);
    System.out.println(
        "OwnerTopic sent:licencePlate=" + tooFastCar.getLicencePlate() + ";Owner="
            + ownerInfo.getFullName() + ";speed=" + tooFastCar.getSpeed());
  }
}