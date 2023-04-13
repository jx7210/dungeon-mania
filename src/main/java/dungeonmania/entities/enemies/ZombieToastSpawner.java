package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Usable;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Unsubscribable implements Interactable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
    }

    public void spawn(Game game) {
        game.getEntityFactory().spawnZombie(game, this);
    }

    @Override
    public void interact(Player player, Game game) {
        ((Usable) player.getInventory().getWeapon()).use(game);
        game.getMap().destroyEntity(this);
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }

}
