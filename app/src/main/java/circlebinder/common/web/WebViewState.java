package circlebinder.common.web;

import android.os.Parcel;
import android.os.Parcelable;

public final class WebViewState implements Parcelable {

    private String url;

    public WebViewState(android.webkit.WebView webView) {
        this.url = webView.getUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public WebViewState() {
    }

    private WebViewState(Parcel in) {
        this.url = in.readString();
    }

    public static final Creator<WebViewState> CREATOR = new Creator<WebViewState>() {
        public WebViewState createFromParcel(Parcel source) {
            return new WebViewState(source);
        }

        public WebViewState[] newArray(int size) {
            return new WebViewState[size];
        }
    };

    public String getUrl() {
        return url;
    }
}
