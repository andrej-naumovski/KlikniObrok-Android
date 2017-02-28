package mk.klikniobrok.fragments.listeners;

import mk.klikniobrok.models.Entry;
import mk.klikniobrok.models.Restaurant;

/**
 * Created by gjorgjim on 1/26/17.
 */

public interface OnItemClickListener {
    void onItemClick(String key);
    void onSubMenuEntryClick(Entry entry);
}
