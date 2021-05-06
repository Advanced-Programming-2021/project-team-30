package model;
import model.board.Board;
import model.phase.Phases;
import model.cards.*;
import model.Player;

import java.util.ArrayList;


public class Duel{
	private Player[] player = new Player[2];
	private ArrayList<Card>[] deck = new ArrayList<Card>[2];
	private ArrayList<Card>[] hand = new ArrayList<Card>[2];
	private ArrayList<Card>[] graveYard = new ArrayList<Card>[2];
	private ArrayList<Card> chain = new ArrayList<Card>();
	private Board[] board = new Board[2];

	private int current_player = 0, current_phase = 0, rounds;
	private Phases phase = new Phases(this);
	private boolean didItSummon;
	
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
			this.player[i] = myPlayer;
			this.deck[i] = myPlayer.getActiveDeck().getAllCards();
			this.board[i] = new Board(this, myPlayer);
		}
		this.rounds = rounds;
	}

	
	public void run(){
		this.round();
		
		// here we design the next rounds
	}

	public int numberOfCardsInHand(){
		return this.hand[this.current_player].size();
	}

	public int numberOfCardInDeck(){
		return this.hand[this.current_player].size();
	}
	
	private void round(){

		while(check_players()){

			this.turn();
			this.current_player = 1 - this.current_player;
		
		}
	}

	private void selectCard(int location, String from, boolean enemy){
		if(enemy){
			this.selectCardFromEnemy(location, from);
			return;
		}
		this.board[this.current_player].selectCard(location, from, false);
	}

	public void selectCardFromEnemy(int location, String from){
		this.board[1 - this.current_player].selectCard(location, from, true);
	}
	
	private void turn(){
		// basic initialization
		this.current_phase = 0;
		this.didItSummon = False;
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

	public void summon(){

		if(this.getSelectedCard() == null)
			return;// message: no card is selected yet

		//if(card is not monster or cannot do normal summon)return;// message: you can't summon this card

		if(this.current_phase != 2 && this.current_phase != 4)
			return;// message: action not allowed in this phase

		if(this.monstersInField() == 5)return; //message: monster card zone is full

		if(this.didItSummon)return;//message:you already summoned/set on this turn
		this.didItSummon = true;
		this.board[this.current_player].summonFromHand();
	}

	public void removeFromHand(int location){
		this.hand.remove(location);
	}

	public int monstersInField(){
		return this.board.monstersInField();
	}

	public void doEffect(){
		//does effects
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