package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

import circlebinder.common.checklist.ChecklistColor;

public class Favorite implements Parcelable {

    private final ChecklistColor checklistColor;
    private final Circle circle;

    Favorite(FavoriteBuilder builder) {
        checklistColor = builder.checklistColor;
        circle = builder.circle;
    }

    public ChecklistColor getChecklist() {
        return checklistColor;
    }

    public Circle getCircle() {
        return circle;
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

    private Favorite(Parcel in) {
        int tmpChecklistColor = in.readInt();
        this.checklistColor = tmpChecklistColor == -1 ? null : ChecklistColor.values()[tmpChecklistColor];
        this.circle = in.readParcelable(Circle.class.getClassLoader());
    }

    public static Creator<Favorite> CREATOR = new Creator<Favorite>() {
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
