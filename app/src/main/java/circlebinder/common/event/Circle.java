package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

import circlebinder.common.checklist.ChecklistColor;

public class Circle implements Parcelable {

    private final long id;
    private final Space space;
    private final Genre genre;
    private final ChecklistColor checklistColor;
    private final String name;
    private final String penName;
    private final CircleLinks links;
    private final String freeMemo;

    Circle(CircleBuilder builder) {
        id = builder.id;
        space = builder.space;
        genre = builder.genre;
        checklistColor = builder.checklistColor;
        name = builder.name;
        penName = builder.penName;
        links = new CircleLinks(builder.links);
        freeMemo = builder.freeMemo;
    }

    public long getId() {
        return id;
    }

    public Space getSpace() {
        return space;
    }

    public Genre getGenre() {
        return genre;
    }

    public ChecklistColor getChecklistColor() {
        return checklistColor;
    }

    public String getName() {
        return name;
    }

    public String getPenName() {
        return penName;
    }

    public CircleLinks getLinks() {
        return links;
    }

    public String getFreeMemo() {
        return freeMemo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.space, 0);
        dest.writeParcelable(this.genre, 0);
        dest.writeInt(this.checklistColor == null ? -1 : this.checklistColor.ordinal());
        dest.writeString(this.name);
        dest.writeString(this.penName);
        dest.writeParcelable(this.links, 0);
        dest.writeString(this.freeMemo);
    }

    private Circle(Parcel in) {
        this.id = in.readLong();
        this.space = in.readParcelable(Space.class.getClassLoader());
        this.genre = in.readParcelable(Genre.class.getClassLoader());
        int tmpChecklistColor = in.readInt();
        this.checklistColor = tmpChecklistColor == -1 ? null : ChecklistColor.values()[tmpChecklistColor];
        this.name = in.readString();
        this.penName = in.readString();
        this.links = in.readParcelable(CircleLinks.class.getClassLoader());
        this.freeMemo = in.readString();
    }

    public static Creator<Circle> CREATOR = new Creator<Circle>() {
        public Circle createFromParcel(Parcel source) {
            return new Circle(source);
        }

        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };
}
