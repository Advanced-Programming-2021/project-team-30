import controller.Controller;
import model.Initializer;
import model.Player;
import model.menu.DeckMenu;
import model.menu.MainMenu;
import model.menu.ShopMenu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ShopMenuTest {
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
        Initializer.readCardsFromCSV();
        controller.shopMenu = new ShopMenu();
        ShopMenu shopMenu = controller.shopMenu;
        controller.setMenusCurrentUser();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        player.setMoney(2000);
        shopMenu.run("shop buy Suijin");
        Assertions.assertEquals("not enough money\r\n", output.toString());
        shopMenu.run("increase --money 20000");
        Assertions.assertEquals(22000, player.getMoney());
        shopMenu.run("shop buy Suijin");
        Assertions.assertEquals("Suijin", shopMenu.getCurrentPlayer().getCards().get(0).getName());
    }
}
