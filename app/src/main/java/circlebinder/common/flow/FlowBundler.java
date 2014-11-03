package circlebinder.common.flow;

import android.os.Bundle;

import flow.Backstack;
import flow.Flow;
import flow.Parcer;

public final class FlowBundler {
    private final static String FLOW_KEY = "flow_key";
    private final Object defaultScreen;
    private final Flow.Listener listener;
    private final Parcer<Object> parcer;

    private Flow flow;

    public FlowBundler(Object defaultScreen, Flow.Listener listener) {
        this(defaultScreen, listener, new NoParameterParcer<>());
    }

    public FlowBundler(Object defaultScreen, Flow.Listener listener, Parcer<Object> parcer) {
        this.defaultScreen = defaultScreen;
        this.listener = listener;
        this.parcer = parcer;
    }

    public AppFlow onCreate(Bundle savedInstanceState) {
        Backstack backstack;
        if (savedInstanceState != null && savedInstanceState.containsKey(FLOW_KEY)) {
            backstack = Backstack.from(savedInstanceState.getParcelable(FLOW_KEY), parcer);
        } else {
            backstack = Backstack.fromUpChain(defaultScreen);
        }
        flow = new Flow(backstack, listener);
        return new AppFlow(flow);
    }

    public void onSaveInstanceState(Bundle outState) {
        Backstack backstack = getBackstackToSave(flow.getBackstack());
        if (backstack == null) {
            return;
        }
        outState.putParcelable(FLOW_KEY, backstack.getParcelable(parcer));
    }

    public Flow getFlow() {
        return flow;
    }

    /**
     * Returns the backstack that should be archived by {@link #onSaveInstanceState}. Overriding
     * allows subclasses to handle cases where the current configuration is not one that should
     * survive process death.  The default implementation returns a BackStackToSave that specifies
     * that view state should be persisted.
     *
     * @return the stack to archive, or null to archive nothing
     */
    protected Backstack getBackstackToSave(Backstack backstack) {
        return backstack;
    }

}
