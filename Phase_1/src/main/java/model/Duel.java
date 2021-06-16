package model;

import model.board.Board;
import model.effect.Effect;
import model.effect.action.Action;
import model.phase.Phases;
import model.cards.MonsterCard.*;
import model.cards.*;
import model.response.DuelMenuResponse;
import view.Main;
import model.effect.event.*;

import java.util.ArrayList;
import java.util.Stack;
import java.lang.Math;

enum eventNum{
	OnGettingAttacked,
	OnSummon,
	OnDeath,
	OnEnemyBattlePhase,
	OnSpellActivation,
	OnStandByPhase,
	OnFlip
}

public class Duel{
	final static Event[] triggerEvents = {
			OnGettingAttacked.getInstance(),
			OnSummon.getInstance(),
			OnDeath.getInstance(),
			OnEnemyBattlePhase.getInstance(),
			OnSpellActivation.getInstance(),
			OnStandByPhase.getInstance(),
			OnFlip.getInstance()
	};
	final int[] rotation = {0, 1, 3, 2, 5, 4};
	// rotates the location; input: location as index, output: rotation of location

	final Player[] player = new Player[2];
	public static int attackerLocation, defenderLocation;
	private final Stack<Card> chain = new Stack<>();
	private Stack<Effect> effectStack = new Stack<>();
	final Board[] board = new Board[2];

	private int currentPlayer, currentPhase, rounds;
	private int[] lp = new int[2];
	private Phases phase;
	private boolean didItSummon, isAttackBlocked;
	private boolean[][] didItChangePosition = new boolean[2][5];
	private boolean[] didItAttack = new boolean[5];

	public static ArrayList<Duel> duels = new ArrayList<>();

	public static Duel getRecentDuel(){return Duel.duels.get(duels.size() - 1);}
	
	public Duel(Player player1, Player player2, int rounds){
		
		// check
		Deck deck1 = player1.getActiveDeck(), deck2 = player2.getActiveDeck();
		//NOTE: taking clones!!!!!
		//IMPORTANT: username and nickname are 2 different terms
		if(deck1 == null){
			Main.outputToUser(DuelMenuResponse.hasNoActiveDeck(player1.getNickname()));
			return;
		}
		if(deck2 == null){
			Main.outputToUser(DuelMenuResponse.hasNoActiveDeck(player2.getNickname()));
			return;
		}
		if(!deck1.isValid()){
			Main.outputToUser(DuelMenuResponse.hasInvalidDeck(player1.getUsername()));
			return;
		}
		if(!deck2.isValid()){
			Main.outputToUser(DuelMenuResponse.hasInvalidDeck(player2.getUsername()));
			return;
		}

		if(rounds != 1 && rounds != 3){
			Main.outputToUser(DuelMenuResponse.invalidRound);
			return;
		}
		//initialize
		Player[] players = {player1, player2};
		this.phase = new Phases();
		for(int i = 0; i < 2; i++){
			this.player[i] = players[i];
			this.board[i] = new Board(this, players[i]);
		}
		this.rounds = rounds;
		Duel.duels.add(this);
	}

	public void roundReset(){
		this.chain.clear();
		this.lp[0] = 8000;
		this.lp[1] = 8000;
		this.currentPlayer = 0;
		this.currentPhase = 0;
		this.isAttackBlocked = false;
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
			this.rounds--;
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
		boolean p1 = (lp[0] <= 0 || getNumberOfCards(Ground.allUsable, getCurrentPlayer()) == 0);
		boolean p2 = (lp[1] <= 0 || getNumberOfCards(Ground.allUsable, 1 - getCurrentPlayer()) == 0);

		return !(p1 || p2);
	}

	public boolean askPositionChange(int location, int ground){
		return didItChangePosition[ground][location];
	}

	public int getNumberOfCards(Ground from, int player){
		return board[player].total(from);
	}

	public int rotate(int location){
		return rotation[location];
	}

	public String listen(){
		//gets input, checks, calls methods
		return "SDFSD";
	}
	
	private void turn(){
		phase.run();
	}

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

		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		//if(card is not monster or cannot do normal summon)return;// message: you can't summon this card

		if(currentPhase != 2 && currentPhase != 4){
			Main.outputToUser(DuelMenuResponse.actionNotAllowedInPhase);
			return;
		}

		if(getNumberOfCards(Ground.monsterGround, currentPlayer) == 5){
			Main.outputToUser(DuelMenuResponse.monsterZoneFull);
			return;	
		}

