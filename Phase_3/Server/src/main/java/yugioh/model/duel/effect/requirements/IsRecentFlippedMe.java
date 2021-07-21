package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.effect.event.OnFlip;

public class IsRecentFlippedMe extends Requirement{
    final int myLocation;

    public IsRecentFlippedMe(int myLocation, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation; }

    public boolean check(){ return myLocation == OnFlip.getLocation(); }
}
