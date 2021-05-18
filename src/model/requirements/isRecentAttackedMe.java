package model.requirements;

import model.event.OnGettingAttacked;

public class isRecentAttackedMe extends Requirement{
    final int myLocation;

    public isRecentAttackedMe(int myLocation){ this.myLocation = myLocation; }

    public boolean check(){ return OnGettingAttacked.getLocation() == myLocation; }
}
