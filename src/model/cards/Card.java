package model.cards;
import java.lang.String;
public class Card {
    protected boolean isInGraveyard = false ;
    protected Type cardType ;
    protected Attribute status ;
    protected int price , speed , cardNumber , quantity ;
    protected String name , details ;
    public Card(String name , int price ,String details) {
        setDetails(details);
        setName(name);
        setPrice(price);
    }
    private void setName(String name) {
        this.name = name;
    }

    private void setDetails(String details) {
        this.details = details;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }
    public String getName() {
        return name ;
    }
    public boolean isInGraveyard() {
        return isInGraveyard ;
    }
    public Attribute getStatus() {
        return status;
    }
    public int getQuantity() {
        return quantity;
    }
    public Type getCardType() {
        return cardType;
    }
    public void addQuantity() {
        quantity++ ;
    }
}
