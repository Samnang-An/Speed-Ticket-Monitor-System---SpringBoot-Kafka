package kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TooFastCar {

  private String licencePlate;
  private double speed;

  public String toString() {
    return licencePlate + " " + speed;
  }

}
