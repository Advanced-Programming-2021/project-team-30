package model.phase;

public class StandbyPhase extends Phases{
    @Override
    public void run(){}

    public StandbyPhase(){
        super(null);
        this.name = "standby phase";
    }
}
