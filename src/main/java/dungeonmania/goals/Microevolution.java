package dungeonmania.goals;

import dungeonmania.Game;

public class Microevolution implements Goal {
    private int target;

    public Microevolution(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;
        return game.getPlayer().getDefeatedEnemiesCount() >= target && game.getMap().countSpawners() == 0;
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return ":enemies";
    }

}
