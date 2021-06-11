package model.phase;

import model.phase.*;
import model.Duel;
import model.cards.Card;
import model.response.DuelMenuResponse;
import view.Main;
import model.Ground;


public class Phases{
	final static Duel duel = Duel.getRecentDuel();
	private DrawPhase drawPhase = new DrawPhase();
	private StandbyPhase standByPhase = new StandbyPhase();
	private MainPhase1 mainPhase1 = new MainPhase1();
	private BattlePhase battlePhase = new BattlePhase();
	private MainPhase2 mainPhase2 = new MainPhase2();
	private Phases[] phases = new Phases[5];
	private String message = "";
	protected String name;


	public Phases(){
		this.phases[0] = this.drawPhase;
		this.phases[1] = this.standByPhase;
		this.phases[2] = this.mainPhase1;
		this.phases[3] = this.battlePhase;
		this.phases[4] = this.mainPhase2;
	}


	public void run(){
		for(Phases phase : phases){
			phase.run();
			Main.outputToUser(DuelMenuResponse.endOfPhase(phase.getName()));
		}
	}

	public String getName(){
		return this.name;
	}

	public String getMessage(){ return this.message; }

	public void setMessage(String message){ this.message = message; }

	public int getNumberOfCards(Ground from, int player){
		return duel.getNumberOfCards(from, player);
	}

	public void draw(){
		duel.draw();
	}

	public Card getSelectedCard(){
		return duel.getSelectedCard();
	}

	public String listen(){
		return duel.listen();
	}

	public void reset(){ this.message = ""; }
}
