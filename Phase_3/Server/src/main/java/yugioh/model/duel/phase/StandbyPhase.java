package yugioh.model.duel.phase;

import yugioh.model.duel.Command;

public class StandbyPhase extends Phase {

    public StandbyPhase(){
        validCommands = new Command[]{
                Command.nextPhase,
                Command.surrender
        };
    }
}
