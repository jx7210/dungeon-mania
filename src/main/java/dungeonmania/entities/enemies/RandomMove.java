package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomMove implements MoveStategy {
    @Override
    public Position move(Position nextPos, GameMap map, Enemy enemy) {
        Random randGen = new Random();
        List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }
        return nextPos;
    }

}
