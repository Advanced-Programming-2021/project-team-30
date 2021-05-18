package model.requirements;

import model.event.OnDeath;

public class IsRecentDeadMe extends Requirement{
    final int myLocation;

    public IsRecentDeadMe(int myLocation){ this.myLocation = myLocation; }

    public boolean check(){ return myLocation == OnDeath.getLocation(); }
}
