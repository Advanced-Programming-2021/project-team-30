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


	public Phases(Duel duel){
		this.duel = duel;
		this.drawPhase = new drawPhase();
		this.standByPhase = new standByPhase();
		this.mainPhase1 = new mainPhase1();
		this.battlePhase = new battlePhase();
		this.mainPhase2 = new mainPhase2();
	}


	public void run(){

		drawPhase.run();
		standByPhase.run();
		mainPhase1.run();
		battlePhase.run();
		mainPhase2.run();
	}
}
