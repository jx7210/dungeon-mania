package dungeonmania.entities.inventory;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;

public class BuildBow implements BuildStrategy {
    private List<InventoryItem> items;

    public BuildBow(List<InventoryItem> items) {
        this.items = items;
    }

    @Override
    public InventoryItem build(Player p, EntityFactory factory) {
        List<Wood> wood = getEntities(Wood.class);
        List<Arrow> arrows = getEntities(Arrow.class);

        if (wood.size() >= 1 && arrows.size() >= 3) {
            items.remove(wood.get(0));
            items.remove(arrows.get(0));
            items.remove(arrows.get(1));
            items.remove(arrows.get(2));
            return factory.buildBow();
        }
        return null;
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

}
