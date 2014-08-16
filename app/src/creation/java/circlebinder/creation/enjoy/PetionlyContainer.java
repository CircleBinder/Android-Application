package circlebinder.creation.enjoy;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import circlebinder.R;

public class PetionlyContainer {

    public static void render(View container) {
        ImageView petiOnly1 = (ImageView) container.findViewById(R.id.event_heads_up_peti_only_1);
        ImageView petiOnly2 = (ImageView) container.findViewById(R.id.event_heads_up_peti_only_2);
        new PetionlyContainer(petiOnly1, petiOnly2).renderView();
    }

    private final Context context;
    private final ImageView petiOnly1;
    private final ImageView petiOnly2;

    private PetionlyContainer(ImageView petiOnly1, ImageView petiOnly2) {
        this.context = petiOnly1.getContext().getApplicationContext();
        this.petiOnly1 = petiOnly1;
        this.petiOnly2 = petiOnly2;
    }

    private void renderView() {
        renderImage(petiOnly1, "http://u.jimdo.com/www47/o/s7c299b1ba1e9f0a7/img/ib658363f5dfafd3a/1402927757/std/image.png");
        renderImage(petiOnly2, "http://omufes.web.fc2.com/omufes3-a.jpg");
    }

    private void renderImage(ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

}