		if(this.didItSummon) {
			Main.outputToUser(DuelMenuResponse.alreadySummoned);
			return;
		}

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
		if(!board[currentPlayer].isRitualSummonPossible())return;
		//waits for inputs
	}

	public void removeFromHand(int location){
		board[currentPlayer].removeCard("hand", location);
	}

	public void set(){
		board[currentPlayer].set();
	}

	public void setPosition(String newPosition){
		if(board[currentPlayer].setPosition(newPosition)){ didItChangePosition[0][getSelectedCardLocation()] = true; }
	}

	public String getPosition(int player, int location, Ground ground){
		return board[player].getPosition(location, ground);
	}

	public void blockAttack(){ this.isAttackBlocked = true; }

	public void attack(int defenderLocation){
		if(defenderLocation < 0 || defenderLocation > 4){
			Main.outputToUser(DuelMenuResponse.invalidInput);
			return;
		}

		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(!getSelectedCardOrigin().equals("monsterGround")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			Main.outputToUser(DuelMenuResponse.alreadyAttacked);
			return;
		}

		MonsterCard enemyCard = (MonsterCard)board[1 - currentPlayer].getCard(Ground.monsterGround, defenderLocation);
		MonsterCard myCard = (MonsterCard)(getSelectedCard());
		attackerLocation = getSelectedCardLocation();
		Duel.defenderLocation = defenderLocation;

		if(enemyCard == null){
			Main.outputToUser(DuelMenuResponse.noCardToAttack);
			return;
		}

		String myPosition = getPosition(currentPlayer, getSelectedCardLocation(), Ground.monsterGround);
		String enemyPosition = getPosition(1 - currentPlayer , defenderLocation, Ground.monsterGround);

		if(!myPosition.equals("OO")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		int atk_dmg = myCard.getAttackDamage(), defender_dmg;

		OnGettingAttacked.getInstance().isCalled = true;
		ArrayList<Integer> locations = getTriggeredCardLocations(1 - currentPlayer, Ground.monsterGround);
		OnGettingAttacked.getInstance().isCalled = false;
		if(!locations.isEmpty() && chain.isEmpty()){
			Main.outputToUser(DuelMenuResponse.askForEffectActivation);
			String ask = listen();
			if(ask.equals("yes")){
				runChain(OnGettingAttacked.getInstance());
				return;
			}
		}

		if(isAttackBlocked){
			isAttackBlocked = false;
			return;
		}

		else if(enemyPosition.equals("OO")) {
			defender_dmg = enemyCard.getAttackDamage();
			if (defender_dmg < atk_dmg) {
				lp[1 - currentPlayer] -= atk_dmg - defender_dmg;
				board[1 - currentPlayer].removeCard("monsterGround", defenderLocation);
				Main.outputToUser(DuelMenuResponse.opponentMonsterDestroyed(atk_dmg - defender_dmg));
			} else if (defender_dmg == atk_dmg) {
				board[currentPlayer].removeCard("monsterGround", getSelectedCardLocation());
				board[1 - currentPlayer].removeCard("monsterGround", defenderLocation);
				Main.outputToUser(DuelMenuResponse.bothMonsterDestroyed);
			} else {
				board[currentPlayer].removeCard("monsterGround", getSelectedCardLocation());
				lp[currentPlayer] -= defender_dmg - atk_dmg;
				Main.outputToUser(DuelMenuResponse.yourMonsterDestroyed(defender_dmg - atk_dmg));
			}
		} else{
			if(enemyPosition.equals("DH")){
				Main.outputToUser(String.format("opponent’s monster card was <%s> and ", enemyCard.getName()));
				// we add this string to the first of every coming output, if the enemy card is hidden (DH mode)
			}

			defender_dmg = myCard.getDefenseDamage();
			if(defender_dmg < atk_dmg){
				board[1 - currentPlayer].removeCard("monsterGround", defenderLocation);
				Main.outputToUser(DuelMenuResponse.defenseDestroyed);
			} else if(defender_dmg == atk_dmg){
				Main.outputToUser(DuelMenuResponse.noCardDestroyed);
			} else{
				lp[currentPlayer] -= defender_dmg - atk_dmg;
				Main.outputToUser(DuelMenuResponse.noCardDestroyedWithDamage(defender_dmg - atk_dmg));
			}
		}
		deselect(false);
		didItAttack[getSelectedCardLocation()] = true;
	}

	private void runChain(Event event) {
		event.isCalled = true;
		int askedPlayer = 1 - currentPlayer;
		chain.add(getSelectedCard());
		Effect effect = getSelectedCard().getEffect();
		effectStack.add(effect);
		effect.callEvent(true);
		deselect(false);
		while(true){
			ArrayList<Integer> triggeredMonsters = getTriggeredCardLocations(askedPlayer, Ground.monsterGround);
			ArrayList<Integer> triggeredSpells = getTriggeredCardLocations(askedPlayer, Ground.spellTrapGround);
			if(triggeredMonsters.isEmpty() && triggeredSpells.isEmpty()){
				if(chain.size() > 1)
					Main.outputToUser("no more effect can be added to chain");
				effect.callEvent(false);
				break;
			}
			Main.outputToUser(DuelMenuResponse.askForEffectActivation);
			String ask = listen();
			if(!ask.equals("yes"))
				break;
			int location = -1;
			Ground ground = Ground.fieldGround;
			while(location == -1){
				Main.outputToUser("which ground?");
				ground = Ground.valueOf(listen());
				Main.outputToUser("location:");
				location = Integer.parseInt("location:");
				if(ground == Ground.monsterGround){
					for(int i: triggeredMonsters)
						if(location == i)
							break;
				} else if(ground == Ground.spellTrapGround){
					for(int i: triggeredSpells)
						if(location == i)
							break;
				} else {
					Main.outputToUser("the selected card doesn't have any effect to run");
					location = -1;
				}
			}
			Card card = getCard(ground, location, askedPlayer);
			effect.callEvent(false);
			effect = card.getEffect();
			chain.add(card);
			effectStack.add(effect);
			effect.callEvent(true);
			Main.outputToUser("added the card to chain\nnow it's player " + (1 - askedPlayer) + "'s turn to add to chain");
			askedPlayer = 1 - askedPlayer;
		}

		while(!chain.isEmpty()){
			Card card = chain.pop();
			effectStack.pop().doEffect();
		}
	}

	public void directAttack(){
		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(!getSelectedCardOrigin().equals("monsterGround")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			Main.outputToUser(DuelMenuResponse.alreadyAttacked);
			return;
		}

		if(getNumberOfCards(Ground.monsterGround, 1 - currentPlayer) != 0){
			//message: you can't attack directly when there is still monster card in the enemy field!
			return;
		}

		if(!getPosition(currentPlayer, getSelectedCardLocation(), Ground.monsterGround).equals("OO")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		MonsterCard attacker = (MonsterCard) getSelectedCard();
		lp[1 - currentPlayer] -= attacker.getAttackDamage();
		deselect(false);
		Main.outputToUser(DuelMenuResponse.opponentReceiveDamage(attacker.getAttackDamage()));
	}

	public void activateSpell(boolean activate){
		if(!checkSelectedCard()){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(!getSelectedCardOrigin().equals("handGround")){
			Main.outputToUser(DuelMenuResponse.cantSummon);
			return;
		}

		if(getNumberOfCards(Ground.spellTrapGround, currentPlayer) == 5){
			Main.outputToUser(DuelMenuResponse.spellZoneFull);
			return;
		}

		if(activate && board[currentPlayer].activateSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			Main.outputToUser(DuelMenuResponse.spellActivated);
			runChain(OnSpellActivation.getInstance());
		}

		if(!activate && board[currentPlayer].setSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			Main.outputToUser(DuelMenuResponse.spellSet);
			deselect(false);
		}
	}

	public void showGraveYard(){
		board[currentPlayer].showGraveYard();
	}

	public void showCard(){
		board[currentPlayer].showCard();
	}

	public int getCurrentPlayer(){
		return currentPlayer;
	}

	public Card getCard(Ground from, int location, int player){
		return board[player].getCard(from, location);
	}

	public boolean checkSelectedCard(){
		return getSelectedCard() != null;
	}

	public void deselect(boolean msg){
		board[currentPlayer].deselect(msg);
	}

	public boolean doesCardWithNameExist(Ground from, int player, String name){
		return board[player].doesCardWithNameExist(from, name);
	}

	public void addAttackDamageToGround(int player, int damage){
		for(int i = 0; i < 5; i++)board[player].addAttackDamage(i, damage);
	}

	public void addAttackDamage(int location, int player, int damage){
		board[player].addAttackDamage(location, damage);
	}

	public ArrayList<Integer> getTriggeredCardLocations(int player, Ground ground){
		ArrayList<Integer> answer = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			Card card;
			if(ground == Ground.spellTrapGround)
				card = board[player].getCard(Ground.spellTrapGround, i);
			else
				card = (MonsterCard) board[player].getCard(Ground.monsterGround, i);
			if(card.checkEffects())
				answer.add(i);
		}
		return answer;
	}

	public void killCard(int location, Ground ground, int player){
		board[player].killCard(location, ground);
	}

	public void stopDamage(){

	}
}