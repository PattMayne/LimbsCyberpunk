package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import java.util.Hashtable;

public class World {
  static Hashtable<Integer, Dungeon> getAllDungeons(Context paramContext) {
    Hashtable<Object, Object> hashtable = new Hashtable<Object, Object>();
    hashtable.put(Integer.valueOf(0), new TestDungeon(paramContext, 0));
    return (Hashtable)hashtable;
  }
  
  static Dungeon getDungeon(Context paramContext, int paramInt) {
    Hashtable<Integer, Dungeon> hashtable = getAllDungeons(paramContext);
    return hashtable.containsKey(Integer.valueOf(paramInt)) ? hashtable.get(Integer.valueOf(paramInt)) : null;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/World.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */