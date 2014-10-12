package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

import circlebinder.common.checklist.ChecklistColor;

public final class FavoriteBuilder implements Parcelable {

    ChecklistColor checklistColor;
    Circle circle;

    public Favorite build() {
        return new Favorite(this);
    }

    public FavoriteBuilder setCircle(Circle circle) {
        this.circle = circle;
        return this;
    }

    public FavoriteBuilder setChecklistColor(ChecklistColor color) {
        this.checklistColor = color;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.checklistColor == null ? -1 : this.checklistColor.ordinal());
        dest.writeParcelable(this.circle, 0);
    }

    public FavoriteBuilder() {
    }

    private FavoriteBuilder(Parcel in) {
        int tmpChecklistColor = in.readInt();
        this.checklistColor = tmpChecklistColor == -1 ? null : ChecklistColor.values()[tmpChecklistColor];
        this.circle = in.readParcelable(((Object) circle).getClass().getClassLoader());
    }

    public static Creator<FavoriteBuilder> CREATOR = new Creator<FavoriteBuilder>() {
        public FavoriteBuilder createFromParcel(Parcel source) {
            return new FavoriteBuilder(source);
        }

        public FavoriteBuilder[] newArray(int size) {
            return new FavoriteBuilder[size];
        }
    };
}
