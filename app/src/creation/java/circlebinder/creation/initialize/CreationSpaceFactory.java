package circlebinder.creation.initialize;

import circlebinder.common.event.Space;
import circlebinder.common.event.SpaceBuilder;

public final class CreationSpaceFactory {

    /**
     * "あ01a" のような文字列から {@link circlebinder.common.event.Space} を生成する
     */
    public Space from(String name) {
        String blockName = name.substring(0, 1);
        int spaceNo = Integer.parseInt(name.substring(1, 3));
        String spaceNuSub = name.substring(3);

        return new SpaceBuilder()
                .setBlockName(blockName)
                .setNo(spaceNo)
                .setNoSub(spaceNuSub)
                .setName(String.format("%s-%02d%s", blockName, spaceNo, spaceNuSub))
                .setSimpleName(String.format("%s%02d%s", blockName, spaceNo, spaceNuSub))
                .build();
    }

}
