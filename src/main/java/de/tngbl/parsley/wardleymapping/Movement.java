package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

@Entity
public class Movement extends Link {

    public Movement(Component from, EvolvedComponent to) {
        super(from, to);
    }

    public boolean hasInertia() {
        return getFrom().hasInertia();
    }
}
