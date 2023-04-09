package dungeonmania.goals;

import dungeonmania.Game;

public class Microevolution implements Goal {
    public Microevolution() {
    }

    @Override
    public boolean achieved(Game game) {
        return true;
        // return (game.getDestroyedEnemiesCount() >= target && (game.getMap().getSpawnerCount() == 0));
    }

    @Override
    public String toString(Game game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

}
