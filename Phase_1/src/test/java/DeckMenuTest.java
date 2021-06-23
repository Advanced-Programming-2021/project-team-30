import controller.Controller;
import model.Deck;
import model.Initializer;
import model.Menu;
import model.Player;
import model.cards.Card;
import model.menu.DeckMenu;
import model.menu.MainMenu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class DeckMenuTest {
    static Controller controller = new Controller();
    static Player player = new Player("ali", "1234", "ali");
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
        Card card6 = Initializer.monsterCardToBuild("Suijin");
        deckMenu.getCurrentPlayer().addCards(new ArrayList<Card>(){{
            add(card1);
            add(card2);
            add(card3);
            add(card4);
            add(card5);
            add(card6);
        }});
        Assertions.assertEquals(card1, player.getPlayerCardByName("Suijin"));
        deckMenu.run("deck add-card --deck Zia --card Suijin");
        Assertions.assertEquals(deck, player.getActiveDeck());
        Assertions.assertEquals("Suijin", deck.getMainDeck().get(0).getName());
        deckMenu.run("deck add-card --deck Zia --side --card Suijin");
        Assertions.assertEquals("Suijin", deck.getSideDeck().get(0).getName());
        deckMenu.run("deck rm-card --card Suijin --deck Zia");
        Assertions.assertEquals(0, deck.getMainDeck().size());
        deckMenu.run("deck rm-card --card Suijin --side --deck Zia");
        Assertions.assertEquals(0, deck.getSideDeck().size());
        Assertions.assertEquals(6, player.getCards().size());
        deckMenu.run("deck add-card --deck Zia --side --card Suijin");
        deckMenu.run("deck add-card --deck Zia --side --card Suijin");
        deckMenu.run("deck add-card --deck Zia --side --card Suijin");
        deckMenu.run("deck add-card --deck Zia --side --card Suijin");
        Assertions.assertEquals(3, deck.getSideDeck().size());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        deckMenu.run("deck show --deck-name Zia");
        Assertions.assertEquals("Suijin", deck.getSideDeck().get(0).getName());
        Assertions.assertEquals("Suijin", deck.getSideDeck().get(1).getName());
        Assertions.assertEquals("Suijin", deck.getSideDeck().get(2).getName());
        Assertions.assertEquals("Deck: ZiaMain deck:Monsters:Spell and Traps:", removeLineSeparators(output.toString()));
        output.reset();
        deckMenu.run("deck show --deck-name Zia --side");
        String expected = "Deck: Zia" +
                "Side deck:" +
                "Monsters:" +
                "Suijin: During damage calculation in your opponent's turn, if this card is being attacked: You can target the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). This effect can only be used once while this card is face-up on the field." +
                "Suijin: During damage calculation in your opponent's turn, if this card is being attacked: You can target the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). This effect can only be used once while this card is face-up on the field." +
                "Suijin: During damage calculation in your opponent's turn, if this card is being attacked: You can target the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). This effect can only be used once while this card is face-up on the field." +
                "Spell and Traps:";
        Assertions.assertEquals(expected, removeLineSeparators(output.toString()));


    }
}
