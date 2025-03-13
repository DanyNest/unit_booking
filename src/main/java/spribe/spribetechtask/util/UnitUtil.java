package spribe.spribetechtask.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

/**
 * @Author danynest @CreateAt 10.03.25
 */
@UtilityClass
public class UnitUtil {
  private static final BigDecimal SERVICE_TAX_PERCENTAGE = new BigDecimal("15");
  private static final BigDecimal HUNDRED_PERCENT = new BigDecimal("100");
  private static final int ROUND_SCALING = 2;

  public static BigDecimal calculatePriceWithTax(BigDecimal price) {
    return price.multiply(
        BigDecimal.ONE.add(SERVICE_TAX_PERCENTAGE.divide(HUNDRED_PERCENT, ROUND_SCALING, RoundingMode.HALF_UP)));
  }
}
