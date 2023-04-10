package dungeonmania.entities.enemies;

import dungeonmania.map.GameMap;

public interface Unsubscribable {
    public void onDestroy(GameMap map);
}
