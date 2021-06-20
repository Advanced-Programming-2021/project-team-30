package model.phase;

import model.Command;

public class StandbyPhase extends Phase {

    public StandbyPhase(){
        validCommands = new Command[]{
                Command.nextPhase,
                Command.surrender
        };
    }
}
