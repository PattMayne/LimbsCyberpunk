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


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/Joint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */