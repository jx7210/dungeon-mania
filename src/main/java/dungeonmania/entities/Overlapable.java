package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Overlapable {
    public void onOverlap(GameMap map, Entity entity);
}
