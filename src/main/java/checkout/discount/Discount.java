package checkout.discount;

import checkout.Item;

import java.math.BigDecimal;
import java.util.List;

public interface Discount {
    BigDecimal getDiscount(List<Item> items);
}
