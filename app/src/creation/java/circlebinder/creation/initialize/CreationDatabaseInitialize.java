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
import circlebinder.Legacy;
import circlebinder.common.event.CircleBuilder;
import circlebinder.common.event.Space;
import circlebinder.common.event.SpaceFactory;
import circlebinder.R;
import circlebinder.creation.event.BlockTable;
import circlebinder.creation.event.CircleTable;

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

        new AppStorage(context).setInitialized(true);
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

                BlockTable blockTable = new BlockTable();
                blockTable.name = space.get("block");
                blockTable.save();
            }
        } catch (IOException e) {
            throw e;
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
                if (TextUtils.isEmpty(circle.get("circle_name"))) {
                    continue;
                }

                builder.clear();

                Space space = spaceFactory.from(circle.get("space"));
                CircleTable circleTable = new CircleTable();
                circleTable.blockId = BlockTable.get(space.getBlockName()).getId();
                circleTable.checklistId = 0;
                circleTable.name = circle.get("circle_name");
                circleTable.penName = circle.get("pen_name");
                circleTable.homepageUrl = circle.get("circle_url");
                circleTable.spaceNo = space.getNo();
                circleTable.spaceNoSub = "a".equals(space.getNoSub()) ? 0 : 1;
                circleTable.save();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
