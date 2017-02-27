package mk.klikniobrok.models;

/**
 * Created by andrejnaumovski on 2/27/17.
 */

public enum EntryType {
    DRINK("drink"),
    MAIN_ENTRY("main"),
    BREAKFAST("breakfast"),
    DESSERT("dessert");

    private String type;
    EntryType(String type) {
        this.type = type;
    }

    public static EntryType fromText(String text) {
        if(text.isEmpty()) {
            return null;
        }
        if(text.equalsIgnoreCase("drink")) {
            return EntryType.DRINK;
        } else if(text.equalsIgnoreCase("main")) {
            return EntryType.MAIN_ENTRY;
        } else if(text.equalsIgnoreCase("breakfast")) {
            return EntryType.BREAKFAST;
        } else if(text.equalsIgnoreCase("dessert")) {
            return EntryType.DESSERT;
        } else {
            throw new IllegalArgumentException("Wrong entry type.");
        }
    }

    @Override
    public String toString() {
        return type;
    }
}