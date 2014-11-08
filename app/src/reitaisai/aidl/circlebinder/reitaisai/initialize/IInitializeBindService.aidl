package circlebinder.reitaisai.initialize;

import circlebinder.reitaisai.initialize.IInitializeServiceCallback;

interface IInitializeBindService {

    oneway void setObserver(IInitializeServiceCallback callback);

    oneway void removeObserver(IInitializeServiceCallback callback);

    void AsyncStart();
}
