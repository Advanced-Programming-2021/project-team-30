package yugioh.model.duel.effect.action;

public class AddLp extends Action{
    final int addedHealth;

    public AddLp(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName, int addedHealth) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
        this.addedHealth = addedHealth;
    }

    @Override
    public void doEffect() {
        duel.decreaseLp(-addedHealth, ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
