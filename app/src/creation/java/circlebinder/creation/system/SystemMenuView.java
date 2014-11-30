package circlebinder.creation.system;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.ichigotake.common.app.ActivityTripper;

import circlebinder.R;
import circlebinder.common.app.phone.AboutApplicationActivity;
import circlebinder.common.app.phone.ContactActivity;
import circlebinder.creation.app.phone.ChangeLogActivity;

public final class SystemMenuView extends ListView {

    public SystemMenuView(Context context) {
        super(context);
    }

    public SystemMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SystemMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SystemMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        setDivider(null);

        SystemMenuAdapter adapter = new SystemMenuAdapter(getContext());
        adapter.add(new SystemMenuItem(getContext().getString(R.string.common_send_feedback), 0,
                ContactActivity.createIntent(getContext())));
        adapter.add(new SystemMenuItem(getContext().getString(R.string.common_change_log), 0,
                ChangeLogActivity.createIntent(getContext())));
        adapter.add(new SystemMenuItem(getContext().getString(R.string.common_about), 0,
                AboutApplicationActivity.createIntent(getContext())));

        View headerView = LayoutInflater.from(getContext())
                .inflate(R.layout.creation_system_menu_header, this, false);
        addHeaderView(headerView, null, false);
        setAdapter(adapter);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemMenuItem item = (SystemMenuItem) parent.getItemAtPosition(position);
                new ActivityTripper(getContext(), item.getTransitionIntent()).trip();
            }
        });
    }
}
