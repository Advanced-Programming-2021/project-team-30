package model.effect.action;

public class BlockEnemyDraw extends Action{
    public BlockEnemyDraw(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
    }

    @Override
    public void doEffect() {
        duel.blockDraw(1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
