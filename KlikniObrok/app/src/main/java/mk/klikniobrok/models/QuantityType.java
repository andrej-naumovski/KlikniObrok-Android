package mk.klikniobrok.models;

/**
 * Created by andrejnaumovski on 2/27/17.
 */

public enum QuantityType {
    GRAMS("grams"), MILILITERS("mililiters");

    private String type;
    QuantityType(String type) {
        this.type = type;
    }
}

