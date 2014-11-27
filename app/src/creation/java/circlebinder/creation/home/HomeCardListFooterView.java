package circlebinder.creation.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;
import circlebinder.common.card.HomeCard;

public class HomeCardListFooterView extends LinearLayout {

    public HomeCardListFooterView(Context context) {
        super(context);
    }

    public HomeCardListFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeCardListFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HomeCardListFooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);
        HomeCardFooterAdapter adapter = new HomeCardFooterAdapter(getContext());
        adapter.add(new CreationHomepageCard(getContext()));
        adapter.add(new CreationLocationCard(getContext()));
        adapter.add(new CreationOfficialTwitterCard(getContext()));
        adapter.add(new CreationTwitterHashTagCard(getContext()));
        for (int i=0,size=adapter.getCount(); i<size; i++) {
            View itemView = adapter.getView(i, null, this);
            ViewFinder finder = Finders.from(itemView);
            itemView.setOnClickListener(new OnItemClickListener(adapter.getItem(i)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finder.find(R.id.common_list_item_label).setBackgroundResource(R.drawable.common_ripple);
            }
            addView(itemView);
        }
    }

    private static class OnItemClickListener implements OnClickListener {

        private final HomeCard card;

        public OnItemClickListener(HomeCard card) {
            this.card = card;
        }

        @Override
        public void onClick(View v) {
            Intent intent = card.createTransitIntent(v.getContext());
            new ActivityTripper(v.getContext(), intent)
                    .trip();
        }
    }

}
