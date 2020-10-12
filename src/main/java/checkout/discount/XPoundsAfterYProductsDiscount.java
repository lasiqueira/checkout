package checkout.discount;

import checkout.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class XPoundsAfterYProductsDiscount implements Discount{
    private String productCode;
    private BigDecimal value;
    private Integer quantity;

    public XPoundsAfterYProductsDiscount(String productCode, BigDecimal value, Integer quantity) {
        this.productCode = productCode;
        this.value = value;
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        BigDecimal discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        List<BigDecimal> discountedList = filterProductPrice(items);
        if(discountedList.size() >= quantity){
            updateItems(items);
            discount = discountedList.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .subtract(value.multiply(BigDecimal.valueOf(discountedList.size())))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return discount.compareTo(BigDecimal.ZERO) == 1? discount: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    private List<BigDecimal> filterProductPrice(List<Item> items){
        return items.stream()
                .filter(item -> item.getCode().equals(productCode))
                .map(Item::getPrice)
                .collect(Collectors.toList());
    }

    private void updateItems(List<Item> items){
        for(Item item : items){
            if(item.getCode().equals(productCode)){
                item.setPrice(value);
            }
        }
    }
}
