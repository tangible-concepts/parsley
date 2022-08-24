package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

@Entity
public class EvolvedComponent extends Component {

    public static final String CONCATENATOR = "--->";

    private final Movement movement;

    public EvolvedComponent(Component origin, EvolutionLevel evolution) {
        super(new Name(origin.getName()), origin.getVisibility(), evolution);
        this.movement = new Movement(origin,this);
    }

    public Movement getMovement() {
        return movement;
    }

    public Name deriveName() {
        return Name.of(String.format("%s %s [%s]", super.getName().value(),CONCATENATOR, evolution.value()));
    }

    public boolean hasInertia() {
        return movement.getFrom().hasInertia();
    }

    @Override
    public String toString() {
        return "EvolvedComponent{" +
                "name=" + name +
                ", visibility=" + visibility +
                ", evolution=" + evolution +
                ", evolutionRelationship=" + movement +
                '}';
    }
}
