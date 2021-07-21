package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.Ground;
import yugioh.model.cards.Type;
import java.util.ArrayList;

// designed for equip cards

public class CheckTargets extends Requirement{
    final ArrayList<Type> targets;
    private int targetLocation;

    public CheckTargets(ArrayList<Type> targets, int ownerPlayer){
        super(ownerPlayer);
        this.targets = targets; }

    public void setTargetLocation(int targetLocation){ this.targetLocation = targetLocation; }

    @Override
    public boolean check() {
//        Type type = duel.getCard(Ground.monsterGround, targetLocation, duel.getCurrentPlayer()).getCardType();
//        for(Type target : targets)if(type == target)return true;
        return false;
    }
}
