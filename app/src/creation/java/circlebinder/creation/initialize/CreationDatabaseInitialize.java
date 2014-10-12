package circlebinder.creation.initialize;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import am.ik.ltsv4j.LTSV;
import circlebinder.common.Legacy;
import circlebinder.common.event.CircleBuilder;
import circlebinder.common.event.Space;
import circlebinder.common.event.SpaceFactory;
import circlebinder.R;
import circlebinder.creation.event.EventBlockTable;
import circlebinder.creation.event.EventBlockType;
import circlebinder.creation.event.EventCircleTable;

abstract class CreationDatabaseInitialize implements Runnable, Legacy {

    private final Context context;
    private final CircleBuilder builder;
    private final Resources resources;

    CreationDatabaseInitialize(Context context) {
        this.context = context;
        this.resources = context.getResources();
        this.builder = new CircleBuilder();
    }

    abstract void finished();

    @Override
    public void run() {
        try {
            ActiveAndroid.beginTransaction();
            initBlock();
            initCircle();
            ActiveAndroid.setTransactionSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }

        new LegacyAppStorage(context).setInitialized(true);
        finished();
    }

    private void initBlock() throws IOException {
        InputStream inputStream = resources.openRawResource(R.raw.creation_spaces);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, String> space = LTSV.parser().parseLine(line);
                if (TextUtils.isEmpty(space.get("block"))) {
                    continue;
                }

                EventBlockTable blockTable = new EventBlockTable();
                blockTable.blockName = space.get("block");
                blockTable.blockTypeId = EventBlockType.一般的なスタイル.getTypeId();
                blockTable.save();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void initCircle() throws IOException {
        InputStream inputStream = resources.openRawResource(R.raw.creation_circles);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            SpaceFactory spaceFactory = new SpaceFactory();
            while ((line = reader.readLine()) != null) {
                Map<String, String> circle = LTSV.parser().parseLine(line);
                String circleName = circle.get("circle_name");
                if (TextUtils.isEmpty(circleName)) {
                    continue;
                }

                builder.clear();

                Space space = spaceFactory.from(circle.get("space"));
                EventCircleTable circleTable = new EventCircleTable();
                circleTable.blockId = EventBlockTable.get(space.getBlockName()).getId();
                circleTable.checklistId = 0;
                circleTable.circleName = circleName;
                circleTable.penName = circle.get("pen_name");
                circleTable.homepage = circle.get("circle_url");
                circleTable.spaceNo = space.getNo();
                circleTable.spaceNoSub = "a".equals(space.getNoSub()) ? 0 : 1;
                circleTable.save();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
