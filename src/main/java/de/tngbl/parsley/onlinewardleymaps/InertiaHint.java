package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.wardleymapping.Component;

/**
 * Signals the need to add inertia to the {@link de.tngbl.parsley.wardleymapping.Movement} of the given {@link Component}
 */
public class InertiaHint implements InterpreterHint {

    private final Component component;

    public InertiaHint(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public InterpreterHintStrategy getStrategy() {
        return new InertiaHintStrategy(this);
    }
}
