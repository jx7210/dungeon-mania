package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public List<String> getBuildables() {

        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class);
        int keys = count(Key.class);
        int sunStones = count(SunStone.class);
        int swords = count(Sword.class);

        List<String> result = new ArrayList<>();

        if (wood >= 1 && arrows >= 3) {
            result.add("bow");
        }
        if (wood >= 2 && (treasure >= 1 || keys >= 1 || sunStones >= 1)) {
            result.add("shield");
        }
        if (swords >= 1 && sunStones >= 1) {
            result.add("midnight_armour");
        }
        if ((wood >= 1 || arrows >= 2) && (treasure >= 1 || keys >= 1 || sunStones >= 2) && sunStones >= 1) {
            result.add("sceptre");
        }
        return result;
    }

    public InventoryItem build(Player p, String type, EntityFactory factory) {

        List<Wood> wood = getEntities(Wood.class);
        List<Arrow> arrows = getEntities(Arrow.class);
        List<Treasure> treasure = getEntities(Treasure.class);
        List<SunStone> sunStones = getEntities(SunStone.class);
        List<Key> keys = getEntities(Key.class);
        List<Sword> swords = getEntities(Sword.class);

        if (wood.size() >= 1 && arrows.size() >= 3 && type.equals("bow")) {
            items.remove(wood.get(0));
            items.remove(arrows.get(0));
            items.remove(arrows.get(1));
            items.remove(arrows.get(2));
            return factory.buildBow();

        } else if (wood.size() >= 2 && (treasure.size() >= 1 || keys.size() >= 1 || sunStones.size() >= 1)
                && type.equals("shield")) {
            items.remove(wood.get(0));
            items.remove(wood.get(1));
            if (sunStones.size() >= 1) {
                return factory.buildSceptre();
            } else if (treasure.size() >= 1) {
                items.remove(treasure.get(0));
            } else if (keys.size() >= 1) {
                items.remove(keys.get(0));
            }
            return factory.buildShield();

        } else if (swords.size() >= 1 && sunStones.size() >= 1 && type.equals("midnight_armour")) {
            items.remove(sunStones.get(0));
            items.remove(swords.get(0));
            return factory.buildMidnightAmour();

        } else if ((wood.size() >= 1 || arrows.size() >= 2)
                && (treasure.size() >= 1 || keys.size() >= 1 || sunStones.size() >= 2) && sunStones.size() >= 1
                && type.equals("sceptre")) {
            items.remove(sunStones.get(0));

            if (wood.size() >= 1) {
                items.remove(wood.get(0));
            } else {
                items.remove(arrows.get(0));
                items.remove(arrows.get(1));
            }

            if (sunStones.size() >= 2) {
                return factory.buildSceptre();
            } else if (treasure.size() >= 1) {
                items.remove(treasure.get(0));
            } else if (keys.size() >= 1) {
                items.remove(keys.get(0));
            }
            return factory.buildSceptre();
        }

        return null;
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

    public boolean hasSceptre() {
        return getFirst(Sceptre.class) != null;
    }

    public int getMindControlDuration() {
        Sceptre s = getFirst(Sceptre.class);
        return s.getMindControlDuration();
    }

}
