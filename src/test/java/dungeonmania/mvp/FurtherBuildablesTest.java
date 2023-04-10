package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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

        // Add test for not having enough inventory...

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
    public void testArmourReducesEnemyAttack() {
        // u have to do maths to test these...
        // maybe can just copy paste the battletests
    }

    @Test
    public void testArmourIncreasesPlayerAttack() {
    }

    @Test
    public void testBuildSceptre() {
        // try 1 wood, 1 treasure and 1 sun stone
        // then try 2 arrows, 2 sun stone 
    }

    @Test
    public void testControlMercenary() {
        // this one is a bit hard
    }

}
