package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import dungeonmania.util.Direction;

public class MicroevoGoalTest {
    // dungeon config = 1 spider, 0 spawner to defeat
    // beat spider and pass both goals
    @Test
    @Tag("16-1")
    @DisplayName("Test player battles spider and spider dies")
    public void testOne() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test1", "c_microTest_test1");

        List<EntityResponse> entities = initialResponse.getEntities();
        int spiderCount = TestUtils.countEntityOfType(entities, "spider");
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, spiderCount);

        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> postBattleEntities = postBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(postBattleEntities, "spider") == 0);
        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(1, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(postBattleResponse));
    }

    // Dungeon Config = 0 enemy, 1 spawner to defeat
    // Beat spawner to pass goal
    @Test
    @Tag("16-2")
    @DisplayName("Test player breaks Spawner")
    public void testTwo() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test2", "c_microTest_test2");

        List<EntityResponse> entities = initialResponse.getEntities();

        assertEquals(1, TestUtils.getEntities(initialResponse, "zombie_toast_spawner").size());
        String spawnerId = TestUtils.getEntities(initialResponse, "zombie_toast_spawner").get(0).getId();

        // cardinally adjacent: true, has sword: false
        assertThrows(InvalidActionException.class, () -> controller.interact(spawnerId));
        assertEquals(1, TestUtils.getEntities(initialResponse, "zombie_toast_spawner").size());

        // pick up sword
        initialResponse = controller.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(initialResponse, "sword").size());

        // cardinally adjacent: false, has sword: true
        assertThrows(InvalidActionException.class, () -> controller.interact(spawnerId));
        assertEquals(1, TestUtils.getEntities(initialResponse, "zombie_toast_spawner").size());

        // move right
        initialResponse = controller.tick(Direction.RIGHT);

        // cardinally adjacent: true, has sword: true, but invalid_id
        assertThrows(IllegalArgumentException.class, () -> controller.interact("random_invalid_id"));
        // cardinally adjacent: true, has sword: true
        initialResponse = assertDoesNotThrow(() -> controller.interact(spawnerId));

        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(0, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(initialResponse));
    }
}
