package dungeonmania.entities.logicals;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalEntity extends Entity {
    private LogicStrategy logicStrategy = null;

    public LogicStrategy getLogicStrategy() {
        return logicStrategy;
    }

    private List<Conductor> adjConductors = new ArrayList<>();
    private int activeCount = 0;

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public LogicalEntity(Position position, String logic) {
        super(position);

        switch (logic) {
        case "and":
            logicStrategy = new AndLogic();
            break;
        case "or":
            logicStrategy = new OrLogic();
            break;
        case "xor":
            logicStrategy = new XorLogic();
            break;
        case "co_and":
            logicStrategy = new CoAndLogic();
            break;
        case "null":
            logicStrategy = null;
            break;
        }
    }

    public void subscribe(Conductor conductor) {
        adjConductors.add(conductor);
    }

    public void unsubscribe(Conductor conductor) {
        adjConductors.remove(conductor);
        update();
    }

    public void update() {
        if (logicStrategy != null)
            logicStrategy.update(this, adjConductors);
    }

    public abstract void activate();

    public abstract void deactivate();

    public abstract boolean isActivated();

}
