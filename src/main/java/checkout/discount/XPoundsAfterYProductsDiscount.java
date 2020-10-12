package checkout.discount;

import checkout.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

//Made the discount itself configurable in case you want a different product, a different quantity of products or a different price
public class XPoundsAfterYProductsDiscount implements Discount{
    private final String productCode;
    private final BigDecimal value;
    private final Integer quantity;

    public XPoundsAfterYProductsDiscount(String productCode, BigDecimal value, Integer quantity) {
        this.productCode = productCode;
        this.value = value;
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        BigDecimal discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        List<BigDecimal> discountedList = filterProductPrice(items);
        //calculates discount only if the filtered list is the same size
        //or bigger than the quantity needed to activate the discount
        if(discountedList.size() >= quantity){

            discount = discountedList.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .subtract(value.multiply(BigDecimal.valueOf(discountedList.size())))
                    .setScale(2, RoundingMode.HALF_UP);
        }
        //If for some reason the discount is configured incorrectly and the discount ends up being negative or 0, then it returns zero
        //updates the list only when we are sure the discount is correct
        if(discount.compareTo(BigDecimal.ZERO) == 1){
            updateItems(items);
        } else{
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return discount;
    }

    private List<BigDecimal> filterProductPrice(List<Item> items){
        return items.stream()
                .filter(item -> item.getCode().equals(productCode))
                .map(Item::getPrice)
                .collect(Collectors.toList());
    }

    // update the original list in order to play nice with the other discount scheme that applies a percentage on the total
    private void updateItems(List<Item> items){
        for(Item item : items){
            if(item.getCode().equals(productCode)){
                item.setPrice(value);
            }
        }
    }
}
