package model.effect.action;

public class DecreaseEnemyPlayerLp extends Action{

    final int damage;

    public DecreaseEnemyPlayerLp(int ownerPlayer, boolean canBeUsedOncePerRound, int damage) {
        super(ownerPlayer, canBeUsedOncePerRound);
        this.damage = damage;
    }

    @Override
    public void doEffect() {
        duel.decreaseLp(damage, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
