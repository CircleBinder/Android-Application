package circlebinder.common.search;

import android.os.Parcel;
import android.os.Parcelable;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Block;

public class CircleSearchOption implements Parcelable {
    private final String keyword;
    private final Order order;
    private final Block block;
    private final ChecklistColor checklistColor;

    CircleSearchOption(CircleSearchOptionBuilder builder) {
        keyword = builder.keyword;
        order = builder.order;
        block = builder.block;
        checklistColor = builder.checklistColor;
    }

    public Order getOrder() {
        return order;
    }

    public boolean hasKeyword() {
        return keyword != null;
    }

    public String getKeyword() {
        return keyword;
    }

    public boolean hasBlock() {
        return block != null;
    }

    public Block getBlock() {
        return block;
    }

    public boolean hasChecklist() {
        return checklistColor != null;
    }

    public ChecklistColor getChecklist() {
        return checklistColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.keyword);
        dest.writeSerializable(this.order);
        dest.writeParcelable(this.block, 0);
        dest.writeInt(this.checklistColor == null ? -1 : this.checklistColor.ordinal());
    }

    private CircleSearchOption(Parcel in) {
        this.keyword = in.readString();
        this.order = (Order) in.readSerializable();
        this.block = in.readParcelable(Block.class.getClassLoader());
        int tmpChecklistColor = in.readInt();
        this.checklistColor = tmpChecklistColor == -1 ? null : ChecklistColor.values()[tmpChecklistColor];
    }

    public static Creator<CircleSearchOption> CREATOR = new Creator<CircleSearchOption>() {
        public CircleSearchOption createFromParcel(Parcel source) {
            return new CircleSearchOption(source);
        }

        public CircleSearchOption[] newArray(int size) {
            return new CircleSearchOption[size];
        }
    };
}
