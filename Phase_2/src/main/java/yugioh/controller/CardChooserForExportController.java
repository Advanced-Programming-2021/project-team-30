package yugioh.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import yugioh.model.cards.Card;
import yugioh.model.CardInitializer;
import yugioh.view.ImportExportView;
import yugioh.view.LoginMenuView;

import java.io.File;

public class CardChooserForExportController {
    public GridPane gridPane;
    public ImageView cardImage;
    public Label cardNameLabel;
    public GridPane gridPane2;

    public void setCardImages(GridPane cardGridPane, File folder){
        File[] files = folder.listFiles();
        assert files != null;
        ImageView[][] imageViews = new ImageView[4][12];
        cardGridPane.setHgap(5);
        cardGridPane.setVgap(5);
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                if (counter < files.length) {
                    String path = "file:\\" + files[counter].getAbsolutePath();
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
        File monstersFolder = new File("src/main/resources/yugioh/assets/cards/Monsters");
        File spellTrapFolder = new File("src/main/resources/yugioh/assets/cards/SpellTrap");
        setCardImages(gridPane, monstersFolder);
        setCardImages(gridPane2, spellTrapFolder);
        GridPane[] gridPanes = new GridPane[]{gridPane, gridPane2};
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
                        cardNameLabel.setLayoutX(190 - (4 * cardName.length()));
                        cardNameLabel.setText("Card name : " + cardName);
                    }
                });
            }
        }
    }

    public void chooseCard(MouseEvent mouseEvent) throws Exception {
        String cardName = cardNameLabel.getText().replace("Card name : ", "");
        Card card = CardInitializer.cardToBuild(cardName);
        assert card != null;
        ImportExportController.toExportCard = card;
        new ImportExportView().start(LoginMenuView.stage);

    }
}
