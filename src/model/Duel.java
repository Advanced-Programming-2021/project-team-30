package model;
import model.board.Board;
import model.phase.Phases;
import model.cards.*;
import model.Player;

import java.util.Stack;
import java.lang.Math;

public class Duel{
	final int[] rotation = {0, 1, 3, 2, 5, 4};
	// rotates the location; input: location as index, output: rotation of location
 
	final Player[] player = new Player[2];
	private Stack<Card> chain = new Stack<>();
	private Board[] board = new Board[2];

	private int currentPlayer, currentPhase, rounds;
	private int[] lp = new int[2];
	private Phases phase;
	private boolean didItSummon;
	private boolean[][] didItChangePosition = new boolean[2][5];
	private boolean[] didItAttack = new boolean[5];
	
	public Duel(Player player1, Player player2, int rounds){
		
		// check
		Deck deck1 = player1.getActiveDeck(), deck2 = player2.getActiveDeck();
		//NOTE: taking clones!!!!!
		/*
		if(deck1 == null){
			// message: player1.getNickName() has no active deck
			return;
		}
		if(deck2 == null){
			// message: player2.getNickname() has no active deck
			return;
		}*/
		//checks player1 for active deck; checks player2 for active deck;
		/* message: username has no active deck
		   message: username's deck is invalid
		*/

		if(rounds != 1 && rounds != 3)return;//message: number of rounds is not supported
		//initialize
		Player[] players = {player1, player2};
		this.phase = new Phases(this);
		for(int i = 0; i < 2; i++){
			this.player[i] = players[i];
			this.board[i] = new Board(this, players[i]);
		}
		this.rounds = rounds;

	}

	public void roundReset(){
		this.chain.clear();
		this.lp[0] = 8000;
		this.lp[1] = 8000;
		this.currentPlayer = 0;
		this.currentPhase = 0;
		this.board[0].reset();
		this.board[1].reset();
		this.phase.reset();
	}

	public void turnReset(){
		currentPhase = 0;
		for(int i = 0; i < 5; i++)
		{
			this.didItAttack[i] = false;
			this.didItSummon = false;
			this.didItChangePosition[0][i] = false;
			this.didItChangePosition[1][i] = false;
		}
	}
	
	public void run(){
		int maxHealth1 = 0, maxHealth2 = 0;
		while(this.rounds > 0){
			//here we design the end of each round
			roundReset();
			startRound();
			maxHealth1 = getMax(maxHealth1, this.lp[0]);
			maxHealth2 = getMax(maxHealth2, this.lp[1]);
			this.rounds -= 1;
		}
	}

	public int getMax(int a, int b){
		return Math.max(a, b);
	}
	
	public void startRound(){
		while(checkPlayers()){
			turnReset();
			turn();
			this.currentPlayer = 1 - this.currentPlayer;
		}
	}

	public boolean checkPlayers(){
		boolean p1 = (lp[0] <= 0 || getNumberOfCards("all_usable", false) == 0);
		boolean p2 = (lp[1] <= 0 || getNumberOfCards("all_usable", true) == 0);

		return !(p1 || p2);
	}

	public boolean askPositionChange(int location, int ground){
		return didItChangePosition[ground][location];
	}

	public int getNumberOfCards(String from, boolean opponent){
		if(opponent)
			return board[1 - currentPlayer].total(from);
		return board[currentPlayer].total(from);
	}

	public int rotate(int location){
		return rotation[location];
	}

	public String listen(){
		//gets input, checks with regex, gives output
		return "SDFSD";
	}
	
	private void turn(){ phase.run(); }

	public void selectCard(int location, String from, boolean enemy){
		if(enemy){
			board[1 - currentPlayer].selectCard(rotate(location), from, true);
			return;
		}
		board[currentPlayer].selectCard(location, from, false);
	}

	public Card getSelectedCard(){
		return board[currentPlayer].getSelectedCard();
	}

	public String getSelectedCardOrigin(){
		return board[currentPlayer].getSelectedCardOrigin();
	}

	public int getSelectedCardLocation(){
		return board[currentPlayer].getSelectedCardLocation();
	}

	public void summon(){

		if(getSelectedCard() == null)
			return;// message: no card is selected yet

		//if(card is not monster or cannot do normal summon)return;// message: you can't summon this card

		if(currentPhase != 2 && currentPhase != 4)
			return;// message: action not allowed in this phase

		if(getNumberOfCards("monsterGround", false) == 5)return; //message: monster card zone is full

		if(this.didItSummon)return;//message:you already summoned/set on this turn
		this.didItSummon = true;
		board[currentPlayer].summonFromHand();
	}

	public void draw(){ board[currentPlayer].draw(); }

	public void flipSummon(){
		if(board[currentPlayer].flipSummon()){
			didItChangePosition[0][board[currentPlayer].getSelectedCardLocation()] = true;
			deselect(false);
		}
	}

	public void ritualSummon(){
		//
	}

	public void removeFromHand(int location){
		board[currentPlayer].removeCard("hand", location);
	}

