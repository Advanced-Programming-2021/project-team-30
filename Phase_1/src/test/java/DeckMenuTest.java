import controller.Controller;
import model.Deck;
import model.Initializer;
import model.Menu;
import model.Player;
import model.cards.Card;
import model.menu.DeckMenu;
import model.menu.MainMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DeckMenuTest {
    static Controller controller = new Controller();
    static Player player = new Player("ali", "1234", "ali");
    @BeforeAll
    static void setUser(){
        MainMenu.setCurrentUser(player);
        controller.setMenusCurrentUser();
    }
    @Test
    public void test(){
        controller.deckMenu = new DeckMenu();
        DeckMenu deckMenu = controller.deckMenu;
        controller.setMenusCurrentUser();
        deckMenu.run("deck create AliDeck");
        Assertions.assertEquals(deckMenu.getCurrentPlayer().getDecks().get(0).getName(), "AliDeck");
        deckMenu.run("deck delete AliDeck");
        Assertions.assertEquals(deckMenu.getCurrentPlayer().getDecks().size(), 0);
        deckMenu.run("deck set-activate AliDeck");
        deckMenu.run("deck create Zia");
        deckMenu.run("deck set-activate Zia");
        Deck deck = deckMenu.getCurrentPlayer().getActiveDeck();
        Assertions.assertEquals("Zia", deck.getName());
        Initializer.readCardsFromCSV();
        Card card1 = Initializer.monsterCardToBuild("Suijin");
        Card card2 = Initializer.monsterCardToBuild("Suijin");
        Card card3 = Initializer.monsterCardToBuild("Suijin");
        Card card4 = Initializer.monsterCardToBuild("Wattkid");
        Card card5 = Initializer.monsterCardToBuild("Wattkid");
        deckMenu.getCurrentPlayer().addCards(new ArrayList<Card>(){{
            add(card1);
            add(card2);
            add(card3);
            add(card4);
            add(card5);
        }});
        Assertions.assertEquals(card1, player.getPlayerCardByName("Suijin"));
        deckMenu.run("deck add-card --deck Zia --card Suijin");
        Assertions.assertEquals(deck, player.getActiveDeck());
        Assertions.assertEquals("Suijin", deck.getMainDeck().get(0).getName());


    }
}
