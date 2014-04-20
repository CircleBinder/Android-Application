package circlebinder.creation.initialize;

import circlebinder.creation.initialize.IInitializeServiceCallback;

interface IInitializeBindService {

    oneway void setObserver(IInitializeServiceCallback callback);

    oneway void removeObserver(IInitializeServiceCallback callback);

    void AsyncStart();
}
