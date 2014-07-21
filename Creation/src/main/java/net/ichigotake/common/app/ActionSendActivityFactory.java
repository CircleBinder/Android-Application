package net.ichigotake.common.app;

import android.content.Context;
import android.content.Intent;

public final class ActionSendActivityFactory implements ActivityFactory {

    private final String text;

    public ActionSendActivityFactory(String text) {
        this.text = text;
    }

    @Override
    public Intent create(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }

}
