package yugioh.model;

public class DeckForDecksMenu {
    private String name;
    private int cardsNumber;

    public DeckForDecksMenu(String name, int cardsNumber) {
        setName(name);
        setCardsNumber(cardsNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardsNumber() {
        return cardsNumber;
    }

    public void setCardsNumber(int cardsNumber) {
        this.cardsNumber = cardsNumber;
    }
}
