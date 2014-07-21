package circlebinder.common.event;

public final class SpaceFactory {

    /**
     * "あ01a" のような文字列から {@link Space} を生成する
     * @param name
     * @return space
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
