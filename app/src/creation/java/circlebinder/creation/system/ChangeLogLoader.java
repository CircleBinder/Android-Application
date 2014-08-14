package circlebinder.creation.system;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import am.ik.ltsv4j.LTSV;
import circlebinder.common.changelog.PublishDate;
import circlebinder.common.changelog.ChangeLogFeed;
import circlebinder.common.changelog.ChangeLogFeedType;
import circlebinder.R;

public final class ChangeLogLoader {

    private final Resources resources;

    public ChangeLogLoader(Context context) {
        this.resources = context.getResources();
    }

    public List<ChangeLogFeed> load() throws IOException {
        List<ChangeLogFeed> changeLogFeeds = new CopyOnWriteArrayList<>();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = resources.openRawResource(R.raw.change_log_ltsv);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                Map<String, String> values = LTSV.parser().parseLine(line);
                changeLogFeeds.add(
                        new ChangeLogFeed(
                                Integer.parseInt(values.get("versionCode")),
                                values.get("versionName"),
                                ChangeLogFeedType.valueOf(values.get("type").toUpperCase(Locale.US)),
                                new PublishDate.Builder()
                                        .setTimestamp(Long.parseLong(values.get("publishDate")))
                                        .setFormattedDate("yyyy-MM-dd")
                                        .build(),
                                values.get("title")
                        )
                );
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return changeLogFeeds;
    }
}
