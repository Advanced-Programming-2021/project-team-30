package yugioh.model.duel.effect.event;

import java.util.ArrayList;
import yugioh.model.duel.effect.Effect;

public abstract class Event {

    public static ArrayList<Event> events;
    protected ArrayList<String>[] data;
    public boolean isCalled;
    protected ArrayList<Effect> registeredEffects = new ArrayList<>();

    public void addEffect(Effect effect){ registeredEffects.add(effect); }

    public abstract Object decode(int index, int check);
//    public abstract Event setParams(Dto data);
//    public boolean checkParams(Dto data) {
//        this.data[1] = data.getData();
//        int size = this.data[1].size();
//        if(size != this.data[0].size()) return false;
//        for(int i = 0; i < size; i++)
//            if(decode(i, 0) != decode(i, 1)) return false;
//        return true;
//    }

    public ArrayList<Effect> getRegisteredEffects(){
        return  registeredEffects;
    }
}
