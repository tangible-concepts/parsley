package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Entity
public class Movement extends Link {

    private final List<Inertia> inertias = new LinkedList<>();

    public Movement(Component from, EvolvedComponent to) {
        super(from, to);
    }

    public void addInertia(Inertia inertia) {
        this.inertias.add(inertia);
    }

    public Optional<List<Inertia>> getInertias() {
        return inertias.isEmpty() ? Optional.empty() : Optional.of(inertias);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "from=" + from.getName() +
                ", to=" + to.getName() +
                ", inertias=" + inertias +
                '}';
    }
}
