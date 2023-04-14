package dungeonmania.entities.inventory;

import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;

public interface BuildStrategy {
    public InventoryItem build(Player player, EntityFactory factory);
}
