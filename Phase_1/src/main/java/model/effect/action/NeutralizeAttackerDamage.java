package model.effect.action;

public class NeutralizeAttackerDamage extends Action {

    public NeutralizeAttackerDamage(int ownerPlayer, boolean canBeUsedOncePerRound){
        super(ownerPlayer, canBeUsedOncePerRound);
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
