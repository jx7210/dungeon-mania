package dungeonmania.entities.logicals;

import java.util.List;

public class OrLogic implements LogicStrategy {
    public OrLogic() {
    }

    public void update(LogicalEntity logicalEntity, List<Conductor> adjConductors) {
        if (adjConductors.stream().anyMatch(Conductor::isActivated)) {
            logicalEntity.activate();
        } else {
            logicalEntity.deactivate();
        }
    }
}
