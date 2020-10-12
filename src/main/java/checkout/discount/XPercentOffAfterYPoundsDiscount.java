package checkout.discount;

import checkout.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class XPercentOffAfterYPoundsDiscount implements Discount {
    private final BigDecimal percentage;
    private final BigDecimal value;

    public XPercentOffAfterYPoundsDiscount(BigDecimal percentage, BigDecimal value) {
        this.percentage = percentage;
        this.value = value;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        BigDecimal total = getTotal(items);
        BigDecimal discount = BigDecimal.ZERO;
        if(total.compareTo(value) >= 0){
            discount =  total.multiply(percentage).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_DOWN);
        }
        return discount;
    }

    private BigDecimal getTotal(List<Item> items){
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
