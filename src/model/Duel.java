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
		//checks player1 for active deck; checks player2 for active deck;
		/* message: username has no active deck
		   message: username's deck is invalid
		*/
		if(rounds != 1 && rounds != 3)return;//message: number of rounds is not supported
		//initialize
		Player[] players = {player1, player2};

		for(int i = 0; i < 2; i++){
			Player myPlayer = players[i];
			player[i] = myPlayer;
			board[i] = new Board(this, myPlayer);
		}
		this.rounds = rounds;
	}

	
	public void run(){
		this.round();
		
		// here we design the next rounds
	}
	
	public void round(){
		first_player_health = 8000;
		second_player_health = 8000;
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
		this.didItSummon = False;
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
		if(borad[current_player].flipSummon()){
			didItChangePosition[borad[current_player].getSelectedCardLocation()] = true;
			borad[current_player].deselect(false);
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
			board[current_player].deselect(false);
		}
	}

	public void getPosition(int opponent, int location, int ground){
		board[opponent].getPosition(location, ground);
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

					//message: your opponent’s monster is destroyed and your opponent receives <damage> battle damage
				}
				else if(defender_dmg == atk_dmg){

				}
		}
		board[current_player].deselect(false);
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