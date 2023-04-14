package dungeonmania.entities.inventory;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;

public class BuildShield extends Inventory implements BuildStrategy {
    private List<InventoryItem> items;

    public BuildShield(List<InventoryItem> items) {
        this.items = items;
    }

    @Override
    public InventoryItem build(Player p, EntityFactory factory) {
        List<Wood> wood = getEntities(Wood.class);
        List<Treasure> treasure = getEntities(Treasure.class);
        List<SunStone> sunStones = getEntities(SunStone.class);
        List<Key> keys = getEntities(Key.class);

        if (wood.size() >= 2 && (treasure.size() >= 1 || keys.size() >= 1 || sunStones.size() >= 1)) {
            items.remove(wood.get(0));
            items.remove(wood.get(1));
            if (sunStones.size() >= 1) {
                return factory.buildShield();
            } else if (treasure.size() >= 1) {
                items.remove(treasure.get(0));
            } else if (keys.size() >= 1) {
                items.remove(keys.get(0));
            }
            return factory.buildShield();
        }
        return null;
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

}
