package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;
    private MoveStategy runAway = new RunAway();
    private MoveStategy random = new RandomMove();

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public void move(Game game) {
        Position nextPos = null;
        GameMap map = game.getMap();
        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            //RunAway
            nextPos = runAway.move(nextPos, map, this);
        } else {
            //Move randomly
            nextPos = random.move(nextPos, map, this);
        }
        game.getMap().moveTo(this, nextPos);

    }
}
