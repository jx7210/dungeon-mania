package dungeonmania.entities.inventory;

import java.util.List;
import java.util.stream.Collectors;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;

public class BuildMidnightArmour implements BuildStrategy {
    private List<InventoryItem> items;

    public BuildMidnightArmour(List<InventoryItem> items) {
        this.items = items;
    }

    @Override
    public InventoryItem build(Player p, EntityFactory factory) {
        List<SunStone> sunStones = getEntities(SunStone.class);
        List<Sword> swords = getEntities(Sword.class);

        if (swords.size() >= 1 && sunStones.size() >= 1) {
            items.remove(sunStones.get(0));
            items.remove(swords.get(0));
            return factory.buildMidnightAmour();

        }
        return null;
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }
}
