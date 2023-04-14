package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface OnOverlap {
    public void onOverlap(GameMap map, Entity entity);
}
