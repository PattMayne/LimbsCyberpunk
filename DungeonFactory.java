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
