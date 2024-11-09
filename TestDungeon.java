package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

public class TestDungeon extends Dungeon {
  Context context;
  
  public TestDungeon(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.context = paramContext;
    this.name = "Test Dungeon";
    setDungeonNumber(paramInt);
    this.bgBitmap = BitmapFactory.decodeResource(paramContext.getResources(), 2131165275);
    this.floorBitmap = BitmapFactory.decodeResource(paramContext.getResources(), 2131165367);
    this.wallBitmap1 = BitmapFactory.decodeResource(paramContext.getResources(), 2131165368);
    this.wallBitmap2 = BitmapFactory.decodeResource(paramContext.getResources(), 2131165369);
  }
  
  public Building building000(int paramInt) {
    Map map = new Map();
    for (byte b = 0; b < 3; b++) {
      Row row = new Row();
      for (byte b1 = 0; b1 < 4; b1++) {
        BlockPoint blockPoint = new BlockPoint(100000, 100000, b, b1, -7 + b1, -7 + b);
        blockPoint.setBuilding(true);
        row.blockPoints.add(blockPoint);
      } 
      map.rows.add(row);
    } 
    return new Building(map, paramInt);
  }
  
  public void createBuildings(int paramInt) {
    this.buildings = new ArrayList<Building>();
    this.buildings.add(building000(paramInt));
  }
  
  public Character getNpc(int paramInt) {
    return (paramInt == 0) ? npc000() : ((paramInt == 1) ? npc001() : ((paramInt == 10) ? npc010() : null));
  }
  
  public Character npc000() {
    ArrayList<Limb> arrayList = new ArrayList();
    Limb limb1 = LimbFactory.getLimb(this.context, 0, 2);
    Limb limb3 = LimbFactory.getLimb(this.context, 2, 2);
    Limb limb2 = LimbFactory.getLimb(this.context, 4, 2);
    limb1.setXY(668, 668);
    limb3.setXY(674, 813);
    limb2.setXY(672, 770);
    limb1.setEquipped(true);
    limb3.setEquipped(true);
    limb2.setEquipped(true);
    arrayList.add(limb1);
    arrayList.add(limb2);
    arrayList.add(limb3);
    return new Character(2, "NPC Prime Lady", arrayList);
  }
  
  public Character npc001() {
    ArrayList<Limb> arrayList = new ArrayList();
    Limb limb1 = LimbFactory.getLimb(this.context, 4, 2);
    Limb limb2 = LimbFactory.getLimb(this.context, 1, 2);
    Limb limb3 = LimbFactory.getLimb(this.context, 3, 2);
    limb1.setXY(698, 638);
    limb2.setXY(668, 668);
    limb3.setXY(692, 496);
    limb1.setEquipped(true);
    limb2.setEquipped(true);
    limb3.setEquipped(true);
    arrayList.add(limb1);
    arrayList.add(limb2);
    arrayList.add(limb3);
    return new Character(2, "NPC Doggo", arrayList);
  }
  
  public Character npc010() {
    ArrayList<Limb> arrayList = new ArrayList();
    Limb limb3 = LimbFactory.getLimb(this.context, 4, 3);
    Limb limb2 = LimbFactory.getLimb(this.context, 2, 3);
    Limb limb1 = LimbFactory.getLimb(this.context, 3, 3);
    limb3.setXY(698, 638);
    limb2.setXY(668, 668);
    limb1.setXY(692, 496);
    limb3.setEquipped(true);
    limb2.setEquipped(true);
    limb1.setEquipped(true);
    arrayList.add(limb3);
    arrayList.add(limb2);
    arrayList.add(limb1);
    Character character = new Character(10, "Stationary Quest NPC", arrayList);
    character.setLocation(10, -4, -4);
    character.setFreeRoam(false);
    character.setQuestCharacter(true);
    return character;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/TestDungeon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */