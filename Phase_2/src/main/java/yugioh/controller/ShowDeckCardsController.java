package yugioh.controller;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import yugioh.model.Card;
import yugioh.model.Deck;

import java.util.ArrayList;

public class ShowDeckCardsController {
    public GridPane mainDeckGridPane;
    public GridPane sideDeckGridPane;
    public static Deck currentDeck;
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
                    String path = EditDeckController.cardNameToImage(cards.get(counter).getName());
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
                    String path = EditDeckController.cardNameToImage(cards.get(counter).getName());
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
        setMainDeckCardImages(mainDeckGridPane, currentDeck.getMainDeck());
        setSideDeckCardImages(sideDeckGridPane, currentDeck.getSideDeck());

    }
}
