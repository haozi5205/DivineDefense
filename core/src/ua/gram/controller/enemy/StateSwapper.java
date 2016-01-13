package ua.gram.controller.enemy;

import ua.gram.model.actor.GameActor;
import ua.gram.model.state.StateInterface;

/**
 * Swap state with new state for provided actor
 * @author Gram <gram7gram@gmail.com>
 */
public class StateSwapper<E extends GameActor> implements Runnable {

    private E actor;
    private StateInterface<E> state1;
    private StateInterface<E> state2;
    private int level;

    public StateSwapper update(E actor,
                               StateInterface<E> state1,
                               StateInterface<E> state2,
                               int level) {
        this.actor = actor;
        this.state1 = state1;
        this.state2 = state2;
        this.level = level;

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        actor.getStateManager().swap(actor, state1, state2, level);
        reset();
    }

    private void reset() {
        this.actor = null;
        this.state1 = null;
        this.state2 = null;
        this.level = 0;
    }
}