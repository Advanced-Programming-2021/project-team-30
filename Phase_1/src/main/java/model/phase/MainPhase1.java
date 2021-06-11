package model.phase;
import model.cards.Card;
import model.Duel;

public class MainPhase1 extends Phases{
	@Override
	public void run() {
		String order = listen();
		if(order.equals("select")){

		}
	}

	@Override
	public String listen(){
		return super.listen();
	}

	public MainPhase1(){ this.name = "main phase 1"; }
}
