package dungeonmania.entities.logicals;

import java.util.List;

public class AndLogic implements LogicStrategy {
    public AndLogic() {
    }

    public void update(LogicalEntity logicalEntity, List<Conductor> adjConductors) {
        int nActive = (int) adjConductors.stream().filter(Conductor::isActivated).count();
        if (nActive == adjConductors.size() && nActive >= 2) {
            logicalEntity.activate();
        } else {
            logicalEntity.deactivate();
        }
    }
}
