package mk.klikniobrok.fragments.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import mk.klikniobrok.fragments.CurrentOrderFragment;
import mk.klikniobrok.fragments.SentOrdersFragment;

/**
 * Created by gjorgjim on 3/12/17.
 */

public class PagerTabStripAdapter extends FragmentStatePagerAdapter {

    public PagerTabStripAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0 ) {
            return new CurrentOrderFragment();
        } else if(position == 1) {
            return new SentOrdersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0 ) {
            return "Моментални нарачки";
        } else {
            return "  Пратени нарачки ";
        }
    }
}
