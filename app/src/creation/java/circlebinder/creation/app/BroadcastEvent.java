package circlebinder.creation.app;

import android.content.Intent;
import android.content.IntentFilter;

public final class BroadcastEvent {

    public static String ACTION = "circlebinder.creation.app";

    private BroadcastEvent(){}

    public static Intent createIntent() {
        return new Intent(ACTION);
    }

    public static IntentFilter createIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        return filter;
    }
}
