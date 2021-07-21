package yugioh.model.duel;

import com.google.gson.Gson;
import yugioh.controller.RegisterAndLoginController;
import yugioh.model.Player;
import yugioh.model.duel.board.Board;
import yugioh.model.duel.effect.Effect;
import yugioh.model.duel.phase.Phase;
import yugioh.model.cards.*;
import yugioh.model.cards.MonsterCard.*;
import yugioh.model.duel.response.Response;
import yugioh.model.duel.response.DuelMessageTexts;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.Math;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Duel extends Thread{
    final static String[] phaseNames = {
            "<draw phase>",
            "<standby phase>",
            "<main phase 1>",
            "<battle phase>",
            "<main phase 2>",
            "<end phase>"
    };
    final static String[] commands = {
            "p-defense-(\\d+)",
            "p-attack-(\\d+)",
            "attack-(\\d+)-(\\d+)",
            "attack-(\\d+)-d",
            "summon-(\\d+)",
            "flip-summon-(\\d+)",
            "set-(\\d+)",
            "phase-next",
            "surrender"
    };
    final int[] rotation = {0, 1, 2, 3, 4};
    // rotates the location; input: location as index, output: rotation of location

    final Player[] player = new Player[2];
    public static int attackerLocation, defenderLocation;
    private final Stack<Card> chain = new Stack<>();
    private final Stack<Integer> locations = new Stack<>();
    private final Stack<Effect> effectStack = new Stack<>();
    private final Response response;
    final Board[] board = new Board[2];
    private int currentPlayer, currentPhase, rounds, p1win, p2win;
    private final int[] lp = new int[2];
    private boolean isTurnFinished, didItSummon, isAttackBlocked, isDamageStopped, preventDeath;
    private final boolean[][] didItChangePosition = new boolean[2][5];
    private final boolean[] didItAttack = new boolean[5], isScannerSet = new boolean[2], didSurrender = new boolean[2], isDrawBlocked = new boolean[2];
    public static ArrayList<Duel> duels = new ArrayList<>();
    public static MonsterCard[] scanner;

    public static Duel getRecentDuel(){return Duel.duels.get(duels.size() - 1);}

    public Duel(Player[] player, int rounds){
        //NOTE: taking clones!!!!!
        //initialize
        String token1 = player[0].getToken(), token2 = player[1].getToken();
        response = new Response(new Socket[]{
                RegisterAndLoginController.socketHashMap.get(token1),
                RegisterAndLoginController.socketHashMap.get(token2)
        });
        for(int i = 0; i < 2; i++){
            this.player[i] = player[i];
            this.board[i] = new Board(this, player[i], i, response);
        }
        this.rounds = rounds;
        Duel.duels.add(this);
        Phase.createPhases();
        p1win = 0;
        p2win = 0;
    }

    public void roundReset(){
        didSurrender[0] = false;
        didSurrender[1] = false;
        chain.clear();
        effectStack.clear();
        locations.clear();
        lp[0] = 8000;
        lp[1] = 8000;
        currentPlayer = 0;
        currentPhase = 0;
        isAttackBlocked = false;
        board[0].reset();
        board[1].reset();
        isDrawBlocked[0] = false;
        isDrawBlocked[1] = false;
        for(int i = 0; i < 5; i++) {
            board[0].draw();
            board[1].draw();
        }
    }

    public void turnReset(){
        currentPhase = 0;
        this.didItSummon = false;
        deselect(false);
        for(int player = 0; player < 2; player++)
            if(isScannerSet[player]){
                isScannerSet[player] = false;
                setScanner(scanner[player], 0);
            }

        for(int i = 0; i < 5; i++) {
            this.didItAttack[i] = false;
            this.didItChangePosition[0][i] = false;
            this.didItChangePosition[1][i] = false;
        }
        isTurnFinished = false;
        response.writeMessage(currentPlayer, phaseNames[0]);
    }

    public void run(){
        response.writeMessage(0, new Gson().toJson(player[1]));
        response.writeMessage(0, "0");
        response.writeMessage(1, new Gson().toJson(player[0]));
        response.writeMessage(1, "1");
        int maxHealth1 = 0, maxHealth2 = 0, r = rounds;
        while(rounds > 0){
            //here we design the end of each round
            roundReset();
            startRound();
            maxHealth1 = getMax(maxHealth1, this.lp[0]);
            maxHealth2 = getMax(maxHealth2, this.lp[1]);
            rounds--;
        }
        int p1money = 100, p2money = 100;
        if(p1win > p2win)
            p1money = 3 * r + 3 * maxHealth1;
        else
            p2money = 3 * r + 3 * maxHealth2;
        player[0].setScore(player[0].getScore() + 1000 * p1win);
        player[0].setMoney(player[0].getMoney() + p1money);
        player[1].setScore(player[1].getScore() + 1000 * p2win);
        player[1].setMoney(player[1].getMoney() + p2money);
    }

    public int getMax(int a, int b){
        return Math.max(a, b);
    }

    public void startRound(){
        while(checkPlayers()){
            turnReset();
            turn();
            currentPlayer = 1 - currentPlayer;
        }
    }

    private void turn(){
        while(!isTurnFinished){
            if(lp[0] <= 0 || lp[1] <= 0)
                break;
            if(currentPhase == 0){
                Card card = draw();
                if(card != null)
                    response.writeMessage(currentPlayer, DuelMessageTexts.newCardAdded(card.getName()));
                nextPhase();
            }
            if(currentPhase == 1){
//				OnStandByPhase.isCalled = true;
//				for(int location: getTriggeredCardLocations(currentPlayer, Ground.monsterGround)){
//					Card card = getCard(Ground.monsterGround, location, currentPlayer);
//					card.doEffect(card.getEffect());
//				}
//				for(int location: getTriggeredCardLocations(currentPlayer, Ground.spellTrapGround)){
//					Card card = getCard(Ground.monsterGround, location, currentPlayer);
//					card.doEffect(card.getEffect());
//				}
//				OnStandByPhase.isCalled = false;
                nextPhase();
            }
            listen();
        }
    }

    public boolean checkPlayers(){
        boolean p1 = (lp[0] <= 0 || getNumberOfCards(Ground.allUsable, 0) == 0 || didSurrender[0]);
        boolean p2 = (lp[1] <= 0 || getNumberOfCards(Ground.allUsable, 1) == 0 || didSurrender[1]);

        if(p1) {
            p2win++;
            response.writeMessage(currentPlayer, "player 1 lost this round");
            response.writeMessage(1 - currentPlayer, "player 1 lost this round");
        }
        else if(p2) {
            p1win++;
            response.writeMessage(currentPlayer, "player 2 lost this round");
            response.writeMessage(1 - currentPlayer, "player 2 lost this round");
        }
        return !(p1 || p2);
    }

    public boolean askPositionChange(Ground ground, int location){
        if(ground == Ground.monsterGround)
            return didItChangePosition[0][location];
        else if(ground == Ground.spellTrapGround)
            return didItChangePosition[1][location];
        else
            return true;
    }

    public int getNumberOfCards(Ground from, int player){
        return board[player].getNumberOfCards(from);
    }

    public int getRotationLocation(int location){
        return rotation[location];
    }

    public String listen(){
        //todo modify listen
        int count = 3;
        while(count > 0){
            String input = response.listen(currentPlayer);
            if(input.equals("fail")) {
                count--;
                continue;
            }
            callForMethod(input);
        }
        return "fail";
    }

    private boolean checkPhaseInvalidity(Command command, int phaseNum) {
        return !Phase.checkPhaseValidity(phaseNum, command);
    }

    private void callForMethod(String input) {
        Pattern pattern;
        Matcher matcher;
        for(int i = 0; i < commands.length; i++){
            pattern = Pattern.compile(commands[i]);
            matcher = pattern.matcher(input);
            if(matcher.find()){
                if(i == 0){
                    if(checkPhaseInvalidity(Command.setCardPositionDefense, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    selectCard(Ground.monsterGround, (Integer.parseInt(matcher.group(1)) - 1), currentPlayer);
                    setPosition("DO");
                    return;
                } else if(i == 1){
                    if(checkPhaseInvalidity(Command.setCardPositionAttack, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    selectCard(Ground.monsterGround, (Integer.parseInt(matcher.group(1)) - 1), currentPlayer);
                    setPosition("OO");
                    return;
                } else if(i == 2){
                    if(checkPhaseInvalidity(Command.attack, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    int attacker = Integer.parseInt(matcher.group(1)) - 1;
                    int defender = Integer.parseInt(matcher.group(2)) - 1;
                    selectCard(Ground.monsterGround, attacker, currentPlayer);
                    attack(defender);
                    return;
                } else if(i == 3){
                    if(checkPhaseInvalidity(Command.directAttack, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    int attacker = Integer.parseInt(matcher.group(1)) - 1;
                    selectCard(Ground.monsterGround, attacker, currentPlayer);
                    directAttack();
                } else if(i == 4){
                    if(checkPhaseInvalidity(Command.summon, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    int location = Integer.parseInt(matcher.group(1)) - 1;
                    selectCard(Ground.handGround, location, currentPlayer);
                    summon();
                } else if(i == 5){
                    if(checkPhaseInvalidity(Command.flipSummon, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    int location = Integer.parseInt(matcher.group(1)) - 1;
                    selectCard(Ground.handGround, location, currentPlayer);
                    flipSummon();
                } else if(i == 6){
                    if(checkPhaseInvalidity(Command.set, currentPhase)){
                        response.writeMessage(currentPlayer, DuelMessageTexts.actionNotAllowedInPhase);
                        return;
                    }
                    int location = Integer.parseInt(matcher.group(1)) - 1;
                    selectCard(Ground.handGround, location, currentPlayer);
                    set();
                } else if(i == 7)
                    nextPhase();
                else surrender();
            }
        }
    }

    public void surrender(){
        didSurrender[currentPlayer] = true;
        isTurnFinished = true;
        response.writeMessage(currentPlayer, String.format("Player <%s> surrendered", player[currentPlayer].getUsername()));
        response.writeMessage(1 - currentPlayer, String.format("Player <%s> surrendered", player[currentPlayer].getUsername()));
    }

    public void nextPhase(){
        currentPhase++;
        response.writeMessage(currentPlayer, phaseNames[currentPhase]);
        if(currentPhase == 5){
            response.writeMessage(currentPlayer, DuelMessageTexts.playerTurn(player[1 - currentPlayer].getNickname()));
            response.writeMessage(1 - currentPlayer, DuelMessageTexts.playerTurn(player[1 - currentPlayer].getNickname()));
            isTurnFinished = true;
        }
    }

    public void selectCard(Ground from, int location, int callerPlayer){
        board[callerPlayer].selectCard(from, location);
    }

    public Card getSelectedCard(){
        return board[currentPlayer].getSelectedCard();
    }

    public Ground getSelectedCardOrigin(){
        return board[currentPlayer].getSelectedCardOrigin();
    }

    public int getSelectedCardLocation(){
        return board[currentPlayer].getSelectedCardLocation();
    }

    public void summon(){

        if(getSelectedCard() == null){
            response.writeMessage(currentPlayer, DuelMessageTexts.noCardSelected);
            return;
        }

        if(getSelectedCardOrigin() != Ground.handGround || !(getSelectedCard() instanceof MonsterCard)){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantSummon);
            return;
        }

        if(getNumberOfCards(Ground.monsterGround, currentPlayer) == 5){
            response.writeMessage(currentPlayer, DuelMessageTexts.monsterZoneFull);
            return;
        }

        if(didItSummon) {
            response.writeMessage(currentPlayer, DuelMessageTexts.alreadySummoned);
            return;
        }

        if(!((MonsterCard)getSelectedCard()).isItPossibleToTribute(board[currentPlayer].getNumberOfCards(Ground.monsterGround))){
            response.writeMessage(currentPlayer, DuelMessageTexts.notEnoughTribute);
            return;
        }

        didItSummon = true;
        board[currentPlayer].summonFromHand();
    }

    public Card draw(){
        if(isDrawBlocked[currentPlayer]) {
            isDrawBlocked[currentPlayer] = false;
            return null;
        }
        else
            return board[currentPlayer].draw();
    }

    public void flipSummon(){
        if(board[currentPlayer].flipSummon()){
            didItChangePosition[0][board[currentPlayer].getSelectedCardLocation()] = true;
            deselect(false);
            didItSummon = true;
        }
    }

    public void ritualSummon(){
        if(!board[currentPlayer].isRitualSummonPossible()){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantRitualSummon);
            return;
        }
        response.writeMessage(currentPlayer, "select location of monster cards which you want to sacrifice");
        //todo here
        boolean[] mark = new boolean[]{false, false, false, false, false};
        int sum = 0;
        while(true){
            String ask = listen();
            if(ask.equals("done"))
                break;
            int location = Integer.parseInt(ask) - 1;
            if(mark[location]){
                response.writeMessage(currentPlayer, "already selected this card for sacrifice");
                continue;
            }
            mark[location] = true;
            sum += board[currentPlayer].getCardLevel(location);
        }
        if(sum != ((MonsterCard)getSelectedCard()).getLevel()){
            response.writeMessage(currentPlayer, "the sum of the selected card levels doesn't match the monster's level");
            return;
        }
        for(int location = 0; location < 5; location++)
            if(mark[location])
                killCard(location, Ground.monsterGround, currentPlayer);
        board[currentPlayer].killAdvancedRitualCard();
        board[currentPlayer].summonFromHand();
        response.writeMessage(currentPlayer, DuelMessageTexts.summonSuccessful);
    }

    public void set(){
        if(board[currentPlayer].set()){
            didItChangePosition[0][getSelectedCardLocation()] = true;
            deselect(false);
        }
    }

    public void setPosition(String newPosition){
        if(askPositionChange(Ground.monsterGround, getSelectedCardLocation())){
            response.writeMessage(currentPlayer, DuelMessageTexts.alreadyChangedPos);
            return;
        }
        if(board[currentPlayer].setPosition(newPosition)){ didItChangePosition[0][getSelectedCardLocation()] = true; }
    }

    public String getPosition(int player, int location, Ground ground){
        return board[player].getPosition(location, ground);
    }

    public String[] getPositions(int player, Ground ground){
        return board[player].getPositions(ground);
    }

    public void blockAttack(){ this.isAttackBlocked = true; }

    public void attack(int defenderLocation){
        if(defenderLocation < 0 || defenderLocation > 4){
            response.writeMessage(currentPlayer, DuelMessageTexts.invalidInput);
            return;
        }

        if(getSelectedCard() == null){
            response.writeMessage(currentPlayer, DuelMessageTexts.noCardSelected);
            return;
        }

        if(getSelectedCardOrigin() != Ground.monsterGround){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantAttack);
            return;
        }

        if(didItAttack[getSelectedCardLocation()]){
            response.writeMessage(currentPlayer, DuelMessageTexts.alreadyAttacked);
            return;
        }

        MonsterCard enemyCard = (MonsterCard)board[1 - currentPlayer].getCard(Ground.monsterGround, defenderLocation);
        MonsterCard myCard = (MonsterCard)(getSelectedCard());
        attackerLocation = getSelectedCardLocation();
        Duel.defenderLocation = defenderLocation;

        if(enemyCard == null){
            response.writeMessage(currentPlayer, DuelMessageTexts.noCardToAttack);
            return;
        }

        String myPosition = getPosition(currentPlayer, attackerLocation, Ground.monsterGround);
        String enemyPosition = getPosition(1 - currentPlayer , defenderLocation, Ground.monsterGround);

        if(!myPosition.equals("OO")){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantAttack);
            return;
        }

        int atk_dmg = myCard.getAttackDamage(), defender_dmg;
        //runChain(OnGettingAttacked.getInstance());

        if(isAttackBlocked){
            isAttackBlocked = false;
            return;
        }

        if(isDamageStopped){
            isDamageStopped = false;
            atk_dmg = 0;
        }

        if(enemyPosition.equals("OO")) {
            defender_dmg = enemyCard.getAttackDamage();
            if (defender_dmg < atk_dmg) {
                if(preventDeath){
                    lp[1 - currentPlayer] -= atk_dmg - defender_dmg;
                    preventDeath = false;
                }
                else{
                    lp[1 - currentPlayer] -= atk_dmg - defender_dmg;
                    board[1 - currentPlayer].removeCard(Ground.monsterGround, defenderLocation);
                    response.writeMessage(currentPlayer, DuelMessageTexts.opponentMonsterDestroyed(atk_dmg - defender_dmg));
                }
            } else if (defender_dmg == atk_dmg) {
                board[currentPlayer].removeCard(Ground.monsterGround, getSelectedCardLocation());
                board[1 - currentPlayer].removeCard(Ground.monsterGround, defenderLocation);
                response.writeMessage(currentPlayer, DuelMessageTexts.bothMonsterDestroyed);
            } else {
                board[currentPlayer].removeCard(Ground.monsterGround, getSelectedCardLocation());
                lp[currentPlayer] -= defender_dmg - atk_dmg;
                response.writeMessage(currentPlayer, DuelMessageTexts.yourMonsterDestroyed(defender_dmg - atk_dmg));
            }
        } else{
            if(enemyPosition.equals("DH")){
                response.writeMessage(currentPlayer, String.format("opponent’s monster card was <%s> and ", enemyCard.getName()));
                // we add this string to the first of every coming output, if the enemy card is hidden (DH mode)
            }

            defender_dmg = myCard.getDefenseDamage();
            if(defender_dmg < atk_dmg){
                board[1 - currentPlayer].removeCard(Ground.monsterGround, defenderLocation);
                response.writeMessage(currentPlayer, DuelMessageTexts.defenseDestroyed);
            } else if(defender_dmg == atk_dmg){
                response.writeMessage(currentPlayer, DuelMessageTexts.noCardDestroyed);
            } else{
                lp[currentPlayer] -= defender_dmg - atk_dmg;
                response.writeMessage(currentPlayer, DuelMessageTexts.noCardDestroyedWithDamage(defender_dmg - atk_dmg));
            }
        }
        didItAttack[getSelectedCardLocation()] = true;
        deselect(false);
    }

//    private void runChain(Event event) {
//        //event.isCalled = true;
//        int askedPlayer = 1 - currentPlayer;
//        Card card1 = getSelectedCard();
//        chain.add(card1);
//        Effect effect = card1.getEffect();
//        effectStack.add(effect);
//        locations.add(getSelectedCardLocation());
//        deselect(false);
//        while(true){
//            ArrayList<Integer> triggeredMonsters = getTriggeredCardLocations(askedPlayer, Ground.monsterGround);
//            ArrayList<Integer> triggeredSpells = getTriggeredCardLocations(askedPlayer, Ground.spellTrapGround);
//            if(triggeredMonsters.isEmpty() && triggeredSpells.isEmpty()){
//                if(chain.size() > 1)
//                    Main.outputToUser("no more effect can be added to chain");
//                effect.callEvent(false);
//                break;
//            }
//            String ask = listen(false, Response.askForEffectActivation, new String[]{"yes", "no"});
//            if(!ask.equals("yes"))
//                break;
//            int location = -1;
//            Ground ground = Ground.fieldGround;
//            while(location == -1){
//                ground = Ground.valueOf(listen(false, "which ground?", new String[]{
//                        "monsterGround",
//                        "spellTrapGround",
//                        "handGround",
//                        "graveyardGround",
//                        "fieldGround"
//                }));
//                location = Integer.parseInt(listen(false, "which location?", new String[]{
//                        "1", "2", "3", "4", "5"
//                })) - 1;
//                if(ground == Ground.monsterGround){
//                    for(int i: triggeredMonsters)
//                        if(location == i)
//                            break;
//                } else if(ground == Ground.spellTrapGround){
//                    for(int i: triggeredSpells)
//                        if(location == i)
//                            break;
//                } else {
//                    Main.outputToUser("the selected card doesn't have any effect to run");
//                    location = -1;
//                }
//            }
//            Card card = getCard(ground, location, askedPlayer);
//            effect.callEvent(false);
//            effect = card.getEffect();
//            chain.add(card);
//            effectStack.add(effect);
//            locations.add(location);
//            effect.callEvent(true);
//            Main.outputToUser("added the card to chain\nnow it's player " + (1 - askedPlayer) + "'s turn to add to chain");
//            askedPlayer = 1 - askedPlayer;
//        }
//
//        while(!chain.isEmpty()){
//            chain.pop();
//            locations.pop();
//            effectStack.pop().doEffect();
//        }
//    }

    public void directAttack(){
        if(getSelectedCard() == null){
            response.writeMessage(currentPlayer, DuelMessageTexts.noCardSelected);
            return;
        }

        if(getSelectedCardOrigin() != Ground.monsterGround
                || !getPosition(currentPlayer, getSelectedCardLocation(), Ground.monsterGround).equals("OO")){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantAttack);
            return;
        }

        if(didItAttack[getSelectedCardLocation()]){
            response.writeMessage(currentPlayer, DuelMessageTexts.alreadyAttacked);
            return;
        }

        if(getNumberOfCards(Ground.monsterGround, 1 - currentPlayer) != 0){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantAttackDirect);
            return;
        }

        MonsterCard attacker = (MonsterCard) getSelectedCard();
        lp[1 - currentPlayer] -= attacker.getAttackDamage();
        didItAttack[getSelectedCardLocation()] = true;
        deselect(false);
        response.writeMessage(currentPlayer, DuelMessageTexts.opponentReceiveDamage(attacker.getAttackDamage()));
    }

    public void activateSpell(){
        if(getSelectedCard() == null){
            response.writeMessage(currentPlayer, DuelMessageTexts.noCardSelected);
            return;
        }

        if(getSelectedCardOrigin() != Ground.handGround){
            response.writeMessage(currentPlayer, DuelMessageTexts.cantSummon);
            return;
        }

        if(getNumberOfCards(Ground.spellTrapGround, currentPlayer) == 5){
            response.writeMessage(currentPlayer, DuelMessageTexts.spellZoneFull);
            return;
        }

        if(board[currentPlayer].activateSpell()){
            didItChangePosition[1][getSelectedCardLocation()] = true;
            response.writeMessage(currentPlayer, DuelMessageTexts.spellActivated);
            //runChain(OnSpellActivation.getInstance());
            // TODO: 7/19/2021
        }
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public Card getCard(Ground from, int location, int player){
        return board[player].getCard(from, location);
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

//    public ArrayList<Integer> getTriggeredCardLocations(int player, Ground ground){
//        ArrayList<Integer> answer = new ArrayList<>();
//        for(int i = 0; i < 5; i++){
//            Card card;
//            if(ground == Ground.spellTrapGround)
//                card = board[player].getCard(Ground.spellTrapGround, i);
//            else
//                card = board[player].getCard(Ground.monsterGround, i);
//            if(card != null && card.checkEffects())
//                answer.add(i);
//        }
//        return answer;
//    }

    public void killCard(int location, Ground ground, int player){
        if(location == -1){
            for(location = 0; location < 5; location++)
                if(board[player].getCard(ground, player) != null){
                    board[player].killCard(location, ground);
                    break;
                }
        }
        board[player].killCard(location, ground);
    }

    public void killCard(String cardName, Ground ground, int player){
        if(cardName == null)
            return;
        board[player].killCard(cardName, ground);
    }

    public void stopDamage(){
        isDamageStopped = true;
    }

    public void setScanner(MonsterCard card, int player) {
        for(int i = 0; i < 5; i++){
            MonsterCard card1 = (MonsterCard) board[player].getCard(Ground.monsterGround, i);
            if(card1.getName().equals("Scanner")) {
                board[player].replaceCard(Ground.monsterGround, i, card);
                scanner[player] = card1;
                isScannerSet[player] = true;
                return;
            }
        }
    }

    public void decreaseLp(int damage, int player) {
        lp[player] -= damage;
    }

    public void stopMyDeath() {
        preventDeath = true;
    }

    public MonsterCard getBeastKing(int player) {
        for(int i = 0; i < 5; i++)
            if(board[player].getCard(Ground.monsterGround, i).getName().equals("Beast King"))
                return (MonsterCard) board[player].getCard(Ground.monsterGround, i);
        return null;
    }

    public void killAllCardsOnGround(Ground ground, int player) {
        for(int i = 0; i < 5; i++)
            board[player].killCard(i, ground);
    }

    public void specialSummon(int ownerPlayer, Ground ground, int location, String position) {
        board[ownerPlayer].specialSummon(ground, location, position);
    }

    public boolean isThereCardOnLocation(int ownerPlayer, Ground ground, int location){
        return board[ownerPlayer].isThereCardOnLocation(ground, location);
    }

    public int getLevelSum(Ground ground, int ownerPlayer) {
        return board[ownerPlayer].getLevelSum(ground);
    }

    public void setCalculator(int ownerPlayer ,int damage) {
        MonsterCard card = null;
        for(int i = 0; i < 5; i++) {
            card = (MonsterCard) board[ownerPlayer].getCard(Ground.monsterGround, i);
            if(card.getName().equals("Calculator"))
                break;
        }
        card.setAttackDamage(damage);
    }

    public void setCardBlockedStatus(int ownerPlayer, Ground ground, int location, boolean status) {
        board[ownerPlayer].setCardBlockedStatus(ground, location, status);
    }

    public void removeCard(int ownerPlayer, Ground ground, int location) {
        board[ownerPlayer].removeCard(ground, location);
    }

    public void addCard(int ownerPlayer, Ground ground, Card card, String position) {
        board[ownerPlayer].addCard(ground, card, position);
    }

    public void destroyRecentSpell() {
        chain.pop();
        effectStack.pop();
        int location = locations.pop(), size = chain.size();
        killCard(location, Ground.spellTrapGround, (size + 1 + currentPlayer) % 2);
    }

    public void blockDraw(int player) {
        isDrawBlocked[player] = true;
    }
}
