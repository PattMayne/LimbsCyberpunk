package ca.spiralmachines.limbscyberpunk;

import java.util.Hashtable;

public abstract class Quest {
  private int destinationX;
  
  private int destinationY;
  
  private Dungeon dungeon;
  
  private Character giver;
  
  private Hashtable<String, String> messages = new Hashtable<String, String>();
  
  private int rewardTypeId;
  
  private int typeId;
  
  public Quest(int paramInt1, Dungeon paramDungeon, int paramInt2, int paramInt3, Character paramCharacter, int paramInt4) {
    this.typeId = paramInt1;
    this.dungeon = paramDungeon;
    this.destinationX = paramInt2;
    this.destinationY = paramInt3;
    this.giver = paramCharacter;
    this.rewardTypeId = paramInt4;
    createMessages();
  }
  
  private void createMessages() {
    setStartMessage("You have accepted a quest.");
    setWinMessage("You won this quest.");
    setFailureMessage("You failed this quest. Good job.");
  }
  
  public int getDestinationX() {
    return this.destinationX;
  }
  
  public int getDestinationY() {
    return this.destinationY;
  }
  
  public Dungeon getDungeon() {
    return this.dungeon;
  }
  
  public Character getGiver() {
    return this.giver;
  }
  
  public Hashtable<String, String> getMessages() {
    return this.messages;
  }
  
  public int getRewardTypeId() {
    return this.rewardTypeId;
  }
  
  public int getTypeId() {
    return this.typeId;
  }
  
  public void setDestinationX(int paramInt) {
    this.destinationX = paramInt;
  }
  
  public void setDestinationY(int paramInt) {
    this.destinationY = paramInt;
  }
  
  public void setDungeon(Dungeon paramDungeon) {
    this.dungeon = paramDungeon;
  }
  
  public void setFailureMessage(String paramString) {
    this.messages.put("failureMessage", paramString);
  }
  
  public void setGiver(Character paramCharacter) {
    this.giver = paramCharacter;
  }
  
  public void setMessages(Hashtable<String, String> paramHashtable) {
    this.messages = paramHashtable;
  }
  
  public void setRewardTypeId(int paramInt) {
    this.rewardTypeId = paramInt;
  }
  
  public void setStartMessage(String paramString) {
    this.messages.put("startMessage", paramString);
  }
  
  public void setTypeId(int paramInt) {
    this.typeId = paramInt;
  }
  
  public void setWinMessage(String paramString) {
    this.messages.put("winMessage", paramString);
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/Quest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */