package mk.klikniobrok.models;

/**
 * Created by andrejnaumovski on 2/27/17.
 */

public class EntryWrapper {
    private Entry entry;
    private int quantity;

    public EntryWrapper(Entry entry, int quantity) {
        this.entry = entry;
        this.quantity = quantity;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
