package dungeonmania.entities.buildables;

import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    public static final int DEFAULT_DURATION = 1;
    private int mindControlDuration;

    public Sceptre(int mindControlDuration) {
        super(null);
        if (mindControlDuration < 0) {
            this.mindControlDuration = 0;
        }
        this.mindControlDuration = mindControlDuration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return null;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }

}
