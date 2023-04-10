package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicSwitchesTest {
    @Test
    public void testNoWires() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testNoWires", "c");

        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // Activate switch
        res = dmc.tick(Direction.RIGHT);

        //Check switch activated
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));

        // Check light on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        // Push boulder off switch
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        // Check light off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    public void testSwitchDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testSwitchDoor", "c");

        // Activate switch
        res = dmc.tick(Direction.DOWN);

        // Check switch activated
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));

        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        res = dmc.tick(Direction.DOWN);

        // Check position changed
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        // Deactivate switch
        res = dmc.tick(Direction.LEFT);

        //Try enter door again
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

    }

    @Test
    public void testLogicalBomb() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testLogicalBomb", "c");

        // Activate switch
        res = dmc.tick(Direction.RIGHT);

        // Check bomb exploded
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(1, TestUtils.getEntities(res, "wire").size());
    }

    @Test
    public void testSimpleWire() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testSimpleWire", "c");

        //Activate switch
        res = dmc.tick(Direction.RIGHT);

        //Check light activated
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
        assertEquals(0, TestUtils.getEntities(res, "light_bulb_off").size());

        //Push boulder off switch onto wire
        res = dmc.tick(Direction.RIGHT);

        //Check light deactivated
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
        assertEquals(0, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    public void testAndBomb() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testAndBomb", "c");

        // Activate a switch adjacent to the bomb
        res = dmc.tick(Direction.RIGHT);

        // check the "and" logic holds
        assertEquals(1, TestUtils.getEntities(res, "bomb").size());

        // Activate the other switch adjacent to the bomb
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // Check bomb exploded
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(0, TestUtils.getEntities(res, "switch").size());
        assertEquals(0, TestUtils.getEntities(res, "boulder").size());

    }

    @Test
    public void testDoesNotActivate() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testDoesNotActivate", "c");

        //Activate switch
        res = dmc.tick(Direction.UP);

        assertEquals(6, TestUtils.getEntities(res, "light_bulb_off").size());
        assertEquals(0, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    public void testOr() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testOr", "c");

        //Activate 1st switch
        res = dmc.tick(Direction.LEFT);

        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        //Activate 2nd switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    public void testAnd() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testAnd", "c");

        //Activate 1st switch
        res = dmc.tick(Direction.LEFT);

        assertEquals(0, TestUtils.getEntities(res, "light_bulb_on").size());

        //Activate 2nd switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    public void testXor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testXor", "c");

        //Activate 1st switch
        res = dmc.tick(Direction.LEFT);

        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        //Activate 2nd switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        assertEquals(0, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    public void testCoAndFail() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testCoAnd", "c");

        // Turn on top switch
        res = dmc.tick(Direction.LEFT);

        // Check light is off
        assertEquals(0, TestUtils.getEntities(res, "light_bulb_on").size());

        // Turn on bottom switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);

        // Check light is off
        assertEquals(0, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    public void testDestroySwitch() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_testDestroySwitch", "c");

        // Pick up bomb
        res = dmc.tick(Direction.UP);

        //Activate adjacent switch and lightbulb
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        // Activate second switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        // Place bomb
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        // Check successfully detonated
        assertEquals(0, TestUtils.getEntities(res, "switch").size());
        assertEquals(0, TestUtils.getEntities(res, "boulder").size());
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());

        // Check light turned off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }
}
