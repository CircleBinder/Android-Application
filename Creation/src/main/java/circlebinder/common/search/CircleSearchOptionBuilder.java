package circlebinder.common.search;

import android.os.Parcel;
import android.os.Parcelable;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;

public final class CircleSearchOptionBuilder implements Parcelable {

    String keyword;
    Order order;
    //TODO: ハードコーディングをやめる
    Block block = new BlockBuilder().setId(-1).build();
    ChecklistColor checklistColor;

    public CircleSearchOptionBuilder() {

    }

    public CircleSearchOptionBuilder(CircleSearchOption searchOption) {
        keyword = searchOption.getKeyword();
        order = searchOption.getOrder();
        block = searchOption.getBlock();
        checklistColor = searchOption.getChecklist();
    }

    public CircleSearchOption build() {
        return new CircleSearchOption(this);
    }

    public CircleSearchOptionBuilder set(CircleSearchOption searchOption) {
        keyword = searchOption.getKeyword();
        order = searchOption.getOrder();
        block = searchOption.getBlock();
        checklistColor = searchOption.getChecklist();
        return this;
    }

    public CircleSearchOptionBuilder setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public CircleSearchOptionBuilder setOrder(Order order) {
        this.order = order;
        return this;
    }

    public CircleSearchOptionBuilder setBlock(Block block) {
        this.block = block;
        return this;
    }

    public CircleSearchOptionBuilder setChecklist(ChecklistColor checklist) {
        this.checklistColor = checklist;
        return this;
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

    private CircleSearchOptionBuilder(Parcel in) {
        this.keyword = in.readString();
        this.order = (Order) in.readSerializable();
        this.block = in.readParcelable(((Object) block).getClass().getClassLoader());
        int tmpChecklistColor = in.readInt();
        this.checklistColor = tmpChecklistColor == -1 ? null : ChecklistColor.values()[tmpChecklistColor];
    }

    public static Creator<CircleSearchOptionBuilder> CREATOR = new Creator<CircleSearchOptionBuilder>() {
        public CircleSearchOptionBuilder createFromParcel(Parcel source) {
            return new CircleSearchOptionBuilder(source);
        }

        public CircleSearchOptionBuilder[] newArray(int size) {
            return new CircleSearchOptionBuilder[size];
        }
    };
}
