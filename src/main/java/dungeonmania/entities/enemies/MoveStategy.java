package dungeonmania.entities.enemies;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public interface MoveStategy {
    public Position move(Position nextPos, GameMap map, Enemy enemy);
}
