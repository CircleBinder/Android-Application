package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CircleLinks implements Parcelable {

    private final List<CircleLink> links = new CopyOnWriteArrayList<CircleLink>();

    public CircleLinks() {}

    public CircleLinks(List<CircleLink> links) {
        this.links.addAll(links);
    }

    public CircleLinks(CircleLink[] links) {
        this.links.addAll( Arrays.asList(links));
    }

    public List<CircleLink> toList() {
        return links;
    }

    public boolean isEmpty() {
        return links.isEmpty();
    }

    public int size() {
        return links.size();
    }

    public CircleLink get(int location) {
        return links.get(location);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CircleLinks)) {
            return false;
        }

        List<CircleLink> objectList = ((CircleLinks)object).toList();
        boolean equals = true;
        final int size = size();
        if (size != objectList.size()) {
            return false;
        }

        for (int i=0; i<size; i++) {
            CircleLink objectItem = objectList.get(i);
            CircleLink expectedItem = get(i);
            if (!expectedItem.equals(objectItem)) {
                equals = false;
                break;
            }
        }

        return equals;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(links);
    }

    private CircleLinks(Parcel in) {
        in.readTypedList(links, CircleLink.CREATOR);
    }

    public static Creator<CircleLinks> CREATOR = new Creator<CircleLinks>() {
        public CircleLinks createFromParcel(Parcel source) {
            return new CircleLinks(source);
        }

        public CircleLinks[] newArray(int size) {
            return new CircleLinks[size];
        }
    };
}
