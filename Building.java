package ca.spiralmachines.limbscyberpunk;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

public class Building {
  private Bitmap bitmap;
  
  private Color color;
  
  public Map map;
  
  public Rect rect;
  
  private int squareLength;
  
  public Building(Map paramMap, int paramInt) {
    this.map = paramMap;
    this.squareLength = paramInt;
    this.rect = new Rect(0, 0, 1, 1);
    updateRect();
  }
  
  public void updateRect() {
    byte b4 = 0;
    byte b2 = 0;
    byte b3 = 1;
    byte b1 = 1;
    int m = b4;
    int k = b2;
    int j = b3;
    int i = b1;
    if (this.map.rows.size() > 0) {
      m = b4;
      k = b2;
      j = b3;
      i = b1;
      if (((Row)this.map.rows.get(0)).blockPoints.size() > 0) {
        BlockPoint blockPoint2 = ((Row)this.map.rows.get(0)).blockPoints.get(0);
        BlockPoint blockPoint1 = ((Row)this.map.rows.get(this.map.rows.size() - 1)).blockPoints.get(((Row)this.map.rows.get(0)).blockPoints.size() - 1);
        m = blockPoint2.x;
        k = blockPoint2.y;
        j = blockPoint1.x + this.squareLength;
        i = blockPoint1.y + this.squareLength;
      } 
    } 
    this.rect.left = m;
    this.rect.right = j;
    this.rect.top = k;
    this.rect.bottom = i;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/Building.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */