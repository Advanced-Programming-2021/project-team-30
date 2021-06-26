package model.phase;

import model.Command;
import model.Duel;


public class Phase {
	final static Phase[] phases = new Phase[6];
	protected String name;
	protected Command[] validCommands;

	public static void createPhases(){
		DrawPhase drawPhase = new DrawPhase();
		phases[0] = drawPhase;
		StandbyPhase standByPhase = new StandbyPhase();
		phases[1] = standByPhase;
		MainPhase1 mainPhase1 = new MainPhase1();
		phases[2] = mainPhase1;
		BattlePhase battlePhase = new BattlePhase();
		phases[3] = battlePhase;
		MainPhase2 mainPhase2 = new MainPhase2();
		phases[4] = mainPhase2;
	}

	public static boolean checkPhaseValidity(int currentPhase, Command command){
		if(currentPhase == 5)
			return false;
		return phases[currentPhase].checkPhaseCommand(command);
	}

	public boolean checkPhaseCommand(Command command){
		for(Command validCommand: validCommands)
			if(command == validCommand)
				return true;
		return false;
	}
}
