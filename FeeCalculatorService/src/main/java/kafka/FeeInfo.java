package kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeInfo {

  private OwnerInfo ownerInfo;
  private double speed;

}
