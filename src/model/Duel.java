package model;
import model.board.Board;
import model.phase.Phases;
import model.Card;
import model.Player;


public class Duel{
	private Player[] player = new Player[2];
	private ArrayList<Card>[] deck = new ArrayList<Card>[2];
	private ArrayList<Card>[] hand = new ArrayList<Card>[2];
	private ArrayList<Card>[] graveYard = new ArrayList<Card>[2];
	private ArrayList<Card> chain = new ArrayList<Card>();
	private Board[] board = new Board[2];

	private int current_player = 0;
	private Phases[] phase = {new Phases(player[1]), new Phases(player[2])};
	
	public Duel(Player player1, Player player2){
		
		Player[] players = {player1, player2};

		//initialize
		for(int i = 0; i < 2; i++){
			Player myPlayer = players[i];
			this.player[i] = myPlayer;
			this.deck[i] = myPlayer.getDeck();
			this.board[i] = new Board(myPlayer);
		}
	}

	
	public void run(){
		this.round();
		
		// here we design the next rounds
	}

	
	private void round(){

		while(check_players()){

			turn(player[this.current_player]);
			this.current_player = 1 - this.current_player;
		
		}
	}

	
	private void turn(Player player){
		Phases myPhase = phase[this.current_player];
		myPhase.run();
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