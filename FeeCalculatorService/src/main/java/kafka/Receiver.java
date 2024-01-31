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

  private static OwnerInfo getOwnerInfo(String licencePlate) {
    return new OwnerInfo(licencePlate, "Owner" + licencePlate);
  }

  @KafkaListener(topics = {"OwnerTopic"})
  public void receive(
      @Payload FeeInfo feeinfo,
      @Headers MessageHeaders headers) {
    String licencePlate = feeinfo.getOwnerInfo().getLicencePlate();
    String fullName = feeinfo.getOwnerInfo().getFullName();
    double speed = feeinfo.getSpeed();
    double feeAmount = calcFee(speed);
    System.out.println(
        "Fee for:licencePlate=" + licencePlate + ";Owner=" + fullName + ";speed=" + speed + ";fee=$"
            + feeAmount);
  }

  private double calcFee(double speed) {
    if (speed > 90) {
      return 125;
    } else if (speed > 82) {
      return 80;
    } else if (speed > 77) {
      return 45;
    } else {
      return 25;
    }
  }
}