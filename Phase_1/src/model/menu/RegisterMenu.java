package model.menu;

import controller.Controller;
import model.Menu;
import model.Player;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.regex.RegisterMenuRegex;
import model.response.MenuResponse;
import model.response.RegisterMenuResponse;
import view.Main;

import java.util.regex.Matcher;

public class RegisterMenu {
    private void createUser(Matcher matcher){
        if (matcher.find()){
            String username = matcher.group("username");
            String password = matcher.group("password");
            String nickname = matcher.group("nickname");
            if (Player.getPlayerByUsername(username) != null)
                Main.outputToUser(RegisterMenuResponse.usernameExists(username));
            else if (Player.getPlayerByNickname(nickname) != null)
                Main.outputToUser(RegisterMenuResponse.nicknameExists(nickname));
            else{
                new Player(username, password, nickname);
                Main.outputToUser(RegisterMenuResponse.userCreated);
            }

        }

    }
    private void loginUser(Matcher matcher){
        if (matcher.find()){
            String username = matcher.group("username");
            String password = matcher.group("password");
            Player player = Player.getPlayerByUsername(username);
            if (player == null)
                Main.outputToUser(RegisterMenuResponse.notMatch);
            else if (!(player.getPassword().equals(password)))
                Main.outputToUser(RegisterMenuResponse.notMatch);
            else{
                Controller.currentMenu = Menu.MAIN;
                MainMenu.setCurrentUser(player);
                Main.outputToUser(RegisterMenuResponse.userLoggedIn);
            }
        }
    }

    public void run(String command){
        Matcher registerMatcher = Regex.getCommandMatcherRegexes(command, RegisterMenuRegex.registerUser);
        Matcher registerAbbrMatcher = Regex.getCommandMatcherRegexes(command, RegisterMenuRegex.registerUserAbbr);
        Matcher loginMatcher = Regex.getCommandMatcherRegexes(command, RegisterMenuRegex.loginUser);
        Matcher loginAbbrMatcher = Regex.getCommandMatcherRegexes(command, RegisterMenuRegex.loginUserAbbr);
        if (registerMatcher != null)
            createUser(registerMatcher);
        else if (registerAbbrMatcher != null)
            createUser(registerAbbrMatcher);
        else if (loginMatcher != null)
            loginUser(loginMatcher);
        else if (loginAbbrMatcher != null)
            loginUser(loginAbbrMatcher);
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);

    }
}
