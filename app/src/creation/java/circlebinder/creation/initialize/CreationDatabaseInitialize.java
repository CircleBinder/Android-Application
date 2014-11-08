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
import circlebinder.R;
import circlebinder.common.table.EventBlockTable;
import circlebinder.common.event.EventBlockType;
import circlebinder.common.table.EventBlockTableForInsert;
import circlebinder.common.table.EventCircleTable;
import circlebinder.common.table.EventCircleTableForInsert;

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

                EventBlockTableForInsert block = new EventBlockTableForInsert.Builder()
                        .setName(space.get("block"))
                        .setType(EventBlockType.一般的なスタイル)
                        .build();
                EventBlockTable.insert(block);
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
            CreationSpaceFactory creationSpaceFactory = new CreationSpaceFactory();
            while ((line = reader.readLine()) != null) {
                Map<String, String> circle = LTSV.parser().parseLine(line);
                String circleName = circle.get("circle_name");
                if (TextUtils.isEmpty(circleName)) {
                    continue;
                }

                builder.clear();

                Space space = creationSpaceFactory.from(circle.get("space"));

                EventCircleTableForInsert insertCircle = new EventCircleTableForInsert.Builder()
                        .setBlockId(EventBlockTable.get(space.getBlockName()).getId())
                        .setChecklistId(0)
                        .setCircleName(circleName)
                        .setPenName(circle.get("pen_name"))
                        .setHomepage(circle.get("circle_url"))
                        .setSpaceNo(space.getNo())
                        .setSpaceNoSub("a".equals(space.getNoSub()) ? 0 : 1)
                        .build();
                EventCircleTable.insert(insertCircle);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
