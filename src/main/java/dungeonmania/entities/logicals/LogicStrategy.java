package dungeonmania.entities.logicals;

import java.util.List;

public interface LogicStrategy {
    public void update(LogicalEntity logicalEntity, List<Conductor> adjConductors);
}
