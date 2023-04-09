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

public class MicroevoGoalTest {
    // dungeon config = 1 spider, 0 spawner to defeat
    // beat spider and pass both goals
    @Test
    @Tag("16-1")
    @DisplayName("Test player battles spider and spider dies")
    public void testSpiderDiesWhenBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = TestUtils.genericSpiderSequence(controller,
                "c_battleTest_basicSpiderSpiderDies");
        List<EntityResponse> entities = postBattleResponse.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "spider") == 0);
        Game game = controller.getGame();
        Player player = game.getPlayer();
        assertEquals(1, player.getDefeatedEnemiesCount());
    }
}
