package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Effectible {
    public void onOverlap(GameMap map, Entity entity);
}
