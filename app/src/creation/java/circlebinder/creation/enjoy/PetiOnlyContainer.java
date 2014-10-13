package circlebinder.creation.enjoy;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.dmitriy.tarasov.android.intents.IntentUtils;
import com.squareup.picasso.Picasso;

import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.R;

class PetiOnlyContainer {

    private final Context context;
    private final View nmnlContainer;
    private final ImageView nmnlImage;
    private final View omufesContainer;
    private final ImageView omufesImage;

    PetiOnlyContainer(View container) {
        this.context = container.getContext().getApplicationContext();
        this.nmnlContainer = container.findViewById(R.id.creation_enjoy_creation_peti_only_nmnl_container);
        this.nmnlImage = (ImageView) container.findViewById(R.id.enjoy_creation_peti_only_nmnl_image);
        this.omufesContainer = container.findViewById(R.id.creation_enjoy_creation_peti_only_omufes_container);
        this.omufesImage = (ImageView) container.findViewById(R.id.creation_enjoy_creation_peti_only_omufes_image);
    }

    void render() {
        nmnlContainer.setOnClickListener(OnClickToTrip.activityTrip(context,
                IntentUtils.openLink("http://nmnl.jimdo.com/")));
        renderImage(nmnlImage, "http://u.jimdo.com/www47/o/s7c299b1ba1e9f0a7/img/i7991934b8e4dc55f/1410948655/std/image.jpg");
        omufesContainer.setOnClickListener(OnClickToTrip.activityTrip(context,
                IntentUtils.openLink("http://omufes.web.fc2.com/")));
        renderImage(omufesImage, "http://omufes.web.fc2.com/omufes3-a.jpg");
    }

    private void renderImage(ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

}
