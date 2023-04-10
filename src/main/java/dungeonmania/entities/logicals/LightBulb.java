package dungeonmania.entities.logicals;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private boolean on = false;

    public LightBulb(Position position, String logic) {
        super(position, logic);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Spider;
    }

    @Override
    public boolean isActivated() {
        return on;
    }

    public void activate() {
        on = true;
    }

    public void deactivate() {
        on = false;
    }
}
