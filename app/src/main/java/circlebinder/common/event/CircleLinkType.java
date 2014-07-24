package circlebinder.common.event;

import circlebinder.R;

public enum CircleLinkType {

    PIXIV(R.string.common_circle_link_pixiv),
    HOMEPAGE(R.string.common_circle_link_homepage),
    ;

    private final int name;

    private CircleLinkType(int name) {
        this.name = name;
    }

    public int getNameResource() {
        return name;
    }

}
