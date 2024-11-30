package ca.spiralmachines.limbscyberpunk;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Suit {
  private int dungeonNumber;
  
  private Hashtable<Integer, Limb> limbs = new Hashtable<Integer, Limb>();
  
  private String name;
  
  private int suitNumber;
  
  public Suit(ArrayList<Limb> paramArrayList, int paramInt1, int paramInt2) {
    for (byte b = 0; b < paramArrayList.size(); b++)
      this.limbs.put(Integer.valueOf(b), paramArrayList.get(b)); 
    this.name = "custom";
    this.dungeonNumber = paramInt1;
    this.suitNumber = paramInt2;
  }
  
  public Suit(Hashtable<Integer, Limb> paramHashtable) {
    this.name = "custom";
    this.dungeonNumber = -1;
  }
  
  public Suit(Hashtable<Integer, Limb> paramHashtable, String paramString, int paramInt) {
    this.name = paramString;
    this.dungeonNumber = paramInt;
  }
  
  public int getDungeonNumber() {
    return this.dungeonNumber;
  }
  
  public ArrayList<Limb> getLimbsArrayList() {
    ArrayList<Limb> arrayList = new ArrayList();
    Iterator<Limb> iterator = this.limbs.values().iterator();
    while (iterator.hasNext())
      arrayList.add(iterator.next()); 
    return arrayList;
  }
  
  public Hashtable<Integer, Limb> getLimbsTable() {
    return this.limbs;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setDungeonNumber(int paramInt) {
    this.dungeonNumber = paramInt;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
}
