package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

@Entity
public class Link extends MapElement {

    public static final String CONCATENATOR = "->";

    protected final Component from;
    protected final Component to;

    public Link(Component from, Component to) {
        super();
        this.from = from;
        this.to = to;
    }

    public Name deriveName() {
        return Name.of(from + CONCATENATOR + to);
    }

    public Component getFrom() {
        return from;
    }

    public Component getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Link{" +
                "from=" + from.getName() +
                ", to=" + to.getName() +
                '}';
    }
}
