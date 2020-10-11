package checkout.discount;

import checkout.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XPercentOffAfterYPoundsDiscountTest {
    private List<Item> items;
    @BeforeEach
    public void setup(){
    items = List.of(
            new Item("002", "Personalised cufflinks", new BigDecimal("50.00")),
            new Item("002", "Personalised cufflinks", new BigDecimal("50.00")));
    }
    @Test
    @DisplayName("Test 10 Percent Off after 100 pounds discount")
    public void TenPercentOffAfterHundredPoundsTest(){
        Discount discount = new XPercentOffAfterYPoundsDiscount(BigDecimal.TEN, BigDecimal.valueOf(100));
        BigDecimal expected = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = discount.getDiscount(items);
        assertEquals(expected, actual);
    }

}
