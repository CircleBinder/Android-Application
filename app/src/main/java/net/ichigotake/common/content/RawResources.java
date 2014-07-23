package net.ichigotake.common.content;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RawResources {

    private final Resources resources;

    public RawResources(Resources resources) {
        this.resources = resources;
    }

    public List<String> getText(int rawResourceId) throws IOException {
        InputStream inputStream = resources.openRawResource(rawResourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        List<String> lines = new CopyOnWriteArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
