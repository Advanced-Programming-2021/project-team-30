package model.effect.action;

public class BlockAttacker extends Action {
    public BlockAttacker(int ownerPlayer){
        super(ownerPlayer);
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
