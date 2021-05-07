package model;
import model.board.Board;
import model.phase.Phases;
import model.cards.*;
import model.response.DuelMenuResponse;
import view.Main;

import java.util.ArrayList;


public class Duel{
	final int[] rotation = {0, 1, 3, 2, 5, 4};
	// rotates the location; input: location as index, output: rotation of location
 
	private Player[] player = new Player[2];
	private ArrayList<Card> chain = new ArrayList<Card>();
	private Board[] board = new Board[2];

	private int current_player = 0, current_phase = 0, rounds;
	private int[] player_lp = new int[2];
	private Phases phase = new Phases(this);
	private boolean didItSummon;
	private boolean[][] didItChangePosition = new boolean[2][5];
	private boolean[] didItAttack = new boolean[5];
	
	public Duel(Player player1, Player player2, int rounds){
		
		// check
		Deck deck1 = player1.getActiveDeck(), deck2 = player2.getActiveDeck();

		if(deck1 == null){
			Main.outputToUser(DuelMenuResponse.hasNoActiveDeck(player1.getUsername()));
			return;
		} else if(deck2 == null){
			Main.outputToUser(DuelMenuResponse.hasNoActiveDeck(player2.getUsername()));
			return;
		} else if (!deck1.isValid()){
			Main.outputToUser(DuelMenuResponse.hasInvalidDeck(player1.getUsername()));
			return;
		} else if (!deck2.isValid()){
			Main.outputToUser(DuelMenuResponse.hasInvalidDeck(player2.getUsername()));
			return;
		}
		//checks player1 for active deck; checks player2 for active deck;
		if(rounds != 1 && rounds != 3) {
			Main.outputToUser(DuelMenuResponse.invalidRound);
			return;
		}
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

	public void selectCard(int location, String from, boolean enemy){
		if(enemy){
			board[1 - current_player].selectCard(rotate(location), from);
			return;
		}
		board[current_player].selectCard(location, from);
	}

	public int rotate(int location){
		return rotation[location];
	}

	public void selectCardFromEnemy(int location, String from){
		board[1 - current_player].selectCard(location, from, true);
	}
	
	private void turn(){
		// basic initialization
		this.current_phase = 0;
		this.didItSummon = false;
		for(int i = 0; i < 5; i++){
			didItChangePosition[0][i] = false;
			didItChangePosition[1][i] = false;
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

		if(getSelectedCard() == null) {
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		//if(card is not monster or cannot do normal summon)return;// message: you can't summon this card

		if(current_phase != 2 && current_phase != 4) {
			Main.outputToUser(DuelMenuResponse.actionNotAllowedInPhase);
			return;
		}

		if(getNumberOfCards("monsterGround", false) == 5){
			Main.outputToUser(DuelMenuResponse.monsterZoneFull);
			return;
		}

		if(this.didItSummon){
			Main.outputToUser(DuelMenuResponse.alreadySummoned);
			return;
		}
		this.didItSummon = true;
		this.board[this.current_player].summonFromHand();
	}

	public void flipSummon(){
		if(board[current_player].flipSummon()){
			didItChangePosition[0][board[current_player].getSelectedCardLocation()] = true;
			deselect(false);
		}
	}

	public void ritualSummon(){

	}

	public void removeFromHand(int location){
		board.removeCard("hand", location);
	}

	public void set(){
		board[current_player].set();
	}

	public void setPosition(String newPosition, String from){
		switch(from){
			case "monsterGround":
				if(board.setPosition(newPosition)){
					didItChangePosition[0][board[current_player].getSelectedCardLocation()] = true;
					deselect(false);
				}

			case "spellTrapGround":

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
			Main.outputToUser(DuelMenuResponse.invalidSelection);
			return;
		}

		if (checkGetSelectedCardErrors()) return;

		Card enemyCard = board[1 - current_player].getCard(defenderLocation);
		Card myCard = getSelectedCard();

		if(enemyCard == null){
			Main.outputToUser(DuelMenuResponse.noCardToAttack);
			return;
		}

		String myPosition = getPosition(0, getSelectedCardLocation(), 0);
		String enemyPosition = getPosition(1, defenderLocation, 0);

		if(!myPosition.equals("OO")){
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
					Main.outputToUser(DuelMenuResponse.opponentMonsterDestroyed(atk_dmg - defender_dmg));
				}
				else if(defender_dmg == atk_dmg){
					board[current_player].removeCard("monsterGround", getSelectedCardLocation());
					board[1 - current_player].removeCard("monsterGround", defenderLocation);
					Main.outputToUser(DuelMenuResponse.bothMonsterDestroyed);
				}
				else{
					board[current_player].removeCard("monsterGround", getSelectedCardLocation());
					lp[current_player] -= defender_dmg - atk_dmg;
					Main.outputToUser(DuelMenuResponse.yourMonsterDestroyed(defender_dmg - atk_dmg));
				}

			default:
				if(enemyPosition.equals("DH")){
					Main.outputToUser(DuelMenuResponse.noCardDestroyedOpponentCard(enemyCard.getName()));
				}

				defender_dmg = myCard.getDefenseDamage();
				if(defender_dmg < atk_dmg){
					board[1 - current_player].removeCard("monsterGround", defenderLocation);
					Main.outputToUser(DuelMenuResponse.defenseDestroyed);
				}
				else if(defender_dmg == atk_dmg){
					Main.outputToUser(DuelMenuResponse.noCardDestroyed);
				}
				else{
					lp[current_player] -= defender_dmg - atk_dmg;
					Main.outputToUser(DuelMenuResponse.noCardDestroyedWithDamage(defender_dmg - atk_dmg));
				}

		}
		deselect(false);
		didItAttack[getSelectedCardLocation()] = true;
	}

	public void directAttack(){
		if (checkGetSelectedCardErrors()) return;

		if(getNumberOfCards("monsterGround", 1) != 0){

			//message: you can't attack directly when there is still monster card in the enemy field! 
			return;
		}

		if(!getPosition(false, getSelectedCardLocation(), "monsterGround").equals("OO")){

			//message: you can't attack with a card which is not on attack mode
		}

		lp[1 - current_player] -= getSelectedCard().getAttackDamage();
		deselect(false);
		Main.outputToUser(DuelMenuResponse.opponentReceiveDamage(getSelectedCard().getAttackDamage()));
	}

	public boolean checkGetSelectedCardErrors() {
		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return true;
		}

		if(!getSelectedCardOrigin().equals("monsterGround")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return true;
		}

		if(didItAttack[getSelectedCardLocation()]){
			Main.outputToUser(DuelMenuResponse.alreadyAttacked);
			return true;
		}
		return false;
	}

	public boolean activateSpell(boolean activate){
		if(!checkSelectedCard()){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return false;
		}

		if(!getSelectedCardOrigin().equals("handGround")){
			Main.outputToUser(DuelMenuResponse.alreadyActivated);
			return false;
		}

		if(getNumberOfCards("spellTrapGround", false) == 5){
			Main.outputToUser(DuelMenuResponse.spellZoneFull);
			return false;
		}

		if(activate && board[current_player].activateSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			deselect(false);
		}

		if(!activate && board[current_player].setSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			deselect(false);
		}
	}

	public void showGraveYard(){
		board[current_player].show("graveYardGround");
	}

	public void showCard(){
		board[current_player].showCard();
	}

	public int getCurrentPlayer(){
		return current_player;
	}

	public boolean checkSelectedCard(){
		return getSelectedCard() != null;
	}

	public void deselect(boolean msg){
		board[current_player].deselect(msg);
	}

	public void doEffect(){
		//does effects
	}

	public void undoEffect(){
		//undoes effects
	}

	public boolean[] askPositionChange(int location){
		return didItChangePosition[location];
	}

	public boolean check_players(){
		;
	}

	public void getMapDetails(){
		;
		//processes for getting map details
	}

	public void ticTacToe(){
		int p1 = player[0].tictactoe(), p2 = player[1].tictactoe();
	}

	static void duelController(Duel duel){
			;
	}
}