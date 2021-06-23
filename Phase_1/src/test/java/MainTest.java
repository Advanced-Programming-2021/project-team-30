import controller.Controller;
import model.Menu;
import model.Player;
import model.cards.Card;
import model.menu.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest {
    static Controller controller = new Controller();
    static ByteArrayOutputStream output = new ByteArrayOutputStream();
    @BeforeEach
    public void initOutput(){
        System.setOut(new PrintStream(output));
    }
    @AfterEach
    public void resetOutput(){
        output.reset();
    }
    @Test
    public void testNavigate(){
        controller.navigate("menu enter Profile");
        Assertions.assertEquals("please login first\r\n", output.toString());
    }
    @Test
    public void testRegisterMenu(){
        Player.getPlayers().clear();
        controller.registerMenu.run("user create --username Ali --nickname AliJoon --password ALi1234");
        Assertions.assertEquals("user created successfully!\r\n", output.toString());
        output.reset();
        controller.registerMenu.run("user create Ali --nickname AliJoon --password ALi1234");
        Assertions.assertEquals("invalid command\r\n", output.toString());
        output.reset();
        controller.registerMenu.run("user create -u Ali -p ALi1234 -n AliJoon");
        Assertions.assertEquals("user with username Ali already exists\r\n", output.toString());
        output.reset();
        controller.registerMenu.run("user create -u Hassan -p ALi1234 -n AliJoon");
        Assertions.assertEquals("user with nickname AliJoon already exists\r\n", output.toString());
    }
    @Test
    public void testLogin(){
        controller.registerMenu.run("user create -u Ali -p ALi1234 -n AliJoon");
        Assertions.assertEquals("Ali", Player.getPlayers().get(0).getUsername());
        output.reset();
        controller.registerMenu.run("user login -u Alii -p ALi1234");
        Assertions.assertEquals("Username and password didn’t match!\r\n", output.toString());
        output.reset();
        controller.registerMenu.run("user login -u Ali -p ALii1234");
        Assertions.assertEquals("Username and password didn’t match!\r\n", output.toString());
        output.reset();
        controller.registerMenu.run("user login -u Ali -p ALi1234");
        Assertions.assertEquals("user logged in successfully!\r\n", output.toString());
        Assertions.assertEquals(Menu.MAIN, Controller.currentMenu);

    }
    @Test
    public void testScoreBoard(){
        Controller.currentMenu = Menu.MAIN;
        Player player = Player.getPlayers().get(0);
        player.setScore(2000);
        controller.navigate("menu enter Scoreboard");
        Assertions.assertEquals(Menu.SCOREBOARD, Controller.currentMenu);
        new Player("Hassan", "Hassan123", "HassanJoon").setScore(3000);
        new Player("Zia", "Zia123", "ZiaJoon").setScore(3000);
        new Player("Arya", "Arya123", "AryaJoon").setScore(1000);
        resetOutput();
        controller.scoreboardMenu = new ScoreboardMenu();
        controller.scoreboardMenu.showScoreboard("scoreboard show");
        String expected = "1- HassanJoon: 3000\r\n1- ZiaJoon: 3000\r\n3- AliJoon: 2000\r\n4- AryaJoon: 1000\r\n";
        Assertions.assertEquals(expected, output.toString());
        controller.navigate("menu exit");
    }
    @Test
    public void testProfileMenu(){
        Assertions.assertEquals("Ali", MainMenu.getCurrentUser().getUsername());
        controller.navigate("menu enter Profile");
        Assertions.assertEquals(Menu.PROFILE, Controller.currentMenu);
        resetOutput();
        controller.profileMenu = new ProfileMenu();
        controller.setMenusCurrentUser();
        controller.profileMenu.run("profile change -n Oskol");
        Assertions.assertEquals("nickname changed successfully!\r\n", output.toString());
        Assertions.assertEquals("Oskol", controller.profileMenu.getCurrentPlayer().getNickname());
        resetOutput();
        controller.profileMenu.run("profile change -p -n 1381 -c 1381");
        Assertions.assertEquals("current password is invalid\r\n", output.toString());
        resetOutput();
        controller.profileMenu.run("profile change -c ALi1234 -n ALi1234 -p");
        Assertions.assertEquals("please enter a new password\r\n", output.toString());
        resetOutput();
        controller.profileMenu.run("profile change -p -n 1381 -c ALi1234");
        Assertions.assertEquals("password changed successfully!\r\n", output.toString());
        Assertions.assertEquals("1381", controller.profileMenu.getCurrentPlayer().getPassword());
        controller.navigate("menu exit");
    }
    @Test
    public void testShopMenu(){
        controller.navigate("menu enter Shop");
        Assertions.assertEquals(Menu.SHOP, Controller.currentMenu);
        controller.shopMenu = new ShopMenu();
        ShopMenu shopMenu = controller.shopMenu;
        controller.setMenusCurrentUser();
        //shopMenu.run("");


    }

}
