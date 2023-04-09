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
    // no enemies all spawners
    @Test
    @Tag("16-1")
    @DisplayName("Test 0 enemies all spawners")

    public void testPlayerKillsAtLeastFiveEnemies() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testMicro", "c_testMicro");

        // assert goal not met
        // assertTrue(TestUtils.getGoals(res).contains(":boulders"));

        // assertTrue(initPlayer.getDefeatedEnemiesCount() >= 1);
        // get Player
    }

    @Test
    @Tag("16-2")
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
