package model.phase;
import model.phase.*;
import model.Duel;


public class Phases{
	private Duel duel;
	private DrawPhase drawPhase;
	private StandByPhase standByPhase;
	private MainPhase1 mainPhase1;
	private BattlePhase battlePhase;
	private MainPhase2 mainPhase2;
	private Phases[] phases = {null, null, null, null, null};
	private String message = null;


	public Phases(Duel duel){
		this.duel = duel;
		this.drawPhase = new drawPhase();
		this.standByPhase = new standByPhase();
		this.mainPhase1 = new mainPhase1();
		this.battlePhase = new battlePhase();
		this.mainPhase2 = new mainPhase2();

		this.phases[0] = this.drawPhase;
		this.phases[1] = this.standByPhase;
		this.phases[2] = this.mainPhase1;
		this.phases[3] = this.battlePhase;
		this.phases[4] = this.mainPhase2;
		
	}


	public void run(){

		for(Phases phase: this.phases){
			//message: "phase:" phase.name() + '\n';
			if(phase.getMessage() != null);//message: phase.getMessage();
			phase.run();
		}
	}

	public getMessage(){
		return this.message;
	}

	public setMessage(String message){
		this.message = message;
	}
}
