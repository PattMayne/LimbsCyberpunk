package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

public class LimbFactory {
  static ArrayList<Limb> getAllLimbs(Context paramContext, int paramInt) {
    ArrayList<Limb> arrayList = new ArrayList();
    arrayList.add(limb000(paramContext, paramInt));
    arrayList.add(limb001(paramContext, paramInt));
    return arrayList;
  }
  
  static Limb getLimb(Context paramContext, int paramInt1, int paramInt2) {
    return (paramInt1 == 0) ? limb000(paramContext, paramInt2) : ((paramInt1 == 1) ? limb001(paramContext, paramInt2) : ((paramInt1 == 2) ? limb002(paramContext, paramInt2) : ((paramInt1 == 3) ? limb003(paramContext, paramInt2) : ((paramInt1 == 4) ? limb004(paramContext, paramInt2) : null))));
  }
  
  static ArrayList<Limb> getTestLimbs(Context paramContext, int paramInt) {
    ArrayList<Limb> arrayList = new ArrayList();
    arrayList.add(limb000(paramContext, paramInt));
    arrayList.add(limb001(paramContext, paramInt));
    arrayList.add(limb002(paramContext, paramInt));
    arrayList.add(limb003(paramContext, paramInt));
    arrayList.add(limb004(paramContext, paramInt));
    return arrayList;
  }
  
  static Limb limb000(Context paramContext, int paramInt) {
    ArrayList<Joint> arrayList = new ArrayList();
    arrayList.add(new Joint(104, 140));
    Limb limb = new Limb(0, paramInt, BitmapFactory.decodeResource(paramContext.getResources(), 2131165302), "Steam Head", arrayList);
    limb.setIntelligence(10);
    limb.setBaseHp(5);
    limb.setCurrentHp(5);
    limb.setDamage(5);
    return limb;
  }
  
  static Limb limb001(Context paramContext, int paramInt) {
    ArrayList<Joint> arrayList = new ArrayList();
    arrayList.add(new Joint(129, 124));
    Limb limb = new Limb(1, paramInt, BitmapFactory.decodeResource(paramContext.getResources(), 2131165303), "Bike Wheel", arrayList);
    limb.setIntelligence(10);
    limb.setBaseHp(7);
    limb.setCurrentHp(7);
    limb.setDamage(3);
    return limb;
  }
  
  static Limb limb002(Context paramContext, int paramInt) {
    ArrayList<Joint> arrayList = new ArrayList();
    arrayList.add(new Joint(97, 111));
    Limb limb = new Limb(2, paramInt, BitmapFactory.decodeResource(paramContext.getResources(), 2131165304), "Car Wheel", arrayList);
    limb.setIntelligence(4);
    limb.setBaseHp(7);
    limb.setCurrentHp(7);
    limb.setDamage(9);
    return limb;
  }
  
  static Limb limb003(Context paramContext, int paramInt) {
    ArrayList<Joint> arrayList = new ArrayList();
    arrayList.add(new Joint(106, 180));
    Limb limb = new Limb(3, paramInt, BitmapFactory.decodeResource(paramContext.getResources(), 2131165305), "Dog Head", arrayList);
    limb.setIntelligence(6);
    limb.setBaseHp(7);
    limb.setCurrentHp(7);
    limb.setDamage(7);
    return limb;
  }
  
  static Limb limb004(Context paramContext, int paramInt) {
    ArrayList<Joint> arrayList = new ArrayList();
    arrayList.add(new Joint(99, 154));
    arrayList.add(new Joint(100, 38));
    Limb limb = new Limb(4, paramInt, BitmapFactory.decodeResource(paramContext.getResources(), 2131165306), "Vertical Pipe", arrayList);
    limb.setIntelligence(2);
    limb.setBaseHp(9);
    limb.setCurrentHp(9);
    limb.setDamage(9);
    return limb;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/LimbFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */