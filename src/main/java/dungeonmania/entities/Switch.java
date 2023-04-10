package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.enemies.Unsubscribable;
import dungeonmania.entities.logicals.Conductor;
import dungeonmania.entities.logicals.LogicalEntity;
import dungeonmania.entities.logicals.Wire;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Conductor implements Effectible, Unsubscribable {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();
    private List<Wire> connectedWires = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    public void subscribe(Wire w) {
        connectedWires.add(w);
    }

    public void unsubscribe(Wire w) {
        connectedWires.remove(w);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            // SCENARIO 1
            activated = true;
            bombs.stream().forEach(b -> b.notify(map));
            for (Wire w : connectedWires) {
                w.update();
            }
            for (LogicalEntity l : getAdjLogicalEntities()) {
                l.update();
            }
        }
    }

    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            // SCENARIO 2
            activated = false;
            for (Wire w : connectedWires) {
                w.update();
            }
            for (LogicalEntity l : getAdjLogicalEntities()) {
                l.update();
            }
        }
    }

    @Override
    public void onDestroy(GameMap map) {
        // SCENARIO 3
        for (Wire w : connectedWires) {
            w.unsubscribe(this);
        }
        for (LogicalEntity l : getAdjLogicalEntities()) {
            l.unsubscribe(this);
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
