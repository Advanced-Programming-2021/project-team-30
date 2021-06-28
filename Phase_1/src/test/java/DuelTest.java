import controller.Controller;
import model.Deck;
import model.Duel;
import model.Initializer;
import model.Player;
import model.cards.Card;
import model.menu.DuelMenu;
import model.menu.MainMenu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DuelTest {
    static Controller controller = new Controller();
    static Player player = new Player("ali", "1234", "ali");
    static Player secondPlayer = new Player("reza", "1111", "reza");
    public int nthOccurrence(String str1, String str2, int n) {
        String tempStr = str1;
        int tempIndex = -1;
        int finalIndex = 0;
        for(int occurrence = 0; occurrence < n ; ++occurrence){
            tempIndex = tempStr.indexOf(str2);
            if(tempIndex==-1){
                finalIndex = 0;
                break;
            }
            tempStr = tempStr.substring(++tempIndex);
            finalIndex+=tempIndex;
        }
        return --finalIndex;
    }
    public String removeLineSeparators(String string){
        return string.replaceAll("\n", "").replaceAll("\r", "");
    }
    @BeforeAll
    static void setUser(){
        MainMenu.setCurrentUser(player);
        controller.setMenusCurrentUser();
    }
    @AfterAll
    static void deleteUsers(){
        Player.getPlayers().remove(player);
        Player.getPlayers().remove(secondPlayer);
    }
    public void setDecks(){
        Deck aliDeck = new Deck("aliDeck", player);
        Deck rezaDeck = new Deck("rezaDeck", secondPlayer);
        Initializer.readCardsFromCSV();
        for (Card monsterCard : Initializer.monsterCards) {
            aliDeck.addCardToMainDeck(monsterCard);
            rezaDeck.addCardToMainDeck(monsterCard);
        }
        player.setActiveDeck(aliDeck);
        secondPlayer.setActiveDeck(rezaDeck);
    }
    @Test
    public void test() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Main.readFromConsole = false;
        Main.setInput();
        Initializer.readCardsFromCSV();
        setDecks();
        controller.duelMenu = new DuelMenu();
        DuelMenu duelMenu = controller.duelMenu;
        controller.setMenusCurrentUser();
        Assertions.assertEquals(41, player.getActiveDeck().getMainDeck().size());
        duelMenu.run("duel --new --second-player reza --rounds 1");
        Duel duel = Duel.getRecentDuel();
        Assertions.assertNotNull(duel);
        Assertions.assertNotNull(player.getActiveDeck());
        String out = output.toString();
        String expected = "phase: <<standby phase>>phase: <<main phase 1>><reza>:<8000>\tc\tc\tc\tc\tcDN\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE GY\t\t\t\t\t\tFZ------------------------------FZ\t\t\t\t\t\tGY\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE \t\t\t\t\t\tDN\tc\tc\tc\tc\tc\tc<ali>:<8000>card selected<reza>:<8000>\tc\tc\tc\tc\tcDN\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE GY\t\t\t\t\t\tFZ------------------------------FZ\t\t\t\t\t\tGY\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE \t\t\t\t\t\tDN\tc\tc\tc\tc\tc\tc<ali>:<8000>you can’t summon this card<reza>:<8000>\tc\tc\tc\tc\tcDN\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE GY\t\t\t\t\t\tFZ------------------------------FZ\t\t\t\t\t\tGY\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE \t\t\t\t\t\tDN\tc\tc\tc\tc\tc\tc<ali>:<8000>phase: <<battle phase>><reza>:<8000>\tc\tc\tc\tc\tcDN\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE GY\t\t\t\t\t\tFZ------------------------------FZ\t\t\t\t\t\tGY\tE \tE \tE \tE \tE \tE \tE \tE \tE \tE \t\t\t\t\t\tDN\tc\tc\tc\tc\tc\tc<ali>:<8000>Player <ali> surrenderedplayer 1 lost this round";
        Assertions.assertEquals(expected, removeLineSeparators(out.substring(nthOccurrence(out, "\n", 2) + 1)));
        Assertions.assertEquals(0, secondPlayer.getScore());
        Assertions.assertEquals(0, player.getScore());
    }
}
