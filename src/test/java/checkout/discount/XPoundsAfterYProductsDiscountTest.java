package checkout.discount;

import checkout.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XPoundsAfterYProductsDiscountTest {
    private List<Item> items;
    @BeforeEach
    public void setup(){
        items = List.of(
                new Item("001", "Travel Card Holder", new BigDecimal("9.25")),
                new Item("001", "Travel Card Holder", new BigDecimal("9.25")));
    }
    @Test
    @DisplayName("Test £8.50 after 2 products discount")
    public void eightFiftyAfterTwoProductsDiscountTest(){
        Discount discount = new XPoundsAfterYProductsDiscount("001", BigDecimal.valueOf(8.50), 2);
        BigDecimal expected = BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = discount.getDiscount(items);
        assertEquals(expected, actual);
    }

}
