// IEventImportServiceListener.aidl
package circlebinder.android.app.service;

// Declare any non-default types here with import statements

import circlebinder.android.app.service.IServiceCompleted;

interface IBackgroundServiceListener {

    void onEvent(in IServiceCompleted event);

}
