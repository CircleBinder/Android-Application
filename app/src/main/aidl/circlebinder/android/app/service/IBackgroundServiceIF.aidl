// IBackgroundServiceIF.aidl
package circlebinder.android.app.service;

import circlebinder.android.app.service.IBackgroundServiceListener;
import circlebinder.android.app.service.IObservableFactory;

oneway interface IBackgroundServiceIF {

    void registerListener(in IBackgroundServiceListener listener);
    void unregisterListener(in IBackgroundServiceListener listener);
    void sendCommand(in IObservableFactory command);

}
