import java.util.ArrayList;
import java.util.List;

public class Checkout {
    private List<Item> items;

    public Checkout() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void scan(Item item){
        items.add(item);
    }

}
