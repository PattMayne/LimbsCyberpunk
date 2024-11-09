package ca.spiralmachines.limbscyberpunk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseHelper extends SQLiteOpenHelper {
  public static final String CHARACTER_TABLE = "CHARACTER_TABLE";
  
  public static final String COLUMN_CHARACTER_ABSOLUTE_X = "ABSOLUTE_X";
  
  public static final String COLUMN_CHARACTER_ABSOLUTE_Y = "ABSOLUTE_Y";
  
  public static final String COLUMN_CHARACTER_ALIVE = "ALIVE";
  
  public static final String COLUMN_CHARACTER_DUNGEON_NUMBER = "DUNGEON_NUMBER";
  
  public static final String COLUMN_CHARACTER_ID = "ID";
  
  public static final String COLUMN_CHARACTER_NAME = "NAME";
  
  public static final String COLUMN_LIMB_CHARACTER_ID = "CHARACTER_ID";
  
  public static final String COLUMN_LIMB_DAMAGE = "DAMAGE";
  
  public static final String COLUMN_LIMB_EQUIPPED = "EQUIPPED";
  
  public static final String COLUMN_LIMB_HEALTH = "HEALTH";
  
  public static final String COLUMN_LIMB_ID = "ID";
  
  public static final String COLUMN_LIMB_INTELLIGENCE = "INTELLIGENCE";
  
  public static final String COLUMN_LIMB_TYPE_NUMBER = "LIMB_TYPE_NUMBER";
  
  public static final String COLUMN_LIMB_X = "X";
  
  public static final String COLUMN_LIMB_Y = "Y";
  
  public static final String COLUMN_PLAYER_AUDIO_PREF = "AUDIO";
  
  public static final String COLUMN_PLAYER_ID = "ID";
  
  public static final String LIMB_TABLE = "LIMB_TABLE";
  
  public static final String PLAYER_TABLE = "PLAYER_TABLE";
  
  private Context context;
  
  public DatabaseHelper(Context paramContext) {
    super(paramContext, "limbs_cyberpunk_db", null, 30);
    this.context = paramContext;
  }
  
  private void createCharacterTable(SQLiteDatabase paramSQLiteDatabase) {
    paramSQLiteDatabase.execSQL("CREATE TABLE CHARACTER_TABLE (ID INTEGER PRIMARY KEY, ALIVE BOOLEAN, NAME TEXT, DUNGEON_NUMBER INTEGER, ABSOLUTE_X INTEGER, ABSOLUTE_Y INTEGER)");
  }
  
  private void createLimbTable(SQLiteDatabase paramSQLiteDatabase) {
    paramSQLiteDatabase.execSQL("CREATE TABLE LIMB_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, X INTEGER, Y INTEGER, HEALTH INTEGER, EQUIPPED BOOL, LIMB_TYPE_NUMBER INTEGER, CHARACTER_ID INTEGER, DAMAGE INTEGER, INTELLIGENCE INTEGER)");
  }
  
  private void createPlayerCharacterEntry(SQLiteDatabase paramSQLiteDatabase) {
    ContentValues contentValues = new ContentValues();
    Integer integer = Integer.valueOf(1);
    contentValues.put("ID", integer);
    contentValues.put("ALIVE", integer);
    contentValues.put("NAME", "Ruxto");
    int i = (int)paramSQLiteDatabase.insert("CHARACTER_TABLE", null, contentValues);
    contentValues = new ContentValues();
    contentValues.put("ID", Integer.valueOf(2));
    contentValues.put("ALIVE", integer);
    contentValues.put("NAME", "NPC");
    i = (int)paramSQLiteDatabase.insert("CHARACTER_TABLE", null, contentValues);
  }
  
  private void createPlayerEntry(SQLiteDatabase paramSQLiteDatabase) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("ID", Integer.valueOf(1));
    contentValues.put("AUDIO", Integer.valueOf(0));
    int i = (int)paramSQLiteDatabase.insert("PLAYER_TABLE", null, contentValues);
  }
  
  private void createPlayerTable(SQLiteDatabase paramSQLiteDatabase) {
    paramSQLiteDatabase.execSQL("CREATE TABLE PLAYER_TABLE (ID INTEGER PRIMARY KEY, AUDIO BOOLEAN)");
  }
  
  private void createTables(SQLiteDatabase paramSQLiteDatabase) {
    createLimbTable(paramSQLiteDatabase);
    createPlayerTable(paramSQLiteDatabase);
    createCharacterTable(paramSQLiteDatabase);
    createPlayerEntry(paramSQLiteDatabase);
    createPlayerCharacterEntry(paramSQLiteDatabase);
  }
  
  private void deleteAllNpcLimbs(SQLiteDatabase paramSQLiteDatabase) {
    long l = paramSQLiteDatabase.delete("LIMB_TABLE", "CHARACTER_ID=?", new String[] { String.valueOf(2) });
  }
  
  private void resetEverything(SQLiteDatabase paramSQLiteDatabase) {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS LIMB_TABLE");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS PLAYER_TABLE");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS CHARACTER_TABLE");
    createTables(paramSQLiteDatabase);
  }
  
  public boolean deleteOneLimb(int paramInt) {
    return (paramInt < 1) ? true : (!(getWritableDatabase().delete("LIMB_TABLE", "id=?", new String[] { String.valueOf(paramInt) }) == -1L));
  }
  
  public ArrayList<Limb> getAllLimbs() {
    return getAllLimbs(getReadableDatabase());
  }
  
  public ArrayList<Limb> getAllLimbs(SQLiteDatabase paramSQLiteDatabase) {
    ArrayList<Limb> arrayList = new ArrayList();
    Cursor cursor = null;
    if (paramSQLiteDatabase != null)
      cursor = paramSQLiteDatabase.rawQuery("SELECT * FROM LIMB_TABLE", null); 
    if (cursor.getCount() != 0)
      while (cursor.moveToNext()) {
        boolean bool = false;
        int i3 = cursor.getInt(0);
        int i = cursor.getInt(1);
        int j = cursor.getInt(2);
        int i2 = cursor.getInt(3);
        if (cursor.getInt(4) == 1)
          bool = true; 
        int m = cursor.getInt(5);
        int n = cursor.getInt(6);
        int i1 = cursor.getInt(7);
        int k = cursor.getInt(8);
        Limb limb = LimbFactory.getLimb(this.context, m, n);
        limb.setId(i3);
        limb.setEquipped(bool);
        limb.setX(i);
        limb.setY(j);
        limb.setCurrentHp(i2);
        limb.setDamage(i1);
        limb.setIntelligence(k);
        arrayList.add(limb);
      }  
    cursor.close();
    paramSQLiteDatabase.close();
    return arrayList;
  }
  
  public boolean getAudioPref() {
    return getAudioPref(getReadableDatabase());
  }
  
  public boolean getAudioPref(SQLiteDatabase paramSQLiteDatabase) {
    boolean bool = true;
    Cursor cursor = paramSQLiteDatabase.rawQuery("SELECT * FROM PLAYER_TABLE WHERE ID == 1", null);
    if (cursor.moveToNext())
      do {
        bool = true;
        if (cursor.getInt(1) == 1)
          continue; 
        bool = false;
      } while (cursor.moveToNext()); 
    cursor.close();
    return bool;
  }
  
  public Character getCharacter(int paramInt) {
    return getCharacter(paramInt, getReadableDatabase());
  }
  
  public Character getCharacter(int paramInt, SQLiteDatabase paramSQLiteDatabase) {
    Character character;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("SELECT * FROM CHARACTER_TABLE WHERE ID == ");
    stringBuilder.append(paramInt);
    String str = stringBuilder.toString();
    stringBuilder = null;
    Cursor cursor = paramSQLiteDatabase.rawQuery(str, null);
    if (cursor.moveToNext())
      do {
        boolean bool = true;
        if (cursor.getInt(1) != 1)
          bool = false; 
        String str1 = cursor.getString(2);
        int i = cursor.getInt(3);
        int j = cursor.getInt(4);
        int k = cursor.getInt(5);
        character = new Character(paramInt, str1, getCharacterLimbs(paramSQLiteDatabase, paramInt));
        character.setAlive(bool);
        character.setLocation(i, j, k);
      } while (cursor.moveToNext()); 
    cursor.close();
    return character;
  }
  
  public ArrayList<Limb> getCharacterLimbs(int paramInt) {
    return getCharacterLimbs(getReadableDatabase(), paramInt);
  }
  
  public ArrayList<Limb> getCharacterLimbs(SQLiteDatabase paramSQLiteDatabase, int paramInt) {
    Cursor cursor;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("SELECT * FROM LIMB_TABLE WHERE CHARACTER_ID == ");
    stringBuilder.append(String.valueOf(paramInt));
    String str = stringBuilder.toString();
    stringBuilder = null;
    if (paramSQLiteDatabase != null)
      cursor = paramSQLiteDatabase.rawQuery(str, null); 
    ArrayList<Limb> arrayList = new ArrayList();
    if (cursor.getCount() != 0)
      while (cursor.moveToNext()) {
        boolean bool = false;
        int i2 = cursor.getInt(0);
        int n = cursor.getInt(1);
        int m = cursor.getInt(2);
        int i1 = cursor.getInt(3);
        if (cursor.getInt(4) == 1)
          bool = true; 
        int i = cursor.getInt(5);
        int j = cursor.getInt(7);
        int k = cursor.getInt(8);
        if (i < 0)
          continue; 
        Limb limb = LimbFactory.getLimb(this.context, i, paramInt);
        limb.setCurrentHp(i1);
        limb.setX(n);
        limb.setY(m);
        limb.setEquipped(bool);
        limb.setId(i2);
        limb.setDamage(j);
        limb.setIntelligence(k);
        arrayList.add(limb);
      }  
    cursor.close();
    paramSQLiteDatabase.close();
    return arrayList;
  }
  
  public boolean npcIsAlive() {
    Character character = getCharacter(2);
    return (character != null && character.getEquippedLimbs().size() < 1);
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
    createTables(paramSQLiteDatabase);
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    resetEverything(paramSQLiteDatabase);
  }
  
  public void saveCharacter(Character paramCharacter) {
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("ALIVE", Boolean.valueOf(paramCharacter.isAlive()));
    contentValues.put("NAME", paramCharacter.getName());
    contentValues.put("DUNGEON_NUMBER", Integer.valueOf(paramCharacter.getDungeonId()));
    contentValues.put("ABSOLUTE_X", Integer.valueOf(paramCharacter.getAbsoluteIndexX()));
    contentValues.put("ABSOLUTE_Y", Integer.valueOf(paramCharacter.getAbsoluteIndexY()));
    long l = sQLiteDatabase.update("CHARACTER_TABLE", contentValues, "ID=?", new String[] { String.valueOf(paramCharacter.getId()) });
    for (Limb limb : paramCharacter.getAllLimbs()) {
      if (limb != null)
        saveLimb(limb); 
    } 
  }
  
  public void saveCharacterLocation(Character paramCharacter) {
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("DUNGEON_NUMBER", Integer.valueOf(paramCharacter.getDungeonId()));
    contentValues.put("ABSOLUTE_X", Integer.valueOf(paramCharacter.getAbsoluteIndexX()));
    contentValues.put("ABSOLUTE_Y", Integer.valueOf(paramCharacter.getAbsoluteIndexY()));
    long l = sQLiteDatabase.update("CHARACTER_TABLE", contentValues, "ID=?", new String[] { String.valueOf(paramCharacter.getId()) });
  }
  
  public boolean saveLimb(Limb paramLimb) {
    boolean bool2 = false;
    boolean bool1 = false;
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("X", Integer.valueOf(paramLimb.getX()));
    contentValues.put("Y", Integer.valueOf(paramLimb.getY()));
    contentValues.put("HEALTH", Integer.valueOf(paramLimb.getCurrentHp()));
    contentValues.put("EQUIPPED", Boolean.valueOf(paramLimb.isEquipped()));
    contentValues.put("LIMB_TYPE_NUMBER", Integer.valueOf(paramLimb.getTypeId()));
    contentValues.put("CHARACTER_ID", Integer.valueOf(paramLimb.getCharacterId()));
    contentValues.put("INTELLIGENCE", Integer.valueOf(paramLimb.getIntelligence()));
    contentValues.put("DAMAGE", Integer.valueOf(paramLimb.getDamage()));
    if (paramLimb.getId() < 0) {
      int i = (int)sQLiteDatabase.insert("LIMB_TABLE", null, contentValues);
      if (i > 0) {
        bool1 = true;
        paramLimb.setId(i);
      } 
    } else {
      bool1 = bool2;
      if (sQLiteDatabase.update("LIMB_TABLE", contentValues, "ID=?", new String[] { String.valueOf(paramLimb.getId()) }) > 0L)
        bool1 = true; 
    } 
    return bool1;
  }
  
  public void saveNpc(Character paramCharacter) {
    SQLiteDatabase sQLiteDatabase = getWritableDatabase();
    deleteAllNpcLimbs(sQLiteDatabase);
    ContentValues contentValues = new ContentValues();
    contentValues.put("ALIVE", Boolean.valueOf(paramCharacter.isAlive()));
    contentValues.put("NAME", paramCharacter.getName());
    long l = sQLiteDatabase.update("CHARACTER_TABLE", contentValues, "ID=?", new String[] { String.valueOf(paramCharacter.getId()) });
    Iterator<Limb> iterator = paramCharacter.getAllLimbs().iterator();
    while (iterator.hasNext())
      saveLimb(iterator.next()); 
  }
  
  public boolean toggleAudioPref() {
    return toggleAudioPref(getWritableDatabase());
  }
  
  public boolean toggleAudioPref(SQLiteDatabase paramSQLiteDatabase) {
    int i = getAudioPref(paramSQLiteDatabase) ^ true;
    ContentValues contentValues = new ContentValues();
    contentValues.put("AUDIO", Boolean.valueOf(i));
    long l = paramSQLiteDatabase.update("PLAYER_TABLE", contentValues, "ID=?", new String[] { String.valueOf(1) });
    return i;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/DatabaseHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */