package dungeonmania.entities.logicals;

import java.util.List;

public class CoAndLogic implements LogicStrategy {
    public CoAndLogic() {
    }

    @Override
    public void update(LogicalEntity logicalEntity, List<Conductor> adjConductors) {
        int count = logicalEntity.getActiveCount();
        int nActive = (int) adjConductors.stream().filter(Conductor::isActivated).count();
        if (nActive == adjConductors.size() && nActive >= 2 && count == 0) {
            logicalEntity.activate();
        } else if (nActive == adjConductors.size() && nActive >= 2) {
            return;
        } else {
            logicalEntity.deactivate();
        }
        logicalEntity.setActiveCount(nActive);
    }

}
