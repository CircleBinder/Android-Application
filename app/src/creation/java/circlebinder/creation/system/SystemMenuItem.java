package circlebinder.creation.system;

import android.content.Intent;

final class SystemMenuItem {

    private final CharSequence label;
    private final int icon;
    private final Intent transitionIntent;

    SystemMenuItem(CharSequence label, int icon, Intent transitionIntent) {
        this.label = label;
        this.icon = icon;
        this.transitionIntent = transitionIntent;
    }

    public CharSequence getLabel() {
        return label;
    }

    public int getIcon() {
        return icon;
    }

    public Intent getTransitionIntent() {
        return transitionIntent;
    }

}
