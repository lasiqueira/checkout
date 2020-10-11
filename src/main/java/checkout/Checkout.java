package checkout;

import checkout.discount.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Checkout {
    private final List<Item> items;
    private final List<Discount> discounts;

    public Checkout() {
        this.items = new ArrayList<>();
        this.discounts = new ArrayList<>();
    }

    public Checkout(List<Discount> discounts) {
        this.discounts = discounts;
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }


    public void scan(Item item){
        items.add(item);
    }

    public Double total(){
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(getDiscount())
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private BigDecimal getDiscount(){
        return discounts.stream()
                .map(discount -> discount.getDiscount(items))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
