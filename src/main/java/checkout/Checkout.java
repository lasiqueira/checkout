package checkout;

import checkout.discount.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Checkout {
    private final List<Item> items;
    //This allows multiple discount schemes to be applied
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

    //This gets the total price from the items - the discounts. If there are no discounts nothing will be subtracted.
    public Double total(){
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(getDiscount())
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    //get a sum of total discount to be later subtracted to the total
    private BigDecimal getDiscount(){
        return discounts.stream()
                .map(discount -> discount.getDiscount(items))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
