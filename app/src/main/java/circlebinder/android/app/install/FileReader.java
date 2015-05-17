package circlebinder.android.app.install;

import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

class FileReader {

    private final Resources resources;

    FileReader(Resources resources) {
        this.resources = resources;
    }

    <T> Result<List<T>> parse(@RawRes int rawRes, Func1<String, T> map) {
        InputStream inputStream = resources.openRawResource(rawRes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        List<Throwable> errors = new ArrayList<>();
        List<T> list = new ArrayList<>();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                list.add(map.call(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
            errors.add(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                errors.add(e);
                e.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                errors.add(e);
                e.printStackTrace();
            }
        }
        return new Result<>(errors, list);
    }
}
