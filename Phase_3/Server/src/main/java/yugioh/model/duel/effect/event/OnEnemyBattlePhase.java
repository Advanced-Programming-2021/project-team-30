package yugioh.model.duel.effect.event;


public class OnEnemyBattlePhase extends Event{
    private static OnEnemyBattlePhase instance;
    public static boolean isCalled;

    public static OnEnemyBattlePhase getInstance() {
        return instance;
    }

//    @Override
//    public Event setParams(Dto data) {
//        if(instance == null) return instance = (new OnEnemyBattlePhase());
//        return instance;
//    }

    @Override
    public Object decode(int index, int check){
        return null;
    }
}
