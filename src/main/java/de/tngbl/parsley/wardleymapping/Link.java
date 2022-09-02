package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

@Entity
public class Link extends MapElement {

    public static final String CONCATENATOR = "->";

    protected final ComponentBase from;
    protected final ComponentBase to;

    public Link(ComponentBase from, ComponentBase to) {
        super();
        this.from = from;
        this.to = to;
    }

    public Name deriveName() {
        return Name.of(from + CONCATENATOR + to);
    }

    public ComponentBase getFrom() {
        return from;
    }

    public ComponentBase getTo() {
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
