package model.phase;

import model.Command;


public class DrawPhase extends Phase {
	public DrawPhase(){
		validCommands = new Command[]{
				Command.nextPhase,
				Command.surrender
		};
	}
}
