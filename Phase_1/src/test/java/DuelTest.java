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

public class DuelTest {
    static Controller controller = new Controller();
    static Player player = new Player("ali", "1234", "ali");
    static Player secondPlayer = new Player("reza", "1111", "reza");
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

    }
}
