package model;
import model.board.Board;
import model.phase.Phases;
import model.cards.*;
import model.Player;


public class Duel{
	private Player[] player = new Player[2];
	private ArrayList<Card>[] deck = new ArrayList<Card>[2];
	private ArrayList<Card>[] hand = new ArrayList<Card>[2];
	private ArrayList<Card>[] graveYard = new ArrayList<Card>[2];
	private ArrayList<Card> chain = new ArrayList<Card>();
	private Board[] board = new Board[2];

	private int current_player = 0;
	private Phases phase = new Phases(this);
	
	public Duel(Player player1, Player player2){
		
		Player[] players = {player1, player2};

		//initialize
		for(int i = 0; i < 2; i++){
			Player myPlayer = players[i];
			this.player[i] = myPlayer;
			this.deck[i] = myPlayer.getDeck();
			this.board[i] = new Board(this, myPlayer);
		}
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

	private void selectCard(int location, String type){
		this.board.selectCard(location, type);
	}
	
	private void turn(){
		this.phase.run();
	}
	

	private void draw(){
		Card drawn = this.deck[this.current_player][0];
		this.deck.remove(0);
		this.hand[this.current_player].add(drawn);
	}

	public Card getSelectedCard(){
		return this.board[this.current_player].getSelectedCard();
	}

	public bool check_players(){
		;
	}

	public void tictactoe(){
		int p1 = player1.tictactoe(), p2 = player2.tictactoe();
	}

	static void duelController(Duel duel){
			;
	}
}