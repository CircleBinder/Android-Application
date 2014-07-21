package circlebinder.common.test;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;

public final class ParcelUtil {

    @SuppressWarnings("unchecked")
    public static <T extends Parcelable> T restore(T object) throws Exception {
        Parcel parcel = Parcel.obtain();

        object.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        Field creator = object.getClass().getDeclaredField("CREATOR");

        T restoredObject = ((Parcelable.Creator<T>)creator.get(null)).createFromParcel(parcel);
        if (restoredObject == null) {
            throw new NullPointerException();
        }

        return restoredObject;
    }
}
