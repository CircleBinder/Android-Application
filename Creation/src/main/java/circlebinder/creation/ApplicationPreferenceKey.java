package circlebinder.creation;

import net.ichigotake.common.content.PreferenceKey;

public enum ApplicationPreferenceKey implements PreferenceKey {

    CIRCLE_SEARCH_OPTION,
    HOME_PAGER_STATE,
    ;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String toString() {
        return getKey();
    }
}
