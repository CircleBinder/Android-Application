// IEventImportServiceIF.aidl
package circlebinder.android.app.service;

// Declare any non-default types here with import statements

import circlebinder.android.app.service.IBackgroundServiceListener;
import circlebinder.android.app.service.IServiceFutureFactory;

oneway interface IBackgroundServiceIF {

    void registerListener(in IBackgroundServiceListener listener);
    void unregisterListener(in IBackgroundServiceListener listener);
    void sendCommand(in IServiceFutureFactory command);

}
