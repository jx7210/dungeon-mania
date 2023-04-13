package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FurtherBuildablesTest {

    // Honestly, preference of which material is consumed isn't a requirement in the spec
    // so prob won't be tested in any autotests. Ok to add in our tests tho
    @Test
    public void testOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_furtherBuildablesTest_testOpenDoor",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // Pick up sun stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());

        // Walk through door
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        // Check sun stone retained
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    public void testShieldSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_furtherBuildablesTest_testShieldSunStone",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // Pick up materials
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Confirm inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Build shield
        res = assertDoesNotThrow(() -> dmc.build("shield"));

        // Check inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "shield").size());
    }

    @Test
    public void testCanBuildArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_furtherBuildablesTest_testCanBuildArmour",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // Check that it can't be built
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));

        // Pick up materials
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Confirm inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Build armour
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        // Check inventory
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    public void testCannotBuildArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_furtherBuildablesTest_testCannotBuildArmour",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // Pick up materials
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Confirm inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Build armour 
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    public void testArmourEffect() throws InvalidActionException {
        DungeonManiaController controller;
        controller = new DungeonManiaController();
        String config = "c_furtherBuildablesTest_testArmourEffect";
        DungeonResponse res = controller.newGame("d_furtherBuildablesTest_testArmourEffect", config);

        // Pick up Materials
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // Confirm inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        res = controller.build("midnight_armour");

        res = controller.tick(Direction.RIGHT);

        BattleResponse battle = res.getBattles().get(0);

        RoundResponse firstRound = battle.getRounds().get(0);

        // Assumption: Armour effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - Armour effect
        int enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("spider_attack", config));
        int shieldEffect = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));
        int expectedDamage = (enemyAttack - shieldEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);

        // Check if armour increases player attack damage
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double armourAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        assertEquals((playerBaseAttack + armourAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }

    @Test
    public void testBuildSceptre() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_furtherBuildablesTest_testBuildSceptre",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // Pick up materials
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Build first sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));

        // Confirm inventory
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        // Pick up materials
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Build second sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));

        // Confirm inventory
        assertEquals(2, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
    }

    @Test
    public void testControlMercenary() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_furtherBuildablesTest_testControlMercenary",
                "c_furtherBuildablesTest_testControlMercenary");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Pick up materials
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Check error trying to interact without sceptre
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));

        // Build sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // attempt mind control
        assertDoesNotThrow(() -> dmc.interact(mercId));

        // Wait until mind control duration ends
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, res.getBattles().size());

        // Check that mercenary has snapped out of mind control and battles player
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, res.getBattles().size());
    }
}
