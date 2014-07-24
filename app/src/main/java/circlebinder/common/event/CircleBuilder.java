package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.checklist.ChecklistColor;

public final class CircleBuilder implements Parcelable {

    long id;
    Space space;
    Genre genre;
    ChecklistColor checklistColor;
    String name;
    String penName;
    List<CircleLink> links;
    String freeMemo;

    public CircleBuilder() {
        links = new CopyOnWriteArrayList<CircleLink>();
    }

    public CircleBuilder(CircleBuilder builder) {
        id = builder.id;
        space = builder.space;
        genre = builder.genre;
        checklistColor = builder.checklistColor;
        name = builder.name;
        penName = builder.penName;
        links = builder.links;
        freeMemo = builder.freeMemo;
    }

    public CircleBuilder(Circle circle) {
        id = circle.getId();
        space = circle.getSpace();
        genre = circle.getGenre();
        checklistColor = circle.getChecklistColor();
        name = circle.getName();
        penName = circle.getPenName();
        links = circle.getLinks().toList();
        freeMemo = circle.getFreeMemo();
    }

    public Circle build() {
        return new Circle(this);
    }

    public CircleBuilder addLink(CircleLink link) {
        this.links.add(link);
        return this;
    }

    public CircleBuilder setLink(CircleLink link) {
        this.links.clear();
        this.links.add(link);
        return this;
    }

    public CircleBuilder setPenName(String penName) {
        this.penName = penName;
        return this;
    }

    public CircleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CircleBuilder setChecklistColor(ChecklistColor checklistColor) {
        this.checklistColor = checklistColor;
        return this;
    }

    public CircleBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public CircleBuilder setSpace(Space space) {
        this.space = space;
        return this;
    }

    public CircleBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public CircleBuilder setFreeMemo(String freeMemo) {
        this.freeMemo = freeMemo;
        return this;
    }

    public CircleBuilder clear() {
        id = 0;
        space = null;
        genre = null;
        checklistColor = null;
        name = null;
        penName = null;
        links.clear();
        freeMemo = null;
        return this;
    }

    public void setLink(CircleLinks links) {
        this.links = links.toList();
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
        dest.writeTypedList(links);
        dest.writeString(this.freeMemo);
    }

    private CircleBuilder(Parcel in) {
        this.id = in.readLong();
        this.space = in.readParcelable(Space.class.getClassLoader());
        this.genre = in.readParcelable(Genre.class.getClassLoader());
        int tmpChecklistColor = in.readInt();
        this.checklistColor = tmpChecklistColor == -1 ? null : ChecklistColor.values()[tmpChecklistColor];
        this.name = in.readString();
        this.penName = in.readString();
        in.readTypedList(links, CircleLink.CREATOR);
        this.freeMemo = in.readString();
    }

    public static Creator<CircleBuilder> CREATOR = new Creator<CircleBuilder>() {
        public CircleBuilder createFromParcel(Parcel source) {
            return new CircleBuilder(source);
        }

        public CircleBuilder[] newArray(int size) {
            return new CircleBuilder[size];
        }
    };
}
