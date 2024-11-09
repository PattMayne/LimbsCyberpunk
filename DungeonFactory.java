package ca.spiralmachines.limbscyberpunk;

import android.content.Context;

public class DungeonFactory {
  static Dungeon dungeon1(Context paramContext) {
    return new TestDungeon(paramContext, 1);
  }
  
  static Dungeon getDungeon(Context paramContext, int paramInt) {
    return (paramInt == 1) ? dungeon1(paramContext) : null;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/DungeonFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */