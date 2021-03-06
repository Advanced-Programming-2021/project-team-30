package model.effect.requirements;

import model.effect.event.OnFlip;

public class IsRecentFlippedMe extends Requirement{
    final int myLocation;

    public IsRecentFlippedMe(int myLocation, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation; }

    public boolean check(){ return myLocation == OnFlip.getLocation(); }
}
