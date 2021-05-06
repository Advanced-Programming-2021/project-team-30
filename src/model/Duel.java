package model;
import model.board.Board;
import model.phase.Phases;
import model.cards.*;
import model.Player;

import java.util.ArrayList;


public class Duel{
	private Player[] player = new Player[2];
	private ArrayList<Card> chain = new ArrayList<Card>();
	private Board[] board = new Board[2];

	private int current_player = 0, current_phase = 0, rounds;
	private int[] player_lp = new int[2];
	private Phases phase = new Phases(this);
	private boolean didItSummon;
	private boolean[] didItChangePosition = new boolean[5];
	private boolean[] didItAttack = new boolean[5];
	
	public Duel(Player player1, Player player2, int rounds){
		
		// check
		Deck deck1 = player1.getActiveDeck(), deck2 = player2.getActiveDeck();
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

		for(int i = 0; i < 2; i++){
			player[i] = players[i];
			board[i] = new Board(this, players[i]);
		}
		this.rounds = rounds;
	}

	
	public void run(){
		while(rounds > 0){
			startRound();

			// here we design the next rounds
			rounds -= 1;
		}
	}
	
	public void startRound(){
		player_lp[0] = 8000;
		player_lp[1] = 8000;
		//set boards
		while(check_players()){

			this.turn();
			this.current_player = 1 - this.current_player;
		
		}
	}

	public int getNumberOfCards(String from, boolean opponent){
		if(opponent)
			return board[1 - current_player].total(from);
		return board[current_player].total(from);
	}

	private void selectCard(int location, String from, boolean enemy){
		if(enemy){
			selectCardFromEnemy(location, from);
			return;
		}
		board[current_player].selectCard(location, from, false);
	}

	public void selectCardFromEnemy(int location, String from){
		board[1 - current_player].selectCard(location, from, true);
	}
	
	private void turn(){
		// basic initialization
		this.current_phase = 0;
		this.didItSummon = false;
		for(int i = 0; i < 5; i++){
			didItChangePosition[i] = false;
			didItAttack[i] = false;
		}
		this.phase.run();
		// waits for the input
	}

	private void nextPhase(){

	}

	private void draw(){
		Card drawn = this.deck[this.current_player][0];
		this.deck.remove(0);
		this.hand[this.current_player].add(drawn);
	}

	public Card getSelectedCard(){
		return this.board[this.current_player].getSelectedCard();
	}

	public String getSelectedCardOrigin(){
		return board[current_player].getSelectedCardOrigin();
	}

	public int getSelectedCardLocation(){
		return board[current_player].getSelectedCardLocation();
	}

	public void summon(){

		if(getSelectedCard() == null)
			return;// message: no card is selected yet

		//if(card is not monster or cannot do normal summon)return;// message: you can't summon this card

		if(current_phase != 2 && current_phase != 4)
			return;// message: action not allowed in this phase

		if(getNumberOfCards("monsterGround", false) == 5)return; //message: monster card zone is full

		if(this.didItSummon)return;//message:you already summoned/set on this turn
		this.didItSummon = true;
		this.board[this.current_player].summonFromHand();
	}

	public void flipSummon(){
		if(board[current_player].flipSummon()){
			didItChangePosition[borad[current_player].getSelectedCardLocation()] = true;
			deselect(false);
		}
	}

	public void removeFromHand(int location){
		this.hand.remove(location);
	}

	public void set(){
		board[current_player].set();
	}

	public void setPosition(String newPosition){
		if(board.setPosition(newPosition)){
			didItChangePosition[board[current_player].getSelectedCardLocation] = true;
			deselect(false);
		}
	}

	public String getPosition(boolean opponent, int location, int ground){
		if(opponent){
			return board[1 - current_player].getPosition(location, ground);
		}
		return board[current_player].getPosition(location, ground);
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

		if(getSelectedCardOrigin() != "monsterGround"){
			//message: you can't attack this card
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			//message: this card already attacked
			return;
		}

		Card enemyCard = board[1 - current_player].getCard(defenderLocation);
		Card myCard = getSelectedCard();

		if(enemyCard == null){
			//message: there is no card to attack at the given location
			return;
		}

		String myPosition = getPosition(0, getSelectedCardLocation(), 0);
		String enemyPosition = getPosition(1, defenderLocation, 0);

		if(myPosition != "OO"){
			//message: you cannot attack with a card that is not on OO position
			return;
		}

		int atk_dmg = myCard.getAttackDamage(), defender_dmg;

		switch(enemyPosition){
			case "OO":
				defender_dmg = enemyCard.getAttackDamage();
				if(defender_dmg < atk_dmg){
					lp[1 - current_player] -= atk_dmg - defender_dmg;
					board[1 - current_player].removeCard("monsterGround", defenderLocation);
					//message: your opponent’s monster is destroyed and your opponent receives <damage> battle damage
				}
				else if(defender_dmg == atk_dmg){
					board[current_player].removeCard("monsterGround", getSelectedCardLocation);
					board[1 - current_player].removeCard("monsterGround", defenderLocation);
					//message: both you and your opponent monster cards are destroyed and no one receives damage
				}
				else{
					board[current_player].removeCard("monsterGround", getSelectedCardLocation);
					lp[current_player] -= defender_dmg - atk_dmg;
					// message: Your monster card is destroyed and you received <damage> battle damage
				}

			default:
				if(enemyPosition == "DH"){
					//message: opponent’s monster card was <monster card name> and 
				}

				defender_dmg = myCard.getDefenseDamage();
				if(defender_dmg < atk_dmg){
					board[1 - current_player].removeCard("monsterGround", defenderLocation);
					//message: the defense position monster is destroyed
				}
				else if(defender_dmg == atk_dmg){
					//message: no card is destroyed
				}
				else{
					lp[current_player] -= defender_dmg - atk_dmg;
					//message: no card is destroyed and you received <damage> battle damage
				}

		}
		deselect(false);
		didItAttack[getSelectedCardLocation] = true;
	}

	public void directAttack(){
		if(getSelectedCard() == null){
			//message: no card is selected yet
			return;
		}

		if(getSelectedCardOrigin != "monsterGround"){
			//message: you can't attack with this card
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			//message: you already attacked with this card in this turn
			return;
		}

		if(getNumberOfCards("monsterGround", 1) != 0){
			//message: you can't attack directly when there is still monster card in the enemy field! 
			return;
		}

		if(getPosition(false, getSelectedCardLocation, "monsterGround") != "OO"){
			//message: you can't attack with a card which is not on attack mode
		}

		lp[1 - current_player] -= getSelectedCard.getAttackDamage();
		deselect(false);
		//message: you opponent receives <damage> battale damage
	}

	public void deselect(boolean msg){
		board[current_player].deselect(msg);
	}

	public void doEffect(){
		//does effects
	}

	public void askPositionChange(int location){
		return didItChangePosition[location];
	}

	public boolean check_players(){
		;
	}

	public void getMapDetails(){
		;
		//processes for getting map details
	}

	public void tictactoe(){
		int p1 = player1.tictactoe(), p2 = player2.tictactoe();
	}

	static void duelController(Duel duel){
			;
	}
}