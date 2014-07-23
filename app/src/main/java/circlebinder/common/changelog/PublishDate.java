package circlebinder.common.changelog;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class PublishDate implements Parcelable {

    public final static class Builder {
        //TODO: デフォルトのフォーマットを考えておく
        private long timestampMillSeconds;
        private String format;

        public PublishDate build() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestampMillSeconds);
            SimpleDateFormat.getDateInstance().setCalendar(calendar);
            String formattedDate = DateFormat.format(format, calendar).toString();
            return new PublishDate(timestampMillSeconds, formattedDate);
        }

        public Builder setTimestamp(long millSeconds) {
            this.timestampMillSeconds = millSeconds;
            return this;
        }

        public Builder setFormattedDate(String format) {
            this.format = format;
            return this;
        }
    }

    private final long timestampMillSeconds;
    private final String formattedDate;

    private PublishDate(long timestampMillSeconds, String formattedDate) {
        this.timestampMillSeconds = timestampMillSeconds;
        this.formattedDate = formattedDate;
    }

    public long getTimestamp() {
        return timestampMillSeconds;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.timestampMillSeconds);
        dest.writeString(this.formattedDate);
    }

    private PublishDate(Parcel in) {
        this.timestampMillSeconds = in.readLong();
        this.formattedDate = in.readString();
    }

    public static Creator<PublishDate> CREATOR = new Creator<PublishDate>() {
        public PublishDate createFromParcel(Parcel source) {
            return new PublishDate(source);
        }

        public PublishDate[] newArray(int size) {
            return new PublishDate[size];
        }
    };
}
