import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {
    private Checkout checkout;
    private Item item;

    @BeforeEach
    public void setup(){
        this.checkout = new Checkout();
        this.item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
    }

    @Test
    @DisplayName("Test adding/scanning item to checkout")
    public void scanItemTest(){
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
