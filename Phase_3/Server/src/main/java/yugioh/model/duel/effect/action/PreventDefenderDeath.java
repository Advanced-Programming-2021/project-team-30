package yugioh.model.duel.effect.action;

public class PreventDefenderDeath extends Action{
    public PreventDefenderDeath(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        duel.stopMyDeath();
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
