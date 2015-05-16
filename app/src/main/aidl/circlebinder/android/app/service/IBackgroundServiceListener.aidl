// IBackgroundServiceListener.aidl
package circlebinder.android.app.service;

import circlebinder.android.app.service.IServiceCompleted;

interface IBackgroundServiceListener {

    void onEvent(in IServiceCompleted event);

}
