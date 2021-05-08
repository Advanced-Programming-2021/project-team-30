package model.menu;

import model.Menu;
import model.Player;
import model.regex.MenuRegex;
import model.regex.ProfileMenuRegex;
import model.regex.Regex;
import model.response.MenuResponse;
import model.response.ProfileMenuResponse;
import view.Main;

import java.util.regex.Matcher;

public class ProfileMenu {
    private Player currentPlayer;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ProfileMenu(){
        Player player = MainMenu.getCurrentUser();
        if (player != null)
            setCurrentPlayer(player);

    }
    private void changeNickname(Matcher matcher){
        if (matcher.find()){
            String nickname = matcher.group("nickname");
            if (Player.getPlayerByNickname(nickname) != null)
                Main.outputToUser(ProfileMenuResponse.nicknameExists(nickname));
            else{
                getCurrentPlayer().setNickname(nickname);
                Main.outputToUser(ProfileMenuResponse.changeNickname);
            }
        }

    }
    private void changePassword(Matcher matcher){
        if (matcher.find()){
            String currentPassword = matcher.group("currentPassword");
            String newPassword = matcher.group("newPassword");
            if (!(getCurrentPlayer().getPassword().equals(currentPassword)))
                Main.outputToUser(ProfileMenuResponse.passwordInvalid);
            else if (currentPassword.equals(newPassword))
                Main.outputToUser(ProfileMenuResponse.enterNewPassword);
            else{
                getCurrentPlayer().setPassword(newPassword);
                Main.outputToUser(ProfileMenuResponse.changePassword);
            }
        }
    }
    public void run(String command){
        Matcher changeNicknameMatcher = Regex.getCommandMatcher(command, ProfileMenuRegex.changeNickname);
        Matcher changeNicknameAbbrMatcher = Regex.getCommandMatcher(command, ProfileMenuRegex.changeNicknameAbbr);
        Matcher changePasswordMatcher = Regex.getCommandMatcherRegexes(command, ProfileMenuRegex.changePassword);
        Matcher changePasswordAbbrMatcher = Regex.getCommandMatcherRegexes(command, ProfileMenuRegex.changePasswordAbbr);
        if (changeNicknameMatcher.find())
            changeNickname(changeNicknameMatcher);
        else if (changeNicknameAbbrMatcher.find())
            changeNickname(changeNicknameAbbrMatcher);
        else if (changePasswordMatcher != null)
            changePassword(changePasswordMatcher);
        else if (changePasswordAbbrMatcher != null)
            changePassword(changePasswordAbbrMatcher);
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);


    }
}
