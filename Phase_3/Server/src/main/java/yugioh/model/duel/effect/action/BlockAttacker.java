package yugioh.model.duel.effect.action;

public class BlockAttacker extends Action {
    public BlockAttacker(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName){
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect(){
        duel.blockAttack();
    }

    @Override
    public void undoEffect(){}

    @Override
    public void callEvent(boolean activationStatus) {}
}
