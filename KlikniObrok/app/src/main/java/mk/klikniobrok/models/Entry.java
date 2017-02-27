package mk.klikniobrok.models;

public class Entry {
    private Integer id;
    private String name;
    private double price;
    private double quantity;
    private QuantityType quantityType;
    private String ingredients;
    private EntryType entryType;

    public Entry() {

    }

    public Entry(
            String name,
            double price,
            double quantity,
            QuantityType quantityType,
            String ingredients,
            EntryType entryType
    ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.quantityType = quantityType;
        this.ingredients = ingredients;
        this.entryType = entryType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public QuantityType getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }
}