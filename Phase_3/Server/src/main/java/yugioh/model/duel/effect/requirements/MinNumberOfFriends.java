package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.Ground;

public class MinNumberOfFriends extends Requirement{
    final Ground ground;
    final int minRequired;

    public MinNumberOfFriends(Ground ground, int minRequired, int ownerPlayer){
        super(ownerPlayer);
        this.ground = ground;
        this.minRequired = minRequired;
    }

    @Override
    public boolean check(){
        return duel.getNumberOfCards(Ground.monsterGround, duel.getCurrentPlayer()) >= minRequired;
    }
}
