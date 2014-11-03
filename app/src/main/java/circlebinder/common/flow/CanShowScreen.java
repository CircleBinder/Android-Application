package circlebinder.common.flow;

import flow.Flow;

public interface CanShowScreen {

    void showScreen(Screen screen, Flow.Direction direction, Flow.Callback callback);

}
