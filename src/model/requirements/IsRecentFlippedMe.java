package model.requirements;

import model.event.OnFlip;

public class IsRecentFlippedMe extends Requirement{
    final int myLocation;

    public IsRecentFlippedMe(int myLocation){ this.myLocation = myLocation; }

    public boolean check(){ return myLocation == OnFlip.getLocation(); }
}
