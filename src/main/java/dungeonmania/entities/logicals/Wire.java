package dungeonmania.entities.logicals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Conductor {
    private boolean activated = false;
    private List<Switch> sources = new ArrayList<>();

    public Wire(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public void update() {
        if (sources.stream().anyMatch(source -> source.isActivated())) {
            activate();
        } else {
            deactivate();
        }
    }

    public void subscribe(Switch s) {
        sources.add(s);
    }

    public void unsubscribe(Switch s) {
        sources.remove(s);
        update();
    }

    public void activate() {
        activated = true;
        for (LogicalEntity l : getAdjLogicalEntities()) {
            l.update();
        }
    }

    public void deactivate() {
        activated = false;
        for (LogicalEntity l : getAdjLogicalEntities()) {
            l.update();
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
