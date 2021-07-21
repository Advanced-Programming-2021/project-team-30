package yugioh.model.duel.effect.action;

public class DestroyRecentSpell extends Action{
    public DestroyRecentSpell(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
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
