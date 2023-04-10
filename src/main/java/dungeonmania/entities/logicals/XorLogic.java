package dungeonmania.entities.logicals;

import java.util.List;

public class XorLogic implements LogicStrategy {
    public XorLogic() {
    }

    @Override
    public void update(LogicalEntity logicalEntity, List<Conductor> adjConductors) {
        int nActive = (int) adjConductors.stream().filter(Conductor::isActivated).count();
        if (nActive == 1) {
            logicalEntity.activate();
        } else {
            logicalEntity.deactivate();
        }
    }
}
