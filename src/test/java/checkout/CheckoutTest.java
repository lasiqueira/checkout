package checkout;

import checkout.discount.Discount;
import checkout.discount.XPercentOffAfterYPoundsDiscount;
import checkout.discount.XPoundsAfterYProductsDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {

    @Test
    @DisplayName("Test adding/scanning item to checkout")
    public void scanItemTest(){
        Checkout checkout = new Checkout();
        Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));

        checkout.scan(item);

        assertNotNull(checkout.getItems());
        assertFalse(checkout.getItems().isEmpty());
        assertEquals(item, checkout.getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }

    @Test
    @DisplayName("Test getting total without discount")
    public void getTotalWithoutDiscount(){
        Checkout checkout = new Checkout();
        Item itemA = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        Item itemB = new Item("002", "Personalised cufflinks", new BigDecimal("45.00"));

        checkout.scan(itemA);
        checkout.scan(itemB);

        Double expected = itemA.getPrice().add(itemB.getPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double actual = checkout.total();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test getting total with 10% discount after £60")
    public void getTotalWith10PercentOver60Discount(){
        Discount discount = new XPercentOffAfterYPoundsDiscount(BigDecimal.TEN, BigDecimal.valueOf(60));
        Checkout checkout = new Checkout(List.of(discount));
        Item itemA = new Item("002", "Personalised cufflinks", new BigDecimal("45.00"));
        Item itemB = new Item("002", "Personalised cufflinks", new BigDecimal("45.00"));

        checkout.scan(itemA);
        checkout.scan(itemB);

        BigDecimal total = itemA.getPrice().add(itemB.getPrice());
        Double expected = total.subtract(total.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double actual = checkout.total();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test getting total with £8.50 discount after 2+ products")
    public void getTotalWith850After2PlusDiscount(){
        Discount discount = new XPoundsAfterYProductsDiscount("001", BigDecimal.valueOf(8.50), 2);
        Checkout checkout = new Checkout(List.of(discount));
        Item itemA = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        Item itemB = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));

        checkout.scan(itemA);
        checkout.scan(itemB);

        BigDecimal total = itemA.getPrice().add(itemB.getPrice());
        Double expected = total.subtract(
                itemA.getPrice().multiply(BigDecimal.valueOf(2)).subtract(BigDecimal.valueOf(8.50).multiply(BigDecimal.valueOf(2))))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        Double actual = checkout.total();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test getting total with multiple discounts")
    public void getTotalWithMultipleDiscounts(){
        Discount discountA = new XPoundsAfterYProductsDiscount("001", BigDecimal.valueOf(8.50), 2);
        Discount discountB = new XPercentOffAfterYPoundsDiscount(BigDecimal.TEN, BigDecimal.valueOf(60));
        Checkout checkout = new Checkout(List.of(discountA, discountB));

        Item itemA = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        Item itemB = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        Item itemC = new Item("002", "Personalised cufflinks", new BigDecimal("45.00"));
        Item itemD = new Item("002", "Personalised cufflinks", new BigDecimal("45.00"));

        checkout.scan(itemA);
        checkout.scan(itemB);
        checkout.scan(itemC);
        checkout.scan(itemD);

        BigDecimal total = itemA.getPrice().add(itemB.getPrice()).add(itemC.getPrice()).add(itemD.getPrice()).setScale(2, RoundingMode.HALF_UP);
        Double expected = total.subtract(
                itemA.getPrice().multiply(BigDecimal.valueOf(2)).subtract(BigDecimal.valueOf(8.50).multiply(BigDecimal.valueOf(2))))
                .subtract(total.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        Double actual = checkout.total();
        assertEquals(expected, actual);

    }



}
