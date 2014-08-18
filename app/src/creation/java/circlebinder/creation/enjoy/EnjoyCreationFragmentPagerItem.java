package circlebinder.creation.enjoy;

import android.app.Fragment;

import net.ichigotake.common.app.FragmentPagerItem;
import net.ichigotake.common.lang.InvalidImplementationException;

public final class EnjoyCreationFragmentPagerItem implements FragmentPagerItem {

    @Override
    public Fragment getItem(int position) throws InvalidImplementationException {
        switch (position) {
            case 0:
                return CircleSearchGuidanceFragment.newInstance();
            default:
                //throw new InvalidImplementationException();
                return PetiOnlyOverviewFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
