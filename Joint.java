package ca.spiralmachines.limbscyberpunk;

public class Joint {
  private boolean free;
  
  private final int x;
  
  private final int y;
  
  public Joint(int paramInt1, int paramInt2) {
    this.x = paramInt1;
    this.y = paramInt2;
    this.free = true;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public boolean isFree() {
    return this.free;
  }
  
  public void setFree(boolean paramBoolean) {
    this.free = paramBoolean;
  }
}
