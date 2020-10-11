import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {
    private Checkout checkout;

    @BeforeEach
    public void setup(){
        this.checkout = new Checkout();
    }

    @Test
    @DisplayName("Test adding/scanning item to checkout")
    public void scanItemTest(){
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

    }

    @Test
    @DisplayName("Test getting total with £8.50 discount after 2+ products")
    public void getTotalWith850After2PlusDiscount(){

    }

    @Test
    @DisplayName("Test getting total with multiple discounts")
    public void getTotalWithMultipleDiscounts(){

    }



}
