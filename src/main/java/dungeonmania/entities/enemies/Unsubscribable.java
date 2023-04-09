package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Unsubscribable extends Entity {
    public Unsubscribable(Position position) {
        super(position);
    }

    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }
}
