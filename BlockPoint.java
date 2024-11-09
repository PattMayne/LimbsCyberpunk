package ca.spiralmachines.limbscyberpunk;

import java.util.ArrayList;

public class BlockPoint {
  public int absoluteIndexX;
  
  public int absoluteIndexY;
  
  private ArrayList<BlockPoint> adjacentBlocks;
  
  private int blockIndex;
  
  public int hBlocksToPlace;
  
  private boolean isAlive;
  
  boolean isBuilding = false;
  
  public boolean isPath;
  
  public int numberOfAdjacentWalls;
  
  private int rowIndex;
  
  public int vBlocksToPlace;
  
  public int x;
  
  public int y;
  
  public BlockPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.x = paramInt1;
    this.y = paramInt2;
    this.rowIndex = paramInt3;
    this.blockIndex = paramInt4;
    this.isPath = true;
    this.isAlive = true;
    this.numberOfAdjacentWalls = 0;
    this.vBlocksToPlace = 0;
    this.hBlocksToPlace = 0;
    this.absoluteIndexX = 0;
    this.absoluteIndexY = 0;
  }
  
  public BlockPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.x = paramInt1;
    this.y = paramInt2;
    this.absoluteIndexY = paramInt6;
    this.absoluteIndexX = paramInt5;
    this.rowIndex = paramInt3;
    this.blockIndex = paramInt4;
    this.isPath = true;
    this.isAlive = true;
    this.numberOfAdjacentWalls = 0;
    this.vBlocksToPlace = 0;
    this.hBlocksToPlace = 0;
  }
  
  public ArrayList<BlockPoint> getAdjacentBlocks() {
    return this.adjacentBlocks;
  }
  
  public int getBlockIndex() {
    return this.blockIndex;
  }
  
  public int getRowIndex() {
    return this.rowIndex;
  }
  
  public boolean isAlive() {
    return this.isAlive;
  }
  
  public boolean isBuilding() {
    return this.isBuilding;
  }
  
  public boolean isPath() {
    return this.isPath;
  }
  
  public void setAdjacentBlocks(ArrayList<BlockPoint> paramArrayList) {
    this.adjacentBlocks = paramArrayList;
  }
  
  public void setAlive(boolean paramBoolean) {
    this.isAlive = paramBoolean;
  }
  
  public void setBlockIndex(int paramInt) {
    this.blockIndex = paramInt;
  }
  
  public void setBuilding(boolean paramBoolean) {
    this.isBuilding = paramBoolean;
  }
  
  public void setRowIndex(int paramInt) {
    this.rowIndex = paramInt;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/BlockPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */