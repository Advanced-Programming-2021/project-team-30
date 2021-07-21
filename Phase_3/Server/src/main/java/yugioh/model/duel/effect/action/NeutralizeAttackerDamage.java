package yugioh.model.duel.effect.action;

public class NeutralizeAttackerDamage extends Action {

    public NeutralizeAttackerDamage(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName){
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect(){
        duel.stopDamage();
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus){

    }
}
