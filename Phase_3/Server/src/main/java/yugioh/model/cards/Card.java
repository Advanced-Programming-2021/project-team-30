package yugioh.model.cards;

import yugioh.model.duel.Ground;

public class Card {
    private String name;
    private int price;
    private String description;
    private int location;
    private Ground ground;


    public Card(String name, int price, String description) {
        setName(name);
        setPrice(price);
        setDescription(description);
    }

    public Card(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return price == card.price && name.equals(card.name) && description.equals(card.description);
    }

    public void setLocation(int location){ this.location = location; }

    public void setGround(Ground ground){ this.ground = ground; }

    public int getLocation(){ return location; }

    public Ground getGround(){ return ground; }
}
