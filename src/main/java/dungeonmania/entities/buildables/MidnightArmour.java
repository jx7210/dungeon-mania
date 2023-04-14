package dungeonmania.entities.buildables;

import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    public static final double DEFAULT_ATTACK = 1;
    public static final double DEFAULT_DEFENCE = 1;

    private double attack;
    private double defence;

    public MidnightArmour(double armourAttack, double armourDefence) {
        super(null);
        this.attack = armourAttack;
        this.defence = armourDefence;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, attack, defence, 1, 1));

    }
}
