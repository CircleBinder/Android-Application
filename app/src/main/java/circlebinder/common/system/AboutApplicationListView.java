package circlebinder.common.system;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

import net.ichigotake.common.app.Tripper;
import net.ichigotake.common.widget.SingleLineTextTripAdapter;

import circlebinder.R;
import circlebinder.common.app.phone.AboutApplicationScreens;
import circlebinder.common.flow.AppFlow;

public class AboutApplicationListView extends ListView {

    public AboutApplicationListView(Context context) {
        super(context);
    }

    public AboutApplicationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutApplicationListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AboutApplicationListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        SingleLineTextTripAdapter adapter = new SingleLineTextTripAdapter(getContext());
        adapter.add(
                getContext().getString(R.string.common_open_source_license), new Tripper() {
                    @Override
                    public void trip() {
                        AppFlow.get(getContext()).goTo(new AboutApplicationScreens.OpenSourceLicense());
                    }
                });
        setAdapter(adapter);
    }

}
