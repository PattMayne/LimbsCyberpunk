package ca.spiralmachines.limbscyberpunk;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import java.util.ArrayList;

public class Limb {
  private int baseHp;
  
  private int characterId;
  
  private int currentHp;
  
  private int damage;
  
  private int id = -1;
  
  private Bitmap image;
  
  private int intelligence;
  
  private boolean isEquipped = false;
  
  private ArrayList<Joint> joints;
  
  private String name = "Limb";
  
  private Rect rect;
  
  private Bitmap thumb;
  
  private int typeId = -1;
  
  private int x;
  
  private int y;
  
  public Limb(int paramInt1, int paramInt2, Bitmap paramBitmap, String paramString, ArrayList<Joint> paramArrayList) {
    this.characterId = paramInt2;
    this.typeId = paramInt1;
    this.name = paramString;
    this.image = paramBitmap;
    this.image = getResizedBitmap(200, 200);
    this.joints = paramArrayList;
    this.x = 0;
    this.y = 0;
    this.currentHp = 30;
    this.baseHp = 30;
    this.damage = 5;
    this.intelligence = 5;
  }
  
  public Limb(int paramInt, Bitmap paramBitmap) {
    this.typeId = paramInt;
    this.image = paramBitmap;
    this.image = getResizedBitmap(200, 200);
    this.thumb = null;
    this.x = 0;
    this.y = 0;
    this.joints = new ArrayList<Joint>();
    this.damage = 5;
    this.intelligence = 5;
  }
  
  public Limb(Bitmap paramBitmap, int paramInt1, int paramInt2, ArrayList<Joint> paramArrayList) {
    this.image = paramBitmap;
    this.thumb = null;
    this.x = paramInt1;
    this.y = paramInt2;
    this.joints = paramArrayList;
    this.damage = 5;
    this.intelligence = 5;
  }
  
  public int getBaseHp() {
    return this.baseHp;
  }
  
  public int getCharacterId() {
    return this.characterId;
  }
  
  public int getCurrentHp() {
    return this.currentHp;
  }
  
  public int getDamage() {
    return this.damage;
  }
  
  public int getId() {
    return this.id;
  }
  
  public Bitmap getImage() {
    return this.image;
  }
  
  public int getIntelligence() {
    return this.intelligence;
  }
  
  public ArrayList<Joint> getJoints() {
    return this.joints;
  }
  
  public String getName() {
    return this.name;
  }
  
  public Rect getRect() {
    return this.rect;
  }
  
  public Bitmap getResizedBitmap(int paramInt1, int paramInt2) {
    int i = this.image.getWidth();
    int j = this.image.getHeight();
    float f2 = paramInt1 / i;
    float f1 = paramInt2 / j;
    Matrix matrix = new Matrix();
    matrix.postScale(f2, f1);
    return Bitmap.createBitmap(this.image, 0, 0, i, j, matrix, false);
  }
  
  public Bitmap getThumb() {
    return this.thumb;
  }
  
  public int getTypeId() {
    return this.typeId;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public boolean isEquipped() {
    return this.isEquipped;
  }
  
  public void setBaseHp(int paramInt) {
    this.baseHp = paramInt;
  }
  
  public void setCharacter(int paramInt) {
    this.characterId = paramInt;
  }
  
  public void setCurrentHp(int paramInt) {
    int i = this.baseHp;
    if (paramInt > i * 2) {
      this.currentHp = i * 2;
    } else {
      this.currentHp = paramInt;
    } 
  }
  
  public void setDamage(int paramInt) {
    this.damage = paramInt;
  }
  
  public void setEquipped(boolean paramBoolean) {
    this.isEquipped = paramBoolean;
  }
  
  public void setId(int paramInt) {
    this.id = paramInt;
  }
  
  public void setImage(Bitmap paramBitmap) {
    this.image = paramBitmap;
  }
  
  public void setIntelligence(int paramInt) {
    this.intelligence = paramInt;
  }
  
  public void setJoints(ArrayList<Joint> paramArrayList) {
    this.joints = paramArrayList;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setRect(Rect paramRect) {
    this.rect = paramRect;
  }
  
  public void setThumb(int paramInt1, int paramInt2) {
    this.thumb = getResizedBitmap(paramInt1, paramInt2);
  }
  
  public void setTypeId(int paramInt) {
    this.typeId = paramInt;
  }
  
  public void setX(int paramInt) {
    this.x = paramInt;
  }
  
  public void setXY(int paramInt1, int paramInt2) {
    this.x = paramInt1;
    this.y = paramInt2;
  }
  
  public void setY(int paramInt) {
    this.y = paramInt;
  }
}