	public void set(){
		board[currentPlayer].set();
	}

	public void setPosition(String newPosition, String from){
		switch(from){
			case "monsterGround":
				if(board[currentPlayer].setPosition(newPosition)){
					didItChangePosition[0][board[currentPlayer].getSelectedCardLocation()] = true;
					deselect(false);
				}

			case "spellTrapGround":

		}
	}

	public String getPosition(boolean opponent, int location, int ground){
		if(opponent){
			return board[1 - currentPlayer].getPosition(location, ground);
		}
		return board[currentPlayer].getPosition(location, ground);
	}

	public void attack(int defenderLocation){
		if(defenderLocation < 0 || defenderLocation > 4){
			//message: invalid input
			return;
		}

		if(getSelectedCard() == null){
			//message: no card is selected yet
			return;
		}

		if(!getSelectedCardOrigin().equals("monsterGround")){
			board[currentPlayer].setMessage("you can't attack this card");
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			board[currentPlayer].setMessage("this card already attacked");
			return;
		}

		Card enemyCard = board[1 - currentPlayer].getCard("monsterGround", defenderLocation);
		Card myCard = getSelectedCard();

		if(enemyCard == null){
			//message: there is no card to attack at the given location
			return;
		}

		String myPosition = getPosition(false, getSelectedCardLocation(), 0);
		String enemyPosition = getPosition(false , defenderLocation, 0);

		if(!myPosition.equals("OO")){
			//message: you cannot attack with a card that is not on OO position
			return;
		}

		int atk_dmg = myCard.getAttackDamage(), defender_dmg;

		switch(enemyPosition){
			case "OO":
				defender_dmg = enemyCard.getAttackDamage();
				if(defender_dmg < atk_dmg){
					lp[1 - currentPlayer] -= atk_dmg - defender_dmg;
					board[1 - currentPlayer].removeCard("monsterGround", defenderLocation);
					//message: your opponent’s monster is destroyed and your opponent receives <damage> battle damage
				}
				else if(defender_dmg == atk_dmg){
					board[currentPlayer].removeCard("monsterGround", getSelectedCardLocation());
					board[1 - currentPlayer].removeCard("monsterGround", defenderLocation);
					//message: both you and your opponent monster cards are destroyed and no one receives damage
				}
				else{
					board[currentPlayer].removeCard("monsterGround", getSelectedCardLocation());
					lp[currentPlayer] -= defender_dmg - atk_dmg;
					// message: Your monster card is destroyed and you received <damage> battle damage
				}

			default:
				if(enemyPosition.equals("DH")){
					//message: opponent’s monster card was <monster card name> and 
				}

				defender_dmg = myCard.getDefenseDamage();
				if(defender_dmg < atk_dmg){
					board[1 - currentPlayer].removeCard("monsterGround", defenderLocation);
					//message: the defense position monster is destroyed
				}
				else if(defender_dmg == atk_dmg){
					//message: no card is destroyed
				}
				else{
					lp[currentPlayer] -= defender_dmg - atk_dmg;
					//message: no card is destroyed and you received <damage> battle damage
				}

		}
		deselect(false);
		didItAttack[getSelectedCardLocation()] = true;
	}

	public void directAttack(){
		if(getSelectedCard() == null){
			//message: no card is selected yet
			return;
		}

		if(getSelectedCardOrigin().equals("monsterGround")){
			//message: you can't attack with this card
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			//message: you already attacked with this card in this turn
			return;
		}

		if(getNumberOfCards("monsterGround", true) != 0){
			//message: you can't attack directly when there is still monster card in the enemy field! 
			return;
		}

		if(!getPosition(false, getSelectedCardLocation(), 0).equals("OO")){
			//message: you can't attack with a card which is not on attack mode
		}

		lp[1 - currentPlayer] -= getSelectedCard().getAttackDamage();
		deselect(false);
		//message: you opponent receives <damage> battle damage
	}

	public boolean activateSpell(boolean activate){
		if(!checkSelectedCard()){
			//message: no card is selected yet
			return false;
		}

		if(getSelectedCardOrigin() != "handGround"){
			//message: this card has been activated/set before
			return false;
		}

		if(getNumberOfCards("spellTrapGround", false) == 5){
			//message: spell card zone is full
			return false;
		}

		if(activate && board[currentPlayer].activateSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			deselect(false);
		}

		if(!activate && board[currentPlayer].setSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			deselect(false);
		}
		return true;
	}

	public void showGraveYard(){
		board[currentPlayer].show("graveYardGround");
	}

	public void showCard(){
		board[currentPlayer].showCard();
	}

	public int getCurrentPlayer(){
		return currentPlayer;
	}

	public boolean checkSelectedCard(){
		if(getSelectedCard() == null) return false;
		return true;
	}

	public void deselect(boolean msg){
		board[currentPlayer].deselect(msg);
	}

	public void doEffect(Card card){
		//does effects
	}

	public void undoEffect(Card card){
		//undoes effects
	}

	public void getMapDetails(){
		;
		//processes for getting map details
	}
}