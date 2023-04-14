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

public class EnemyGoalTest {
    // Dungeon Config = 1 spider, 0 spawner to defeat
    // beat spider and pass both goals
    @Test
    @Tag("16-1")
    @DisplayName("Test player achieving basic enemy goal kill spider - no spawners")
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
    @DisplayName("Test player achieving basic enemy goal breaks Spawner - no enemies")
    public void testTwo() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test2", "c_microTest_test2");

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

    // PLAYER DOESNT EXIST
    @Test
    @Tag("16-3")
    @DisplayName("Test player not achieve basic enemy goal - player does not exist")
    public void testThree() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test3", "c_microTest_test3");

        List<EntityResponse> entities = initialResponse.getEntities();
        assertEquals(0, TestUtils.countEntityOfType(entities, "player"));

        assertEquals(1, TestUtils.getEntities(initialResponse, "zombie_toast").size());

        // assert goal NOT met
        assertEquals(":enemies", TestUtils.getGoals(initialResponse));
    }

    // Dungeon Config = 1 zombie toast, 0 spawner to defeat
    // beat zombie toast and pass both goals
    @Test
    @Tag("16-4")
    @DisplayName("Test player achieving basic enemy goal kill zombie toast - no spawners")
    public void testFour() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test4", "c_microTest_test4");

        List<EntityResponse> entities = initialResponse.getEntities();
        int zombieToastCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, zombieToastCount);

        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> postBattleEntities = postBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(postBattleEntities, "zombie_toast") == 0);
        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(1, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(postBattleResponse));
    }

    // Dungeon Config = 1 mercenary, 0 spawner to defeat
    // beat zombie toast and pass both goals
    @Test
    @Tag("16-5")
    @DisplayName("Test player achieving basic enemy goal kill mercenary - no spawners")
    public void testFive() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test5", "c_microTest_test5");

        List<EntityResponse> entities = initialResponse.getEntities();
        int mercenaryCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, mercenaryCount);

        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> postBattleEntities = postBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(postBattleEntities, "mercenary") == 0);
        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(1, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(postBattleResponse));
    }

    // kill 2 enemies : 2 zombie toast
    // beat both enemies
    @Test
    @Tag("16-6")
    @DisplayName("Test player achieving basic enemy goal killing 2 enemies - no spawners")
    public void testSix() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test6", "c_microTest_test6");

        List<EntityResponse> entities = initialResponse.getEntities();
        int zombieToastCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(2, zombieToastCount);

        DungeonResponse firstBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> firstBattleEntities = firstBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(firstBattleEntities, "zombie_toast") == 0);

        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(2, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(firstBattleResponse));
    }

    // Dungeon Config = 1 zombie toast, 1 mercenary, 1 spider, 0 spawner to defeat
    // beat all enemies and spawners
    @Test
    @Tag("16-7")
    @DisplayName("Test player achieving basic enemy goal kill 3 enemies - 0 spawners")
    public void testSeven() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test7", "c_microTest_test7");

        List<EntityResponse> entities = initialResponse.getEntities();
        int zombieToastCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        int spiderCount = TestUtils.countEntityOfType(entities, "spider");
        int mercenaryCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, zombieToastCount);
        assertEquals(1, spiderCount);
        assertEquals(1, mercenaryCount);

        DungeonResponse firstBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> firstBattleEntities = firstBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(firstBattleEntities, "zombie_toast") == 0);
        assertTrue(TestUtils.countEntityOfType(firstBattleEntities, "spider") == 0);
        assertTrue(TestUtils.countEntityOfType(firstBattleEntities, "mercenary") == 1);
        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(2, player.getDefeatedEnemiesCount());

        DungeonResponse secondBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> secondBattleEntities = secondBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(secondBattleEntities, "zombie_toast") == 0);
        assertTrue(TestUtils.countEntityOfType(secondBattleEntities, "spider") == 0);
        assertTrue(TestUtils.countEntityOfType(secondBattleEntities, "mercenary") == 0);
        assertEquals(3, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(secondBattleResponse));
    }

    // Dungeon Config = 1 spider, 1 spawner to defeat
    // beat all enemies and spawners
    @Test
    @Tag("16-8")
    @DisplayName("Test player achieving basic enemy goal kill 1 spider - 1 spawner")
    public void testEight() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_microTest_test8", "c_microTest_test8");

        List<EntityResponse> entities = initialResponse.getEntities();
        int zombieToastSpawnerCount = TestUtils.countEntityOfType(entities, "zombie_toast_spawner");
        int spiderCount = TestUtils.countEntityOfType(entities, "spider");
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, spiderCount);
        assertEquals(1, zombieToastSpawnerCount);
        String spawnerId = TestUtils.getEntities(initialResponse, "zombie_toast_spawner").get(0).getId();
        assertThrows(InvalidActionException.class, () -> controller.interact(spawnerId));

        // 1.
        DungeonResponse firstBattleResponse = controller.tick(Direction.RIGHT);

        List<EntityResponse> firstBattleEntities = firstBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(firstBattleEntities, "spider") == 0);
        assertTrue(TestUtils.countEntityOfType(firstBattleEntities, "zombie_toast_spawner") == 1);
        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(1, player.getDefeatedEnemiesCount());

        // 2.
        DungeonResponse secondBattleResponse = controller.tick(Direction.RIGHT);
        // has sword
        assertEquals(1, TestUtils.getInventory(secondBattleResponse, "sword").size());

        List<EntityResponse> secondBattleEntities = secondBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(secondBattleEntities, "spider") == 0);
        assertTrue(TestUtils.countEntityOfType(secondBattleEntities, "zombie_toast_spawner") == 1);
        assertEquals(1, player.getDefeatedEnemiesCount());

        // 3.
        DungeonResponse thirdBattleResponse = controller.tick(Direction.DOWN);
        // has sword
        assertEquals(1, TestUtils.getInventory(thirdBattleResponse, "sword").size());

        List<EntityResponse> thirdBattleEntities = thirdBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(thirdBattleEntities, "spider") == 0);
        assertTrue(TestUtils.countEntityOfType(thirdBattleEntities, "zombie_toast_spawner") == 1);
        assertEquals(1, player.getDefeatedEnemiesCount());

        // 4.
        DungeonResponse fourthBattleResponse = controller.tick(Direction.RIGHT);

        // cardinally adjacent: true, has sword: true, but invalid_id
        assertThrows(IllegalArgumentException.class, () -> controller.interact("random_invalid_id"));
        // cardinally adjacent: true, has sword: true
        fourthBattleResponse = assertDoesNotThrow(() -> controller.interact(spawnerId));

        // has sword
        assertEquals(1, TestUtils.getInventory(fourthBattleResponse, "sword").size());
        List<EntityResponse> fourthBattleEntities = fourthBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(fourthBattleEntities, "spider") == 0);
        assertTrue(TestUtils.countEntityOfType(fourthBattleEntities, "zombie_toast_spawner") == 0);
        assertEquals(1, player.getDefeatedEnemiesCount());

        // assert goal met
        assertEquals("", TestUtils.getGoals(fourthBattleResponse));
    }

}
