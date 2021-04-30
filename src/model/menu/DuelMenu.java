package model.menu;

import model.Player;
import model.board.Board;

public class DuelMenu {
    int playerLF = 8000;
    int opponentLF = 8000;
    Player player;
    Player opponentPlayer;
    Player currentPlayer;
    Board playerBoard;
    Board opponentBoard;
    int status; //0 for player 1 win    1 for player 2 win  2 for draw
    public DuelMenu(){
        player = Player.getPlayerByUsername(MainMenu.getCurrentUser());
        opponentPlayer = Player.getPlayerByUsername(MainMenu.getOpponentUser());
        currentPlayer = player;
        playerBoard = player.getBoard();
        opponentBoard = player.getBoard();
    }
    public void run(String command){

    }
}
