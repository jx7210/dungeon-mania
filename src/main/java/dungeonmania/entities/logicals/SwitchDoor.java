package dungeonmania.entities.logicals;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private boolean open = false;

    public SwitchDoor(Position position, String logic) {
        super(position.asLayer(Entity.DOOR_LAYER), logic);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isActivated() {
        return open;
    }

    public void activate() {
        open = true;
    }

    public void deactivate() {
        open = false;
    }
}
