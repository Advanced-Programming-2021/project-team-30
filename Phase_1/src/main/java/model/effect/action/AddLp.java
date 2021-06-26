package model.effect.action;

public class AddLp extends Action{
    final int addedHealth;

    public AddLp(int ownerPlayer, boolean canBeUsedOncePerRound, int addedHealth) {
        super(ownerPlayer, canBeUsedOncePerRound);
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
