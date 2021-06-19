package model.effect.action;

public class DestroyRecentSpell extends Action{
    public DestroyRecentSpell(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        duel.destroyRecentSpell();
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
