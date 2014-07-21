package circlebinder.common.checklist;

import circlebinder.Legacy;
import circlebinder.R;

//TODO: インターフェースをすっきりさせたい
public enum ChecklistColor implements Legacy {
    ALL(-1, R.drawable.ic_checklist_none, "全て", R.color.circlebinder_checklist_none),
    NONE(0, R.drawable.ic_checklist_none, "お気に入りからはずす", R.color.circlebinder_checklist_none),
    ORANGE(1, R.drawable.ic_checklist_orange, "お気に入り", R.color.circlebinder_checklist_orange),
    PINK(2, R.drawable.ic_checklist_pink, "お気に入り", R.color.circlebinder_checklist_pink),
    YELLOW(3, R.drawable.ic_checklist_yellow, "お気に入り", R.color.circlebinder_checklist_yellow),
    GREEN(4, R.drawable.ic_checklist_green, "お気に入り", R.color.circlebinder_checklist_green),
    LIGHT_BLUE(5, R.drawable.ic_checklist_light_blue, "お気に入り", R.color.circlebinder_checklist_light_blue),
    PURPLE(6, R.drawable.ic_checklist_purple, "お気に入り", R.color.circlebinder_checklist_purple),
    BLUE(7, R.drawable.ic_checklist_blue, "お気に入り", R.color.circlebinder_checklist_blue),
    LIGHT_GREEN(8, R.drawable.ic_checklist_light_green, "お気に入り", R.color.circlebinder_checklist_light_green),
    RED(9, R.drawable.ic_checklist_red, "お気に入り", R.color.circlebinder_checklist_red),
    ;

    final private int mId;
    final private int mColorDrawable;
    final private String mName;
    final private int mColorResource;

    private ChecklistColor(int id, int colorDrawable, String name, int colorResource) {
        mId = id;
        mColorDrawable = colorDrawable;
        mName = name;
        mColorResource = colorResource;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getDrawableResource() {
        return mColorDrawable;
    }

    public int getColorResource() {
        return mColorResource;
    }

    @Override
    public String toString() {
        return mName;
    }

    public static ChecklistColor valueOf(int ordinal) {
        return values()[ordinal];
    }

    public static ChecklistColor getById(int id) {
        for (ChecklistColor color : values()) {
            if (id == color.getId()) {
                return color;
            }
        }
        return NONE;
    }

    public static ChecklistColor[] checklists() {
        ChecklistColor[] values = values();
        ChecklistColor[] colors = new ChecklistColor[values.length-2];
        for (ChecklistColor color : values) {
            if (color.getId() > 0) {
                colors[color.getId()-1] = color;
            }
        }
        return colors;
    }

    public static boolean isChecklist(ChecklistColor checklist) {
        return checklist.getId() > 0;
    }

}
