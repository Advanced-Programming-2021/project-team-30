package model.requirements;

import model.event.OnGettingAttacked;

public class IsRecentAttackedMe extends Requirement{
    final int myLocation;

    public IsRecentAttackedMe(int myLocation){ this.myLocation = myLocation; }

    public boolean check(){ return OnGettingAttacked.getLocation() == myLocation; }
}
