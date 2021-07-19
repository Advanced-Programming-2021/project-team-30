package yugioh.model.duel.phase;

import yugioh.model.duel.Command;


public class DrawPhase extends Phase {
	public DrawPhase(){
		validCommands = new Command[]{
				Command.nextPhase,
				Command.surrender
		};
	}
}
