package circlebinder.common.checklist;

import circlebinder.common.Legacy;
import circlebinder.R;

//TODO: インターフェースをすっきりさせたい
public enum ChecklistColor implements Legacy {
    ALL(-1,
            R.drawable.common_ic_checklist_none,
            "全て",
            R.color.common_checklist_none,
            R.style.AppThemeOverlay_Dark),
    NONE(0,
            R.drawable.common_ic_checklist_none,
            "お気に入りからはずす",
            R.color.common_checklist_none,
            R.style.AppThemeOverlay_Dark),
    ORANGE(1,
            R.drawable.common_ic_checklist_orange,
            "お気に入り",
            R.color.common_checklist_orange,
            R.style.AppTheme_Orange),
    PINK(2,
            R.drawable.common_ic_checklist_pink,
            "お気に入り",
            R.color.common_checklist_pink,
            R.style.AppTheme_Pink),
    YELLOW(3,
            R.drawable.common_ic_checklist_yellow,
            "お気に入り",
            R.color.common_checklist_yellow,
            R.style.AppTheme_Yellow),
    GREEN(4,
            R.drawable.common_ic_checklist_green,
            "お気に入り",
            R.color.common_checklist_green,
            R.style.AppTheme_Green),
    LIGHT_BLUE(5,
            R.drawable.common_ic_checklist_light_blue,
            "お気に入り",
            R.color.common_checklist_light_blue,
            R.style.AppTheme_LightBlue),
    PURPLE(6,
            R.drawable.common_ic_checklist_purple,
            "お気に入り",
            R.color.common_checklist_purple,
            R.style.AppTheme_Purple),
    BLUE(7,
            R.drawable.common_ic_checklist_blue,
            "お気に入り",
            R.color.common_checklist_blue,
            R.style.AppTheme_Blue),
    LIGHT_GREEN(8,
            R.drawable.common_ic_checklist_light_green,
            "お気に入り",
            R.color.common_checklist_light_green,
            R.style.AppTheme_LightGreen),
    RED(9,
            R.drawable.common_ic_checklist_red,
            "お気に入り",
            R.color.common_checklist_red,
            R.style.AppTheme_Red),
    ;

    final private int mId;
    final private int mColorDrawable;
    final private String mName;
    final private int mColorResource;
    final private int mStyleResource;

    private ChecklistColor(int id, int colorDrawable, String name, int colorResource, int styleResource) {
        mId = id;
        mColorDrawable = colorDrawable;
        mName = name;
        mColorResource = colorResource;
        mStyleResource = styleResource;
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

    public int getStyleResource() {
        return mStyleResource;
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
