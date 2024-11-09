package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;

public abstract class Dungeon {
  protected Bitmap bgBitmap;
  
  protected ArrayList<Building> buildings;
  
  protected Context context;
  
  protected int dungeonNumber = -1;
  
  protected Bitmap floorBitmap;
  
  protected Map map;
  
  protected String name;
  
  protected Bitmap wallBitmap1;
  
  protected Bitmap wallBitmap2;
  
  public Dungeon(Context paramContext, int paramInt) {
    this.context = paramContext;
    setDungeonNumber(paramInt);
    this.name = "Dungeon";
    this.buildings = new ArrayList<Building>();
  }
  
  public abstract void createBuildings(int paramInt);
  
  public Bitmap getBgBitmap() {
    return this.bgBitmap;
  }
  
  public ArrayList<Building> getBuildings() {
    return this.buildings;
  }
  
  public int getDungeonNumber() {
    return this.dungeonNumber;
  }
  
  public Bitmap getFloorBitmap() {
    return this.floorBitmap;
  }
  
  public Map getMap() {
    if (this.map == null)
      this.map = new Map(); 
    return this.map;
  }
  
  public String getName() {
    return this.name;
  }
  
  public abstract Character getNpc(int paramInt);
  
  public Bitmap getWallBitmap1() {
    return this.wallBitmap1;
  }
  
  public void setBgBitmap(Bitmap paramBitmap) {
    this.bgBitmap = paramBitmap;
  }
  
  public void setDungeonNumber(int paramInt) {
    this.dungeonNumber = paramInt;
  }
  
  public void setFloorBitmap(Bitmap paramBitmap) {
    this.floorBitmap = paramBitmap;
  }
  
  public void setMap(Map paramMap) {
    this.map = paramMap;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setWallBitmap1(Bitmap paramBitmap) {
    this.wallBitmap1 = paramBitmap;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/Dungeon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */