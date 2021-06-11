package model.menu;

import com.google.gson.Gson;
import model.cards.Card;
import model.regex.ImportExportMenuRegex;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.response.MenuResponse;
import view.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ImportExportMenu {
    public ArrayList<Card> cards;
    public Card getCardFromCards(String cardName){
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
    public void write(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            Card card = getCardFromCards(cardName);
            try {
                FileWriter fileWriter = new FileWriter("Cards.json");
                fileWriter.write(new Gson().toJson(card));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void read(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            try {
                String json = new String(Files.readAllBytes(Paths.get("Cards.json")));
                Card card = new Gson().fromJson(json, Card.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void run(String command){
        if (Regex.getCommandMatcher(command, ImportExportMenuRegex.importCard).find())
            read(Regex.getCommandMatcher(command, ImportExportMenuRegex.importCard));
        else if (Regex.getCommandMatcher(command, ImportExportMenuRegex.exportCard).find())
            write(Regex.getCommandMatcher(command, ImportExportMenuRegex.exportCard));
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);

    }

}
