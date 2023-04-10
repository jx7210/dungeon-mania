package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
