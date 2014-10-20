package net.ichigotake.common.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import circlebinder.R;

/**
 * TODO: xmlで画像リソースを指定出来るようにする
 */
public class FloatingActionButton extends FrameLayout {

    private ImageView iconView;

    public FloatingActionButton(Context context) {
        super(context);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.common_view_floating_action_button, this, true);
        iconView = (ImageView) view.findViewById(R.id.common_view_floating_action_button_icon);
        ViewCompat.setTranslationZ(view, 3);
        ViewCompat.setElevation(view, 3);
    }

    public void setImageResource(int resource) {
        iconView.setImageResource(resource);
    }

}
