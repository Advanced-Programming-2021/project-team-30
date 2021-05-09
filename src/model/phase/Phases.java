package model.phase;
import model.phase.*;
import model.Duel;
import model.cards.Card;
import model.response.DuelMenuResponse;
import view.Main;


public class Phases{
	private Duel duel;
	private DrawPhase drawPhase = new DrawPhase();
	private StandbyPhase standByPhase = new StandbyPhase();
	private MainPhase1 mainPhase1 = new MainPhase1();
	private BattlePhase battlePhase = new BattlePhase();
	private MainPhase2 mainPhase2 = new MainPhase2();
	private Phases[] phases = new Phases[5];
	private String message = "";
	protected String name;


	public Phases(Duel duel){
		this.duel = duel;
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

	public int getNumberOfCards(String from, boolean opponent){
		return duel.getNumberOfCards(from, opponent);
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
}
