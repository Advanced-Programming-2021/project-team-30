package yugioh.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import yugioh.model.Card;
import yugioh.model.CardInitializer;
import yugioh.model.Deck;
import yugioh.view.DeckMenuView;
import yugioh.view.LoginMenuView;
import yugioh.view.PickCardView;

import java.io.File;
import java.util.ArrayList;

public class EditDeckController {
    public static Deck currentDeck;
    public GridPane mainDeckGridPane;
    public GridPane sideDeckGridPane;
    public ImageView cardImage;
    public Label cardNameLabel;
    public Label deckNameLabel;
    public static String cardNameToImage(String cardName){
        String imageName = "";
        File monsterFolder = new File("src/main/resources/yugioh/assets/cards/Monsters");
        File spellTrapFolder = new File("src/main/resources/yugioh/assets/cards/SpellTrap");
        File[] monstersFiles = monsterFolder.listFiles();
        assert monstersFiles != null;
        for (File file : monstersFiles) {
            String name = file.getName().replace(".jpg", "");
            if (name.equals(cardName)){
                imageName = "file:\\" + file.getAbsolutePath();
            }
        }
        File[] spellTrapFiles = spellTrapFolder.listFiles();
        assert spellTrapFiles != null;
        for (File file : spellTrapFiles) {
            String name = file.getName().replace(".jpg", "");
            if (name.equals(cardName)){
                imageName = "file:\\" + file.getAbsolutePath();
            }
        }
        return imageName;
    }

    public void setMainDeckCardImages(GridPane cardGridPane, ArrayList<Card> cards){
        ImageView[][] imageViews = new ImageView[5][12];
        ArrayList<Node> nodesArrayList = new ArrayList<>();
        for (Node child : cardGridPane.getChildren()) {
            if (child instanceof ImageView) {
                nodesArrayList.add(child);
            }
        }
        for (Node node : nodesArrayList) {
            cardGridPane.getChildren().remove(node);
        }
        cardGridPane.setHgap(5);
        cardGridPane.setVgap(5);
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                if (counter < cards.size()) {
                    String path = cardNameToImage(cards.get(counter).getName());
                    imageViews[i][j] = new ImageView(new Image(path));
                    imageViews[i][j].setFitWidth(60);
                    imageViews[i][j].setFitHeight(80);
                    counter++;
                    cardGridPane.add(imageViews[i][j], j, i);
                }
            }
        }

    }
    public void setSideDeckCardImages(GridPane cardGridPane, ArrayList<Card> cards){
        ImageView[][] imageViews = new ImageView[5][12];
        ArrayList<Node> nodesArrayList = new ArrayList<>();
        for (Node child : cardGridPane.getChildren()) {
            if (child instanceof ImageView) {
                nodesArrayList.add(child);
            }
        }
        for (Node node : nodesArrayList) {
            cardGridPane.getChildren().remove(node);
        }
        cardGridPane.setHgap(5);
        cardGridPane.setVgap(5);
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                if (counter < cards.size()) {
                    String path = cardNameToImage(cards.get(counter).getName());
                    imageViews[i][j] = new ImageView(new Image(path));
                    imageViews[i][j].setFitWidth(60);
                    imageViews[i][j].setFitHeight(80);
                    counter++;
                    cardGridPane.add(imageViews[i][j], j, i);
                }
            }
        }

    }

    public void initialize(){
        deckNameLabel.setText("Current Deck Name : " + currentDeck.getName());
        setMainDeckCardImages(mainDeckGridPane, currentDeck.getMainDeck());
        setSideDeckCardImages(sideDeckGridPane, currentDeck.getSideDeck());
        GridPane[] gridPanes = new GridPane[]{mainDeckGridPane, sideDeckGridPane};
        for (GridPane pane : gridPanes) {
            for (Node child : pane.getChildren()) {
                ImageView imageView = (ImageView) child;
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cardImage.setImage(imageView.getImage());
                        String name = cardImage.getImage().getUrl();
                        File file = new File(name);
                        String cardName = file.getName().replace(".jpg", "");
                        cardNameLabel.setLayoutX(180 - (4 * cardName.length()));
                        cardNameLabel.setText("Card name : " + cardName);
                    }
                });
            }
        }
    }

    public void back(ActionEvent actionEvent) throws Exception {
        new DeckMenuView().start(LoginMenuView.stage);
    }

    public void addCardMainDeck(MouseEvent mouseEvent) throws Exception {
        if (currentDeck.getMainDeck().size() == 60) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Card Failed!");
            alert.setContentText("Main Deck is full!");
            alert.show();
        } else {
            PickCardController.deck = currentDeck.getMainDeck();
            PickCardController.isMainDeck = true;
            new PickCardView().start(LoginMenuView.stage);
        }
    }

    public void removeCardMainDeck(MouseEvent mouseEvent) {
        String cardName = cardNameLabel.getText().replace("Card name : ", "");
        Card card = CardInitializer.cardToBuild(cardName);
        currentDeck.removeCardFromMainDeck(card);
        setMainDeckCardImages(mainDeckGridPane, currentDeck.getMainDeck());
        cardImage.setImage(null);
    }

    public void addCardSideDeck(MouseEvent mouseEvent) throws Exception {
        if (currentDeck.getSideDeck().size() == 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Card Failed!");
            alert.setContentText("Side Deck is full!");
            alert.show();
        } else {
            PickCardController.deck = currentDeck.getSideDeck();
            PickCardController.isMainDeck = false;
            new PickCardView().start(LoginMenuView.stage);
        }
    }

    public void removeCardSideDeck(MouseEvent mouseEvent) {
        String cardName = cardNameLabel.getText().replace("Card name : ", "");
        Card card = CardInitializer.cardToBuild(cardName);
        currentDeck.removeCardFromSideDeck(card);
        setSideDeckCardImages(sideDeckGridPane, currentDeck.getSideDeck());
        cardImage.setImage(null);
    }
}
