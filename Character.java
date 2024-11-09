package ca.spiralmachines.limbscyberpunk;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Character {
  static final int MaxEquippedLimbs = 9;
  
  static final int MaxSpareLimbs = 12;
  
  private int absoluteIndexX;
  
  private int absoluteIndexY;
  
  private int dungeonId;
  
  private ArrayList<Limb> equippedLimbs = new ArrayList<Limb>();
  
  private Paint errorPaint;
  
  private boolean freeRoam = true;
  
  private int height;
  
  private int id;
  
  private Bitmap image;
  
  private boolean isAlive = true;
  
  private boolean isHostile = false;
  
  private boolean isNpc = true;
  
  private String name;
  
  private boolean questCharacter = false;
  
  private Random random;
  
  private ArrayList<Limb> spareLimbs = new ArrayList<Limb>();
  
  private int width;
  
  public Character(int paramInt, String paramString, ArrayList<Limb> paramArrayList) {
    this.name = paramString;
    this.id = paramInt;
    Paint paint = new Paint();
    this.errorPaint = paint;
    paint.setColor(-65281);
    this.random = new Random();
    assignLimbs(paramArrayList);
    baseCoordinatesOnZero();
    setWidthAndHeight();
    setAvatarImage(300, 300);
  }
  
  private void assignLimbs(ArrayList<Limb> paramArrayList) {
    for (Limb limb : paramArrayList) {
      if (limb.isEquipped()) {
        this.equippedLimbs.add(limb);
        continue;
      } 
      this.spareLimbs.add(limb);
    } 
  }
  
  static Bitmap getResizedBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
    int j = paramBitmap.getWidth();
    int i = paramBitmap.getHeight();
    float f1 = paramInt1 / j;
    float f2 = paramInt2 / i;
    Matrix matrix = new Matrix();
    matrix.postScale(f1, f2);
    return Bitmap.createBitmap(paramBitmap, 0, 0, j, i, matrix, false);
  }
  
  public void baseCoordinatesOnZero() {
    ArrayList<Limb> arrayList = this.equippedLimbs;
    if (arrayList == null)
      return; 
    if (arrayList.size() < 1)
      return; 
    int j = 999999999;
    int i = 999999999;
    for (Limb limb : this.equippedLimbs) {
      int k = j;
      if (limb.getX() < j)
        k = limb.getX(); 
      int m = i;
      if (limb.getY() < i)
        m = limb.getY(); 
      j = k;
      i = m;
    } 
    for (Limb limb : this.equippedLimbs) {
      limb.setX(limb.getX() - j);
      limb.setY(limb.getY() - i);
    } 
  }
  
  public int getAbsoluteIndexX() {
    return this.absoluteIndexX;
  }
  
  public int getAbsoluteIndexY() {
    return this.absoluteIndexY;
  }
  
  public ArrayList<Limb> getAllLimbs() {
    ArrayList<Limb> arrayList = new ArrayList();
    Iterator<Limb> iterator = this.equippedLimbs.iterator();
    while (iterator.hasNext())
      arrayList.add(iterator.next()); 
    iterator = this.spareLimbs.iterator();
    while (iterator.hasNext())
      arrayList.add(iterator.next()); 
    return arrayList;
  }
  
  public int getDamage() {
    ArrayList<Limb> arrayList = this.equippedLimbs;
    if (arrayList == null)
      return 0; 
    if (arrayList.size() < 1)
      return 0; 
    int i = 0;
    Iterator<Limb> iterator = this.equippedLimbs.iterator();
    while (iterator.hasNext())
      i += ((Limb)iterator.next()).getDamage(); 
    if (0.0F < 1.0F);
    return (int)(((i / 3 + i) / this.equippedLimbs.size()) * (this.random.nextInt(4) + 19) / (this.random.nextInt(4) + 19));
  }
  
  public int getDungeonId() {
    return this.dungeonId;
  }
  
  public ArrayList<Limb> getEquippedLimbs() {
    return this.equippedLimbs;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public int getId() {
    return this.id;
  }
  
  public Bitmap getImage() {
    if (this.image == null)
      setAvatarImage(300, 300); 
    return this.image;
  }
  
  public int getIntelligence() {
    ArrayList<Limb> arrayList = this.equippedLimbs;
    if (arrayList == null)
      return 0; 
    if (arrayList.size() < 1)
      return 0; 
    float f1 = 0.0F;
    Iterator<Limb> iterator = this.equippedLimbs.iterator();
    while (iterator.hasNext())
      f1 += ((Limb)iterator.next()).getIntelligence(); 
    float f2 = f1;
    if (f1 < 1.0F)
      f2 = 1.0F; 
    return (int)((f2 / 3.0F + f2) / this.equippedLimbs.size() * (this.random.nextInt(4) + 19) / (this.random.nextInt(4) + 19));
  }
  
  public String getName() {
    return this.name;
  }
  
  public ArrayList<Limb> getSpareLimbs() {
    return this.spareLimbs;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public boolean isAlive() {
    return this.isAlive;
  }
  
  public boolean isFreeRoam() {
    return this.freeRoam;
  }
  
  public boolean isHostile() {
    return this.isHostile;
  }
  
  public boolean isNpc() {
    return this.isNpc;
  }
  
  public boolean isQuestCharacter() {
    return this.questCharacter;
  }
  
  public void setAlive(boolean paramBoolean) {
    this.isAlive = paramBoolean;
  }
  
  public void setAvatarImage(int paramInt1, int paramInt2) {
    setWidthAndHeight();
    if (this.equippedLimbs.size() < 1) {
      Bitmap bitmap1 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      (new Canvas(bitmap1)).drawRect(0.0F, 0.0F, paramInt1, paramInt2, this.errorPaint);
      this.image = bitmap1;
      return;
    } 
    int j = this.width;
    int i = this.height;
    if (j > i) {
      i = j;
    } else {
      j = i;
    } 
    Bitmap bitmap = Bitmap.createBitmap(j, i, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    for (Limb limb : this.equippedLimbs)
      canvas.drawBitmap(limb.getImage(), limb.getX(), limb.getY(), null); 
    this.image = getResizedBitmap(bitmap, paramInt1, paramInt2);
  }
  
  public void setEquippedLimbs(ArrayList<Limb> paramArrayList) {
    this.equippedLimbs = paramArrayList;
  }
  
  public void setFreeRoam(boolean paramBoolean) {
    this.freeRoam = paramBoolean;
  }
  
  public void setHostile(boolean paramBoolean) {
    this.isHostile = paramBoolean;
  }
  
  public void setImage(Bitmap paramBitmap) {
    this.image = paramBitmap;
  }
  
  public void setLimbs(ArrayList<Limb> paramArrayList) {
    assignLimbs(paramArrayList);
    setWidthAndHeight();
  }
  
  public void setLocation(int paramInt1, int paramInt2, int paramInt3) {
    this.dungeonId = paramInt1;
    this.absoluteIndexX = paramInt2;
    this.absoluteIndexY = paramInt3;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setNpc(boolean paramBoolean) {
    this.isNpc = paramBoolean;
  }
  
  public void setQuestCharacter(boolean paramBoolean) {
    this.questCharacter = paramBoolean;
  }
  
  public void setSpareLimbs(ArrayList<Limb> paramArrayList) {
    this.spareLimbs = paramArrayList;
  }
  
  public void setWidthAndHeight() {
    ArrayList<Limb> arrayList = this.equippedLimbs;
    if (arrayList == null) {
      this.width = 1;
      this.height = 1;
      return;
    } 
    if (arrayList.size() < 1) {
      this.width = 1;
      this.height = 1;
      return;
    } 
    int m = 999999999;
    int k = 999999999;
    int j = 0;
    int i = 0;
    for (Limb limb : this.equippedLimbs) {
      int n = m;
      if (limb.getX() < m)
        n = limb.getX(); 
      int i1 = k;
      if (limb.getY() < k)
        i1 = limb.getY(); 
      int i2 = j;
      if (limb.getX() > j)
        i2 = limb.getX() + limb.getImage().getWidth(); 
      int i3 = i;
      if (limb.getY() > i)
        i3 = limb.getY() + limb.getImage().getHeight(); 
      m = n;
      k = i1;
      j = i2;
      i = i3;
    } 
    j -= m;
    this.width = j;
    i -= k;
    this.height = i;
    if (j < 1)
      this.width = 1; 
    if (i < 1)
      this.height = 1; 
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/Character.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */