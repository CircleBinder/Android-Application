package net.ichigotake.common.widget;

import android.text.Html;
import android.widget.TextView;

import net.ichigotake.common.lang.InvalidImplementationException;

public final class TextViewUtil {

    private TextViewUtil() {
        throw new InvalidImplementationException();
    }

    public static void hyperLinkDecoration(TextView textView) {
        hyperLinkDecoration(textView, "#");
    }

    public static void hyperLinkDecoration(TextView textView, String url) {
        textView.setText(Html.fromHtml("<a href=\"" + url + "\">" + textView.getText() + "</a>"));
    }

}
