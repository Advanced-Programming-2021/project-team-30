package model.effect;

public class BlockAttacker extends Effect{
    public BlockAttacker(int ownerPlayer){
        super(ownerPlayer);
    }

    @Override
    public void doEffect(){
        duel.blockAttack();
    }

    @Override
    public void undoEffect(){}
}
