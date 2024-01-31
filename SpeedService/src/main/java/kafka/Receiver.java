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

  public static Map<String, SensorRecord> cars = new HashMap<>();
  @Autowired
  private Sender sender;

  @KafkaListener(topics = {"cameratopic1", "cameratopic2"})
  public void receive(
      @Payload SensorRecord sensorRecord,
      @Headers MessageHeaders headers) {

    String licencePlate = sensorRecord.getLicencePlate();
    SensorRecord theFirstCamera = cars.get(licencePlate);
    if (Objects.isNull(theFirstCamera)) {
      if (sensorRecord.cameraId == 1) {
        cars.put(licencePlate, sensorRecord);
      }
    } else {
      double speed = calculateSpeed(theFirstCamera, sensorRecord);
      System.out.println("License plate:" + licencePlate + "; Speed: " + speed);
      if (speed > 72) {
        overSpeedLimit(licencePlate, speed);
      }
      cars.remove(licencePlate);
    }

  }

  private void overSpeedLimit(String licencePlate, double speed) {
    sender.send("toofastTopic", new TooFastCar(licencePlate, speed));
    System.out.println("sending to topic:toofastTopic;speed:" + speed);
  }

  private double calculateSpeed(SensorRecord theFirstCamera, SensorRecord theSecondCamera) {
    String time1 = theFirstCamera.getMinute() + ":" + theFirstCamera.getSecond();
    String time2 = theSecondCamera.getMinute() + ":" + theSecondCamera.getSecond();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    Date date2, date1;
    try {
      date1 = simpleDateFormat.parse(time1);
      date2 = simpleDateFormat.parse(time2);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    long differenceInMilliSeconds
        = Math.abs(date2.getTime() - date1.getTime());
    long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;
    return 0.5 / differenceInSeconds * 3600;
  }

}