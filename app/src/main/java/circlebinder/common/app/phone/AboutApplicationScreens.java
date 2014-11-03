package circlebinder.common.app.phone;

import circlebinder.R;
import circlebinder.common.app.CircleBinderApplication;
import circlebinder.common.flow.HasTitle;
import circlebinder.common.flow.Screen;
import flow.HasParent;
import flow.Layout;

public final class AboutApplicationScreens {

    @Layout(R.layout.common_screen_about)
    public static class About extends Screen implements HasTitle {

        @Override
        public CharSequence getTitle() {
            return CircleBinderApplication.get().getString(R.string.common_about);
        }
    }

    @Layout(R.layout.common_screen_open_source_license)
    public static class OpenSourceLicense extends Screen implements HasParent<About>, HasTitle {

        @Override
        public About getParent() {
            return new About();
        }

        @Override
        public CharSequence getTitle() {
            return CircleBinderApplication.get().getString(R.string.common_open_source_license);
        }
    }

}
