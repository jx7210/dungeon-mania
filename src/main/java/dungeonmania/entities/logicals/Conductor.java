package dungeonmania.entities.logicals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public abstract class Conductor extends Entity {
    private List<LogicalEntity> adjLogicalEntities = new ArrayList<>();
    private List<Conductor> adjConductors = new ArrayList<>();

    public Conductor(Position position) {
        super(position);
    }

    public void subscribe(Conductor conductor) {
        adjConductors.add(conductor);
    }

    public void unsubscribe(Conductor conductor) {
        adjConductors.remove(conductor);
    }

    public void subscribe(LogicalEntity logicalEntity) {
        adjLogicalEntities.add(logicalEntity);
    }

    public void unsubscribe(LogicalEntity logicalEntity) {
        adjLogicalEntities.remove(logicalEntity);
    }

    public abstract boolean isActivated();

    public List<LogicalEntity> getAdjLogicalEntities() {
        return adjLogicalEntities;
    }

    public List<Conductor> getAdjConductors() {
        return adjConductors;
    }
}
