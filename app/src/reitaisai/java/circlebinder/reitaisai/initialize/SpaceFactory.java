package circlebinder.reitaisai.initialize;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import circlebinder.common.event.Space;
import circlebinder.common.event.SpaceBuilder;

public final class SpaceFactory {

    /**
     * "あ01a" のような文字列から {@link circlebinder.common.event.Space} を生成する
     */
    public Space from(String name) {
        Pattern pattern = Pattern.compile("([^0-9]+)(\\d+)([^0-9])");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find()) {
            throw new RuntimeException();
        }
        String blockName = matcher.group(1);
        int spaceNo = Integer.parseInt(matcher.group(2));
        String spaceNuSub = matcher.group(3);

        return new SpaceBuilder()
                .setBlockName(blockName)
                .setNo(spaceNo)
                .setNoSub(spaceNuSub)
                .setName(String.format("%s-%02d%s", blockName, spaceNo, spaceNuSub))
                .setSimpleName(String.format("%s%02d%s", blockName, spaceNo, spaceNuSub))
                .build();
    }

}
