package yugioh.model.cards.nonMonsterCard;


import yugioh.model.cards.Card;
import yugioh.model.cards.Icon;

public class NonMonsterCard extends Card {

    protected boolean isSpell;
    protected Icon icon;
    protected boolean isLimited;

    public NonMonsterCard(String name, boolean isSpell, Icon icon, String details, boolean isLimited, int price) {
        super(name, price, details);
        this.icon = icon;
        this.isSpell = isSpell;
        this.isLimited = isLimited;
    }

    public void summon() {
        // ??
    }
    public void set() {
        // ??
    }

    public boolean isSpell() {
        return isSpell;
    }

    public Icon getIcon() {
        return icon;
    }

    public boolean isLimited() {
        return isLimited;
    }
    public static boolean stringToIsSpell(String string){
        return string.equals("Spell");
    }
    public static Icon stringToIcon(String string){
        switch (string){
            case "Normal" -> {return Icon.Normal;}
            case "Counter" -> {return Icon.Counter;}
            case "Continuous" -> {return Icon.Continuous;}
            case "Quick-play" -> {return Icon.Quick_play;}
            case "Field" -> {return Icon.Field;}
            case "Equip" -> {return Icon.Equip;}
            case "Ritual" -> {return Icon.Ritual;}
            default -> {return null;}
        }
    }
    public static boolean stringToIsLimited(String string){
        return string.equals("Limited");
    }
}
