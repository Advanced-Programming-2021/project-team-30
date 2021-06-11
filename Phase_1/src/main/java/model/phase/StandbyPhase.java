package model.phase;

public class StandbyPhase extends Phases{
    @Override
    public void run(){}

    @Override
    public void reset(){}

    public StandbyPhase(){ this.name = "standby phase"; }
}
