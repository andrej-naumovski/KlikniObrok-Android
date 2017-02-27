package mk.klikniobrok.fragments.listeners;

import java.util.List;

import mk.klikniobrok.models.Entry;

/**
 * Created by andrejnaumovski on 2/27/17.
 */

public interface SubMenuEntriesListener {
    List<Entry> getEntriesByType(String type);
}
