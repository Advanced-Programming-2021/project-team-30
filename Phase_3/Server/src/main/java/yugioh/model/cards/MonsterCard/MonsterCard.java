package yugioh.model.cards.MonsterCard;

import yugioh.model.cards.Attribute;
import yugioh.model.cards.Card;
import yugioh.model.cards.Type;

import java.util.ArrayList;

public class MonsterCard extends Card {
    protected int health , attackDamage , defenseDamage , level, tributes = 0;
    protected boolean hasEffect;
    protected ArrayList<Type> types;
    protected Attribute attribute ;

    public MonsterCard(String name, int price, String details, boolean hasEffect , int attackDamage , int defenseDamage ,int level ,ArrayList<Type> types , Attribute attribute) {
        super(name, price, details);
        this.hasEffect = hasEffect;
        this.attackDamage = attackDamage;
        this.defenseDamage = defenseDamage;
        this.level = level;
        this.types = types;
        this.attribute = attribute;
    }

    public int showHealth() {
        return this.health ;
    }

    public void changeHealth(int change) { this.health = change; }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getDefenseDamage() {
        return defenseDamage;
    }

    public void addAttackDamage(int damage){ this.attackDamage += damage; }

    public void addDefenseDamage(int damage){ this.defenseDamage += damage; }

    public int getLevel() {
        return level;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getTributes(){ return tributes; }

    public boolean checkTributes() {
        if(level >= 7) return tributes == 2;
        else if(level >= 5) return tributes == 1;
        else return true;
    }

    public boolean isItPossibleToTribute(int totalCards){
        if(level >= 7) return totalCards >= 2;
        else if(level >= 5) return totalCards >= 1;
        else return true;
    }

    public void setAttackDamage(int damage) {
        this.attackDamage = damage;
    }

    public String hasEffectToString(){
        if (hasEffect)
            return "Effect";
        return "Normal";
    }
    public String typesToString(){
        for (Type type : types) {
            switch (type){
                case Warrior -> {return "Warrior";}
                case Effect -> {return "Effect";}
                case Ritual -> {return "Ritual";}
                case Beast -> {return "Beast";}
                case Fiend -> {return "Fiend";}
                case Aqua -> {return "Aqua";}
                case Pyro -> {return "Pyro";}
                case SpellCaster -> {return "Spellcaster";}
                case Thunder -> {return "Thunder";}
                case Dragon -> {return "Dragon";}
                case Machine -> {return "Machine";}
                case Rock -> {return "Rock";}
                case Insect -> {return "Insect";}
                case Cyberse -> {return "Cyberse";}
                case Beast_Warrior -> {return "Beast-Warrior";}
                case Fairy -> {return "Fairy";}
                case Sea_Serpent -> {return "Sea Serpent";}
            }
        }
        return "";
    }
    public String attributeToString(){
        return attribute.toString();
    }

    public static boolean stringToHasEffect(String string){
        return !string.equals("Normal");
    }
    public static ArrayList<Type> stringToTypes(String string){
        ArrayList<Type> types = new ArrayList<>();
        switch (string) {
            case "Warrior" -> types.add(Type.Warrior);
            case "Effect" -> types.add(Type.Effect);
            case "Ritual" -> types.add(Type.Ritual);
            case "Beast" -> types.add(Type.Beast);
            case "Fiend" -> types.add(Type.Fiend);
            case "Aqua" -> types.add(Type.Aqua);
            case "Pyro" -> types.add(Type.Pyro);
            case "Spellcaster" -> types.add(Type.SpellCaster);
            case "Thunder" -> types.add(Type.Thunder);
            case "Dragon" -> types.add(Type.Dragon);
            case "Machine" -> types.add(Type.Machine);
            case "Rock" -> types.add(Type.Rock);
            case "Insect" -> types.add(Type.Insect);
            case "Cyberse" -> types.add(Type.Cyberse);
            case "Beast-Warrior" -> types.add(Type.Beast_Warrior);
            case "Fairy" -> types.add(Type.Fairy);
            case "Sea Serpent" -> types.add(Type.Sea_Serpent);
            default -> types.add(Type.any);
        }
        return types;
    }
    public static Attribute stringToAttribute(String string){
        switch (string){
            case "DARK" -> { return Attribute.DARK; }
            case "EARTH" -> { return Attribute.EARTH; }
            case "FIRE" -> { return Attribute.FIRE; }
            case "LIGHT" -> { return Attribute.LIGHT; }
            case "WATER" -> { return Attribute.WATER; }
            case "WIND" -> { return Attribute.WIND; }
            default -> { return null; }
        }
    }
}