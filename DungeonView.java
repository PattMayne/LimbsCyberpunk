package ca.spiralmachines.limbscyberpunk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class DungeonView extends View {
  static int screenHeight;
  
  static int screenWidth;
  
  int NPCRange;
  
  ArrayList<MapCharacter> NPCs;
  
  long UPDATE_MILLIS = 55L;
  
  boolean animateMap;
  
  boolean animateNPC;
  
  Bitmap audioOff;
  
  Bitmap audioOn;
  
  Paint audioPaint;
  
  boolean audioPref;
  
  Rect audioPrefRect;
  
  Paint blockPaint;
  
  int blue = Color.parseColor("#0f3bc0");
  
  Bitmap buildingTile;
  
  int buttonColor;
  
  Paint buttonPaint;
  
  boolean canTouch;
  
  Rect cancelRect;
  
  private boolean characterEncountered;
  
  Paint characterPaint;
  
  Paint cheatPaint;
  
  Context context;
  
  int coordinateAdjustment = 0;
  
  DatabaseHelper databaseHelper;
  
  Bitmap downBM;
  
  Bitmap downPressedBM;
  
  Rect downRect;
  
  Dungeon dungeon;
  
  int floorColor = this.white;
  
  boolean gameIsWon;
  
  int green = Color.parseColor("#00871f");
  
  Handler handler;
  
  int health;
  
  int healthBonusCounter;
  
  Bitmap homeBitmap;
  
  Rect homeButtonRect;
  
  Character hostileNpc;
  
  String lastMapMove;
  
  Bitmap leftBM;
  
  Bitmap leftPressedBM;
  
  Rect leftRect;
  
  Map map;
  
  Rect mapRect;
  
  boolean mapShifted;
  
  int maxLife;
  
  int maxNPCs;
  
  private final int maxRows = 37;
  
  Rect messageRect;
  
  boolean midAnimating;
  
  private final int minRows = 18;
  
  MediaPlayer mpBadNoise;
  
  MediaPlayer mpGoodNoise;
  
  MediaPlayer mpNoiseToPlay;
  
  Character nonHostileNpc;
  
  private int numberOfRows = 18;
  
  Typeface paintFont;
  
  int panelColor;
  
  Paint panelPaint;
  
  Rect panelRect;
  
  Character playerCharacter;
  
  int playerColor;
  
  int points;
  
  int predatorColor;
  
  int preyColor;
  
  boolean quitMessage;
  
  Random random;
  
  Bitmap rightBM;
  
  Bitmap rightPressedBM;
  
  Rect rightRect;
  
  Runnable runnable;
  
  boolean saveNewLocation;
  
  Rect screenRect;
  
  int squareLength;
  
  Paint textPaint;
  
  int touchX;
  
  int touchY;
  
  Bitmap upBM;
  
  Bitmap upPressedBM;
  
  Rect upRect;
  
  int wallColor;
  
  int white = Color.parseColor("#ffd6b1");
  
  Rect winYesRect;
  
  Rect yesRect;
  
  public DungeonView(Context paramContext, Dungeon paramDungeon) {
    super(paramContext);
    int i = Color.parseColor("#3f1a3c");
    this.wallColor = i;
    this.buttonColor = i;
    this.panelColor = Color.parseColor("#35303f");
    this.mapShifted = false;
    this.saveNewLocation = true;
    this.animateNPC = false;
    this.animateMap = false;
    this.midAnimating = false;
    this.canTouch = true;
    this.quitMessage = false;
    this.gameIsWon = false;
    this.maxNPCs = this.numberOfRows / 2;
    this.points = 0;
    this.health = 3;
    this.healthBonusCounter = 0;
    this.maxLife = 3;
    this.lastMapMove = "";
    this.audioPref = true;
    this.characterEncountered = false;
    this.context = paramContext;
    this.dungeon = paramDungeon;
    DatabaseHelper databaseHelper = new DatabaseHelper(paramContext);
    this.databaseHelper = databaseHelper;
    this.audioPref = databaseHelper.getAudioPref();
    this.random = new Random();
    this.playerColor = Color.parseColor("#364ed7");
    this.preyColor = this.green;
    this.predatorColor = Color.parseColor("#e00c1d");
    Display display = ((Activity)paramContext).getWindowManager().getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    screenWidth = point.x;
    screenHeight = point.y;
    int j = screenWidth;
    int k = -j / 5;
    i = screenHeight;
    this.mapRect = new Rect(k, -i / 5, j + j / 5, i + i / 5);
    this.screenRect = new Rect(0, 0, screenWidth, screenHeight);
    i = screenWidth;
    this.panelRect = new Rect(0, i, i, screenHeight);
    this.NPCs = new ArrayList<MapCharacter>();
    this.handler = new Handler();
    this.blockPaint = new Paint();
    this.textPaint = new Paint();
    Paint paint2 = new Paint();
    this.panelPaint = paint2;
    paint2.setColor(this.blue);
    paint2 = new Paint();
    this.characterPaint = paint2;
    paint2.setColor(-65536);
    this.textPaint.setColor(-16776961);
    this.textPaint.setTextSize(50.0F);
    Typeface typeface = ResourcesCompat.getFont(paramContext, 2131230721);
    this.paintFont = typeface;
    this.textPaint.setTypeface(typeface);
    this.blockPaint.setColor(-16711936);
    Paint paint1 = new Paint();
    this.buttonPaint = paint1;
    paint1.setColor(this.buttonColor);
    this.audioPaint = new Paint();
    paint1 = new Paint();
    this.cheatPaint = paint1;
    paint1.setColor(-1);
    this.cheatPaint.setTextSize(37.0F);
    this.buildingTile = BitmapFactory.decodeResource(getResources(), 2131165364);
    if (this.audioPref) {
      this.audioPaint.setColor(this.preyColor);
    } else {
      this.audioPaint.setColor(this.predatorColor);
    } 
    if (this.maxNPCs < 2)
      this.maxNPCs = 2; 
    setDimensions();
    setupCharacters();
    this.map = createMap();
    resetAdjacentBlocks();
    updateBuildings();
    clearCentre();
    createButtonRects();
    setupAudio();
    MediaPlayer mediaPlayer = this.mpGoodNoise;
    if (mediaPlayer != null && this.audioPref) {
      this.mpNoiseToPlay = mediaPlayer;
      playNoise();
    } 
    getImages();
    invalidate();
    this.runnable = new Runnable() {
        final DungeonView this$0;
        
        public void run() {
          DungeonView.this.invalidate();
        }
      };
  }
  
  private boolean NPCDecision(BlockPoint paramBlockPoint) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (this.NPCs.size() < this.maxNPCs) {
      bool1 = bool2;
      if (this.random.nextInt(101) > 95) {
        createNewNPC(paramBlockPoint);
        bool1 = true;
      } 
    } 
    return bool1;
  }
  
  private void assignAbsoluteIndexesToMap() {
    BlockPoint blockPoint = getCentreBlock();
    int i = blockPoint.getBlockIndex();
    int j = blockPoint.getRowIndex();
    for (byte b = 0; b < this.map.rows.size(); b++) {
      for (byte b1 = 0; b1 < ((Row)this.map.rows.get(b)).blockPoints.size(); b1++) {
        BlockPoint blockPoint1 = ((Row)this.map.rows.get(b)).blockPoints.get(b1);
        blockPoint1.absoluteIndexY = blockPoint1.getRowIndex() - j + this.playerCharacter.getAbsoluteIndexY();
        blockPoint1.absoluteIndexX = blockPoint1.getBlockIndex() - i + this.playerCharacter.getAbsoluteIndexX();
        for (MapCharacter mapCharacter : this.NPCs) {
          if (mapCharacter.getCharacter().isQuestCharacter() && checkForAbsoluteIdentical(blockPoint1, mapCharacter.getCharacter())) {
            mapCharacter.setBlockPoint(blockPoint1);
            blockPoint1.isPath = true;
          } 
        } 
      } 
    } 
  }
  
  private void backToMain() {
    Intent intent = new Intent(this.context, PlayActivity.class);
    ((Activity)this.context).startActivity(intent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private boolean checkForCentreBlock() {
    BlockPoint blockPoint = getCentreBlock();
    if (blockPoint.isBuilding()) {
      this.saveNewLocation = false;
      Intent intent = new Intent(this.context, BuildingActivity.class);
      this.context.startActivity(intent);
      ((Activity)this.context).finish();
      ((Activity)this.context).overridePendingTransition(17432576, 17432577);
      return false;
    } 
    for (MapCharacter mapCharacter : this.NPCs) {
      if (sameCoordinates(blockPoint, mapCharacter.getBlockPoint())) {
        this.characterEncountered = true;
        if (mapCharacter.getCharacter().isQuestCharacter() && !mapCharacter.getCharacter().isHostile()) {
          startIntentSequence(new Intent(this.context, CharacterDialogActivity.class));
        } else {
          this.databaseHelper.saveNpc(mapCharacter.getCharacter());
          startIntentSequence(new Intent(this.context, BattleActivity.class));
        } 
        return true;
      } 
    } 
    return false;
  }
  
  private void checkForDoubles() {
    checkForCentreBlock();
    for (MapCharacter mapCharacter : this.NPCs) {
      for (MapCharacter mapCharacter1 : this.NPCs) {
        if (mapCharacter1 != mapCharacter && sameCoordinates(mapCharacter1.getBlockPoint(), mapCharacter.getBlockPoint()))
          mapCharacter.kill(); 
      } 
    } 
  }
  
  private String checkOnQuestNpc() {
    String str = "";
    for (MapCharacter mapCharacter : this.NPCs) {
      String str1 = str;
      if (mapCharacter.getCharacter().getId() == 10) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("X,Y: ");
        stringBuilder.append((mapCharacter.getBlockPoint()).x);
        stringBuilder.append(",");
        stringBuilder.append((mapCharacter.getBlockPoint()).y);
        stringBuilder.append(" ");
        str = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("//ab.X,Y");
        stringBuilder.append((mapCharacter.getBlockPoint()).absoluteIndexX);
        stringBuilder.append(",");
        stringBuilder.append((mapCharacter.getBlockPoint()).absoluteIndexY);
        stringBuilder.append(" ");
        str1 = stringBuilder.toString();
      } 
      str = str1;
    } 
    return str;
  }
  
  private void clearCentre() {
    BlockPoint blockPoint = getCentreBlock();
    if (blockPoint.x > this.squareLength * 4 && blockPoint.y > this.squareLength * 4) {
      int j = blockPoint.x;
      int k = this.numberOfRows;
      int i = this.squareLength;
      if (j < k * i - i * 4) {
        j = blockPoint.y;
        i = this.numberOfRows;
        k = this.squareLength;
        if (j < i * k - k * 4)
          for (i = blockPoint.getBlockIndex() - 3; i < blockPoint.getBlockIndex() + 3; i++) {
            for (j = blockPoint.getRowIndex() - 3; j < blockPoint.getRowIndex() + 3; j++)
              ((BlockPoint)((Row)this.map.rows.get(j)).blockPoints.get(i)).isPath = true; 
          }  
      } 
    } 
    for (MapCharacter mapCharacter : this.NPCs) {
      if (sameCoordinates(getCentreBlock(), mapCharacter.getBlockPoint()))
        mapCharacter.kill(); 
    } 
  }
  
  private BlockPoint createBlockPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ArrayList<BlockPoint> paramArrayList) {
    BlockPoint blockPoint = new BlockPoint(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt4 > 0) {
      if (!((BlockPoint)paramArrayList.get(paramInt4 - 1)).isPath() && ((BlockPoint)paramArrayList.get(paramInt4 - 1)).hBlocksToPlace > 0) {
        blockPoint.isPath = false;
        ((BlockPoint)paramArrayList.get(paramInt4 - 1)).hBlocksToPlace--;
        blockPoint.vBlocksToPlace = ((BlockPoint)paramArrayList.get(paramInt4 - 1)).vBlocksToPlace;
      } 
    } else if (paramArrayList.size() > 1 && !((BlockPoint)paramArrayList.get(paramInt4 + 1)).isPath() && ((BlockPoint)paramArrayList.get(paramInt4 + 1)).hBlocksToPlace > 0) {
      blockPoint.isPath = false;
      ((BlockPoint)paramArrayList.get(paramInt4 + 1)).hBlocksToPlace--;
      blockPoint.vBlocksToPlace = ((BlockPoint)paramArrayList.get(paramInt4 + 1)).vBlocksToPlace;
    } 
    if (paramInt3 == 0 && this.map.rows.size() > 1) {
      if (!((BlockPoint)((Row)this.map.rows.get(1)).blockPoints.get(paramInt4)).isPath() && ((BlockPoint)((Row)this.map.rows.get(1)).blockPoints.get(paramInt4)).vBlocksToPlace > 0) {
        blockPoint.isPath = false;
        ((BlockPoint)((Row)this.map.rows.get(1)).blockPoints.get(paramInt4)).vBlocksToPlace--;
        blockPoint.hBlocksToPlace = ((BlockPoint)((Row)this.map.rows.get(1)).blockPoints.get(paramInt4)).hBlocksToPlace;
      } 
    } else if (paramInt3 > 0 && !((BlockPoint)((Row)this.map.rows.get(paramInt3 - 1)).blockPoints.get(paramInt4)).isPath() && ((BlockPoint)((Row)this.map.rows.get(paramInt3 - 1)).blockPoints.get(paramInt4)).vBlocksToPlace > 0) {
      blockPoint.isPath = false;
      ((BlockPoint)((Row)this.map.rows.get(paramInt3 - 1)).blockPoints.get(paramInt4)).vBlocksToPlace--;
      blockPoint.hBlocksToPlace = ((BlockPoint)((Row)this.map.rows.get(paramInt3 - 1)).blockPoints.get(paramInt4)).hBlocksToPlace;
    } 
    paramInt1 = 0;
    for (MapCharacter mapCharacter : this.NPCs) {
      paramInt2 = paramInt1;
      if (mapCharacter.getCharacter().isQuestCharacter()) {
        paramInt2 = paramInt1;
        if (checkForAbsoluteIdentical(blockPoint, mapCharacter.getCharacter())) {
          mapCharacter.setBlockPoint(blockPoint);
          blockPoint.isPath = true;
          paramInt2 = 1;
        } 
      } 
      paramInt1 = paramInt2;
    } 
    if (paramInt1 == 0 && blockPoint.isPath()) {
      pathDecision(blockPoint);
      NPCDecision(blockPoint);
    } 
    return blockPoint;
  }
  
  private void createButtonRects() {
    int i = screenHeight;
    this.messageRect = new Rect(0, i / 3, screenWidth, i - i / 3);
    int j = screenHeight / 3;
    i = screenWidth / 4;
    int k = screenHeight;
    this.yesRect = new Rect(0, k - j - k / 12, i, k - j);
    k = screenWidth;
    int m = screenHeight;
    this.cancelRect = new Rect(k - i, m - j - m / 12, k, m - j);
    m = screenWidth;
    int n = m / 2;
    k = i / 2;
    int i1 = screenHeight;
    this.winYesRect = new Rect(n - k, i1 - j - i1 / 12, m / 2 + i / 2, i1 - j);
    Rect rect = this.panelRect;
    if (rect != null) {
      i = 400;
      j = 200;
      if (200 * 3 > rect.height()) {
        j = this.panelRect.height() / 3 - 6;
        i = j * 2;
      } 
      m = screenWidth;
      n = m / 2;
      i1 = i / 2;
      k = screenHeight;
      this.downRect = new Rect(n - i1, k - j + 2, m / 2 + i / 2, k - 2);
      k = screenWidth;
      m = k / 2;
      n = screenHeight;
      this.leftRect = new Rect(m - i - 2, n - j * 2 + 2, k / 2 - 2, n - j - 2);
      k = screenWidth;
      m = k / 2;
      n = screenHeight;
      this.rightRect = new Rect(m + 2, n - j * 2 + 2, k / 2 + i - 2, n - j - 2);
      n = screenWidth;
      k = n / 2;
      i1 = i / 2;
      m = screenHeight;
      this.upRect = new Rect(k - i1, m - j * 3 + 1, n / 2 + i / 2 - 2, m - j * 2 - 2);
      k = screenHeight;
      this.audioPrefRect = new Rect(2, k - j + 1, i / 2 - 2, k - 2);
      j = screenWidth;
      this.homeButtonRect = new Rect(j - i / 2 - 2, j + 2, j - 2, j + i / 2 - 2);
    } 
  }
  
  private Map createMap() {
    this.map = new Map();
    for (byte b = 0; b < this.numberOfRows; b++)
      this.map.rows.add(createRow(b)); 
    assignAbsoluteIndexesToMap();
    this.dungeon.createBuildings(this.squareLength);
    return this.map;
  }
  
  private void createNewNPC(BlockPoint paramBlockPoint) {
    Character character;
    boolean bool = true;
    int i = 20;
    int j = this.points;
    if (j < 10) {
      i = 20;
    } else if (j < 25) {
      i = 25;
    } else if (j < 50) {
      i = 30;
    } else if (j < 80) {
      i = 35;
    } else if (j < 111) {
      i = 40;
    } else if (j < 150) {
      i = 45;
    } else if (j < 200) {
      i = 50;
    } else if (j < 300) {
      i = 55;
    } else if (j < 400) {
      i = 57;
    } else if (j < 500) {
      i = 59;
    } else if (j < 600) {
      i = 63;
    } else if (j < 800) {
      i = 69;
    } else if (j < 1000) {
      i = 70;
    } else if (j < 1200) {
      i = 73;
    } else if (j < 1310) {
      i = 74;
    } else if (j < 1500) {
      i = 77;
    } else if (j < 1867) {
      i = 80;
    } else if (j < 1989) {
      i = 90;
    } 
    if (this.random.nextInt(100) > i) {
      bool = false;
      character = this.nonHostileNpc;
      i = this.squareLength;
      character.setAvatarImage(i, i);
      character = this.nonHostileNpc;
    } else {
      character = this.hostileNpc;
      i = this.squareLength;
      character.setAvatarImage(i, i);
      character = this.hostileNpc;
    } 
    MapCharacter mapCharacter = new MapCharacter(paramBlockPoint, true, bool);
    mapCharacter.setCharacter(character);
    this.NPCs.add(mapCharacter);
  }
  
  private Row createRow(int paramInt) {
    Row row = new Row();
    for (byte b = 0; b < this.numberOfRows; b++) {
      int i = this.coordinateAdjustment;
      int j = this.squareLength;
      row.blockPoints.add(createBlockPoint((b - i) * j, (paramInt - i) * j, paramInt, b, row.blockPoints));
    } 
    this.NPCRange = this.squareLength * 4;
    return row;
  }
  
  private void drawBlock(Canvas paramCanvas, BlockPoint paramBlockPoint, int paramInt) {
    drawBlock(paramCanvas, paramBlockPoint, paramInt, 0, 0);
  }
  
  private void drawBlock(Canvas paramCanvas, BlockPoint paramBlockPoint, int paramInt1, int paramInt2, int paramInt3) {
    this.blockPaint.setColor(paramInt1);
    int m = paramBlockPoint.x;
    int n = this.squareLength / 2;
    int i = paramBlockPoint.y;
    paramInt1 = this.squareLength / 2;
    int i2 = paramBlockPoint.x;
    int i1 = this.squareLength / 2;
    int j = paramBlockPoint.y;
    int k = this.squareLength / 2;
    paramCanvas.drawRect((m - n + paramInt2), (i - paramInt1 + paramInt3), (i2 + i1 + paramInt2 + 1), (j + k + paramInt3 + 1), this.blockPaint);
  }
  
  private void drawCharacters(Canvas paramCanvas) {
    if (this.animateMap)
      if (this.lastMapMove.equals("up")) {
        int i = this.squareLength / 2;
      } else if (this.lastMapMove.equals("down")) {
        int i = -(this.squareLength / 2);
      } else if (this.lastMapMove.equals("left")) {
        int i = this.squareLength / 2;
      } else if (this.lastMapMove.equals("right")) {
        int i = -(this.squareLength / 2);
      }  
    byte b = 0;
    for (MapCharacter mapCharacter : this.NPCs) {
      BlockPoint blockPoint;
      b++;
      if (mapCharacter.isAggressive) {
        int i = this.predatorColor;
      } else {
        int i = this.preyColor;
      } 
      if (this.animateNPC) {
        blockPoint = new BlockPoint(((mapCharacter.getOldBlockPoint()).x + (mapCharacter.getBlockPoint()).x) / 2, ((mapCharacter.getOldBlockPoint()).y + (mapCharacter.getBlockPoint()).y) / 2, mapCharacter.getBlockPoint().getRowIndex(), mapCharacter.getBlockPoint().getBlockIndex());
      } else {
        blockPoint = mapCharacter.getBlockPoint();
      } 
      paramCanvas.drawBitmap(mapCharacter.getCharacter().getImage(), (blockPoint.x - this.squareLength / 2), (blockPoint.y - this.squareLength / 2), null);
    } 
    drawPlayer(paramCanvas);
  }
  
  private void drawMap(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: getfield blockPaint : Landroid/graphics/Paint;
    //   4: aload_0
    //   5: getfield green : I
    //   8: invokevirtual setColor : (I)V
    //   11: aload_1
    //   12: aload_0
    //   13: getfield screenRect : Landroid/graphics/Rect;
    //   16: aload_0
    //   17: getfield blockPaint : Landroid/graphics/Paint;
    //   20: invokevirtual drawRect : (Landroid/graphics/Rect;Landroid/graphics/Paint;)V
    //   23: aload_0
    //   24: getfield animateMap : Z
    //   27: ifeq -> 132
    //   30: aload_0
    //   31: getfield lastMapMove : Ljava/lang/String;
    //   34: ldc_w 'up'
    //   37: invokevirtual equals : (Ljava/lang/Object;)Z
    //   40: ifeq -> 55
    //   43: aload_0
    //   44: getfield squareLength : I
    //   47: iconst_2
    //   48: idiv
    //   49: istore_3
    //   50: iconst_0
    //   51: istore_2
    //   52: goto -> 136
    //   55: aload_0
    //   56: getfield lastMapMove : Ljava/lang/String;
    //   59: ldc_w 'down'
    //   62: invokevirtual equals : (Ljava/lang/Object;)Z
    //   65: ifeq -> 81
    //   68: aload_0
    //   69: getfield squareLength : I
    //   72: iconst_2
    //   73: idiv
    //   74: ineg
    //   75: istore_3
    //   76: iconst_0
    //   77: istore_2
    //   78: goto -> 136
    //   81: aload_0
    //   82: getfield lastMapMove : Ljava/lang/String;
    //   85: ldc_w 'left'
    //   88: invokevirtual equals : (Ljava/lang/Object;)Z
    //   91: ifeq -> 106
    //   94: aload_0
    //   95: getfield squareLength : I
    //   98: iconst_2
    //   99: idiv
    //   100: istore_2
    //   101: iconst_0
    //   102: istore_3
    //   103: goto -> 136
    //   106: aload_0
    //   107: getfield lastMapMove : Ljava/lang/String;
    //   110: ldc_w 'right'
    //   113: invokevirtual equals : (Ljava/lang/Object;)Z
    //   116: ifeq -> 132
    //   119: aload_0
    //   120: getfield squareLength : I
    //   123: iconst_2
    //   124: idiv
    //   125: ineg
    //   126: istore_2
    //   127: iconst_0
    //   128: istore_3
    //   129: goto -> 136
    //   132: iconst_0
    //   133: istore_2
    //   134: iconst_0
    //   135: istore_3
    //   136: aload_0
    //   137: getfield blockPaint : Landroid/graphics/Paint;
    //   140: aload_0
    //   141: getfield floorColor : I
    //   144: invokevirtual setColor : (I)V
    //   147: iconst_0
    //   148: istore #4
    //   150: iload #4
    //   152: aload_0
    //   153: getfield map : Lca/spiralmachines/limbscyberpunk/Map;
    //   156: getfield rows : Ljava/util/ArrayList;
    //   159: invokevirtual size : ()I
    //   162: if_icmpge -> 377
    //   165: aload_0
    //   166: getfield map : Lca/spiralmachines/limbscyberpunk/Map;
    //   169: getfield rows : Ljava/util/ArrayList;
    //   172: iload #4
    //   174: invokevirtual get : (I)Ljava/lang/Object;
    //   177: checkcast ca/spiralmachines/limbscyberpunk/Row
    //   180: astore #11
    //   182: iconst_0
    //   183: istore #5
    //   185: iload #5
    //   187: aload #11
    //   189: getfield blockPoints : Ljava/util/ArrayList;
    //   192: invokevirtual size : ()I
    //   195: if_icmpge -> 371
    //   198: aload #11
    //   200: getfield blockPoints : Ljava/util/ArrayList;
    //   203: iload #5
    //   205: invokevirtual get : (I)Ljava/lang/Object;
    //   208: checkcast ca/spiralmachines/limbscyberpunk/BlockPoint
    //   211: astore #10
    //   213: aload #10
    //   215: invokevirtual isPath : ()Z
    //   218: ifeq -> 239
    //   221: aload_0
    //   222: getfield floorColor : I
    //   225: istore #6
    //   227: aload_0
    //   228: getfield dungeon : Lca/spiralmachines/limbscyberpunk/Dungeon;
    //   231: invokevirtual getFloorBitmap : ()Landroid/graphics/Bitmap;
    //   234: astore #9
    //   236: goto -> 254
    //   239: aload_0
    //   240: getfield wallColor : I
    //   243: istore #6
    //   245: aload_0
    //   246: getfield dungeon : Lca/spiralmachines/limbscyberpunk/Dungeon;
    //   249: invokevirtual getWallBitmap1 : ()Landroid/graphics/Bitmap;
    //   252: astore #9
    //   254: aload #10
    //   256: invokevirtual isBuilding : ()Z
    //   259: ifeq -> 271
    //   262: aload_0
    //   263: getfield buildingTile : Landroid/graphics/Bitmap;
    //   266: astore #9
    //   268: goto -> 271
    //   271: aload #11
    //   273: getfield blockPoints : Ljava/util/ArrayList;
    //   276: iload #5
    //   278: invokevirtual get : (I)Ljava/lang/Object;
    //   281: checkcast ca/spiralmachines/limbscyberpunk/BlockPoint
    //   284: getfield x : I
    //   287: istore #6
    //   289: aload #11
    //   291: getfield blockPoints : Ljava/util/ArrayList;
    //   294: iload #5
    //   296: invokevirtual get : (I)Ljava/lang/Object;
    //   299: checkcast ca/spiralmachines/limbscyberpunk/BlockPoint
    //   302: getfield y : I
    //   305: istore #7
    //   307: aload_0
    //   308: getfield squareLength : I
    //   311: istore #8
    //   313: iload #6
    //   315: iload #8
    //   317: ineg
    //   318: if_icmple -> 365
    //   321: iload #6
    //   323: getstatic ca/spiralmachines/limbscyberpunk/DungeonView.screenWidth : I
    //   326: iload #8
    //   328: iadd
    //   329: if_icmpgt -> 365
    //   332: iload #7
    //   334: iload #8
    //   336: ineg
    //   337: if_icmple -> 365
    //   340: iload #7
    //   342: getstatic ca/spiralmachines/limbscyberpunk/DungeonView.screenHeight : I
    //   345: iload #8
    //   347: iadd
    //   348: if_icmpgt -> 365
    //   351: aload_0
    //   352: aload_1
    //   353: aload #10
    //   355: aload #9
    //   357: iload_2
    //   358: iload_3
    //   359: invokespecial drawTile : (Landroid/graphics/Canvas;Lca/spiralmachines/limbscyberpunk/BlockPoint;Landroid/graphics/Bitmap;II)V
    //   362: goto -> 365
    //   365: iinc #5, 1
    //   368: goto -> 185
    //   371: iinc #4, 1
    //   374: goto -> 150
    //   377: return
  }
  
  private void drawPanel(Canvas paramCanvas) {
    Bitmap bitmap;
    paramCanvas.drawRect(this.panelRect, this.panelPaint);
    paramCanvas.drawRect(this.downRect, this.buttonPaint);
    paramCanvas.drawRect(this.leftRect, this.buttonPaint);
    paramCanvas.drawRect(this.upRect, this.buttonPaint);
    paramCanvas.drawRect(this.rightRect, this.buttonPaint);
    if (this.audioPref) {
      bitmap = this.audioOn;
    } else {
      bitmap = this.audioOff;
    } 
    paramCanvas.drawRect(this.audioPrefRect, this.audioPaint);
    paramCanvas.drawBitmap(bitmap, (this.audioPrefRect.left + 5), (this.audioPrefRect.top + 5), null);
    paramCanvas.drawRect(this.homeButtonRect, this.buttonPaint);
    paramCanvas.drawBitmap(this.homeBitmap, (this.homeButtonRect.left + 5), (this.homeButtonRect.top + 5), null);
    int i = this.upRect.left + this.upRect.width() / 2 - this.upBM.getWidth() / 2;
    paramCanvas.drawBitmap(this.upBM, i, (this.upRect.top + 5), null);
    paramCanvas.drawBitmap(this.downBM, i, (this.downRect.top + 5), null);
    paramCanvas.drawBitmap(this.leftBM, (this.leftRect.left + 5), (this.leftRect.top + 5), null);
    paramCanvas.drawBitmap(this.rightBM, (this.rightRect.right - this.rightBM.getWidth() - 5), (this.rightRect.top + 5), null);
    if (this.animateMap || this.animateNPC || this.midAnimating) {
      this.buttonPaint.setColor(this.green);
      if (this.lastMapMove.equals("up")) {
        paramCanvas.drawRect(this.downRect, this.buttonPaint);
        paramCanvas.drawBitmap(this.downPressedBM, i, (this.downRect.top + 5), null);
      } else if (this.lastMapMove.equals("down")) {
        paramCanvas.drawRect(this.upRect, this.buttonPaint);
        paramCanvas.drawBitmap(this.upPressedBM, i, (this.upRect.top + 5), null);
      } else if (this.lastMapMove.equals("left")) {
        paramCanvas.drawRect(this.rightRect, this.buttonPaint);
        paramCanvas.drawBitmap(this.rightPressedBM, (this.rightRect.right - this.rightBM.getWidth() - 5), (this.rightRect.top + 5), null);
      } else if (this.lastMapMove.equals("right")) {
        paramCanvas.drawRect(this.leftRect, this.buttonPaint);
        paramCanvas.drawBitmap(this.leftPressedBM, (this.leftRect.left + 5), (this.leftRect.top + 5), null);
      } 
      this.buttonPaint.setColor(this.buttonColor);
    } 
    if (this.quitMessage)
      paintQuitMessage(paramCanvas); 
  }
  
  private void drawPlayer(Canvas paramCanvas) {
    BlockPoint blockPoint = getCentreBlock();
    int m = blockPoint.x;
    int k = this.squareLength / 2;
    int i = blockPoint.y;
    int j = this.squareLength / 2;
    if (this.playerCharacter.getImage() != null) {
      paramCanvas.drawBitmap(this.playerCharacter.getImage(), (m - k), (i - j), null);
    } else {
      drawBlock(paramCanvas, getCentreBlock(), -16777216);
    } 
  }
  
  private void drawPoints(Canvas paramCanvas) {
    if (this.quitMessage)
      return; 
    if (!this.animateNPC && !this.midAnimating && !this.animateMap) {
      int i = screenWidth;
      Rect rect = new Rect(5, i + 5, i / 4 - 5, i + this.panelRect.height() / 3 - 5);
      this.blockPaint.setColor(this.blue);
      paramCanvas.drawRect(rect, this.blockPaint);
    } 
    this.textPaint.setColor(-16711936);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Health: ");
    stringBuilder.append(this.health);
    stringBuilder.append("/");
    stringBuilder.append(this.maxLife);
    String str = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append("Points: ");
    stringBuilder.append(this.points);
    paramCanvas.drawText(stringBuilder.toString(), 20.0F, (screenWidth + 50), this.textPaint);
    paramCanvas.drawText(str, 20.0F, (screenWidth + 100), this.textPaint);
    paramCanvas.drawText(checkOnQuestNpc(), 20.0F, (screenWidth + 200), this.textPaint);
  }
  
  private void drawTile(Canvas paramCanvas, BlockPoint paramBlockPoint, Bitmap paramBitmap, int paramInt1, int paramInt2) {
    int i = paramBlockPoint.x;
    int k = this.squareLength / 2;
    int j = paramBlockPoint.y;
    int m = this.squareLength / 2;
    paramCanvas.drawBitmap(paramBitmap, (i - k + paramInt1), (j - m + paramInt2), null);
  }
  
  private void filterNPCs() {
    if (this.NPCs.size() > 0)
      for (int i = this.NPCs.size() - 1; i > 0; i--) {
        MapCharacter mapCharacter = this.NPCs.get(i);
        if (!mapCharacter.getBlockPoint().isAlive())
          this.NPCs.remove(mapCharacter); 
      }  
  }
  
  private void followOrFlee(MapCharacter paramMapCharacter) {
    ArrayList<BlockPoint> arrayList = new ArrayList();
    BlockPoint blockPoint = getCentreBlock();
    if (paramMapCharacter.isAggressive) {
      if ((paramMapCharacter.getBlockPoint()).x < blockPoint.x) {
        arrayList.add(getBlockRight(paramMapCharacter.getBlockPoint()));
      } else if ((paramMapCharacter.getBlockPoint()).x > blockPoint.x) {
        arrayList.add(getBlockLeft(paramMapCharacter.getBlockPoint()));
      } 
      if ((paramMapCharacter.getBlockPoint()).y < blockPoint.y) {
        arrayList.add(getBlockBelow(paramMapCharacter.getBlockPoint()));
      } else if ((paramMapCharacter.getBlockPoint()).y > blockPoint.y) {
        arrayList.add(getBlockAbove(paramMapCharacter.getBlockPoint()));
      } 
    } else {
      if ((paramMapCharacter.getBlockPoint()).x < blockPoint.x) {
        arrayList.add(getBlockLeft(paramMapCharacter.getBlockPoint()));
      } else if ((paramMapCharacter.getBlockPoint()).x > blockPoint.x) {
        arrayList.add(getBlockRight(paramMapCharacter.getBlockPoint()));
      } 
      if ((paramMapCharacter.getBlockPoint()).y < blockPoint.y) {
        arrayList.add(getBlockAbove(paramMapCharacter.getBlockPoint()));
      } else if ((paramMapCharacter.getBlockPoint()).y > blockPoint.y) {
        arrayList.add(getBlockBelow(paramMapCharacter.getBlockPoint()));
      } 
    } 
    if (arrayList.size() > 0) {
      blockPoint = arrayList.get(this.random.nextInt(arrayList.size()));
      if (blockPoint != null && blockPoint.isPath() && blockPoint.isAlive())
        paramMapCharacter.setBlockPoint(blockPoint); 
      return;
    } 
    randomMoveNPC(paramMapCharacter);
  }
  
  private ArrayList<BlockPoint> getAdjacentBlocks(BlockPoint paramBlockPoint) {
    ArrayList<BlockPoint> arrayList = new ArrayList();
    int i = paramBlockPoint.getRowIndex();
    int j = paramBlockPoint.getBlockIndex();
    if (i > 0)
      arrayList.add(((Row)this.map.rows.get(i - 1)).blockPoints.get(j)); 
    if (j < this.map.rows.size() - 1)
      arrayList.add(((Row)this.map.rows.get(i)).blockPoints.get(j + 1)); 
    if (i < this.map.rows.size() - 1)
      arrayList.add(((Row)this.map.rows.get(i + 1)).blockPoints.get(j)); 
    if (j > 0)
      arrayList.add(((Row)this.map.rows.get(i)).blockPoints.get(j - 1)); 
    paramBlockPoint.setAdjacentBlocks(arrayList);
    return arrayList;
  }
  
  private BlockPoint getBlockAbove(BlockPoint paramBlockPoint) {
    return (paramBlockPoint.getRowIndex() > 1) ? ((Row)this.map.rows.get(paramBlockPoint.getRowIndex() - 1)).blockPoints.get(paramBlockPoint.getBlockIndex()) : null;
  }
  
  private BlockPoint getBlockBelow(BlockPoint paramBlockPoint) {
    return (paramBlockPoint.getRowIndex() < this.map.rows.size() - 1) ? ((Row)this.map.rows.get(paramBlockPoint.getRowIndex() + 1)).blockPoints.get(paramBlockPoint.getBlockIndex()) : null;
  }
  
  private BlockPoint getBlockLeft(BlockPoint paramBlockPoint) {
    return (paramBlockPoint.getBlockIndex() > 1) ? ((Row)this.map.rows.get(paramBlockPoint.getRowIndex())).blockPoints.get(paramBlockPoint.getBlockIndex() - 1) : null;
  }
  
  private BlockPoint getBlockRight(BlockPoint paramBlockPoint) {
    return (paramBlockPoint.getBlockIndex() < ((Row)this.map.rows.get(paramBlockPoint.getRowIndex())).blockPoints.size() - 1) ? ((Row)this.map.rows.get(paramBlockPoint.getRowIndex())).blockPoints.get(paramBlockPoint.getBlockIndex() + 1) : null;
  }
  
  private BlockPoint getCentreBlock() {
    Row row = this.map.rows.get(this.map.rows.size() / 2);
    return row.blockPoints.get(row.blockPoints.size() / 2);
  }
  
  private void getImages() {
    int i = this.audioPrefRect.width() - 10;
    int j = this.audioPrefRect.height() - 10;
    this.audioOff = BitmapFactory.decodeResource(getResources(), 2131165270);
    this.audioOn = BitmapFactory.decodeResource(getResources(), 2131165271);
    this.homeBitmap = BitmapFactory.decodeResource(getResources(), 2131165291);
    this.upBM = BitmapFactory.decodeResource(getResources(), 2131165372);
    this.downBM = BitmapFactory.decodeResource(getResources(), 2131165289);
    this.leftBM = BitmapFactory.decodeResource(getResources(), 2131165300);
    this.rightBM = BitmapFactory.decodeResource(getResources(), 2131165361);
    this.upPressedBM = BitmapFactory.decodeResource(getResources(), 2131165373);
    this.downPressedBM = BitmapFactory.decodeResource(getResources(), 2131165290);
    this.leftPressedBM = BitmapFactory.decodeResource(getResources(), 2131165301);
    this.rightPressedBM = BitmapFactory.decodeResource(getResources(), 2131165362);
    this.audioOff = getResizedBitmap(this.audioOff, i, j);
    this.audioOn = getResizedBitmap(this.audioOn, i, j);
    this.homeBitmap = getResizedBitmap(this.homeBitmap, i, j);
    this.upBM = getResizedBitmap(this.upBM, i, j);
    this.downBM = getResizedBitmap(this.downBM, i, j);
    this.leftBM = getResizedBitmap(this.leftBM, i, j);
    this.rightBM = getResizedBitmap(this.rightBM, i, j);
    this.upPressedBM = getResizedBitmap(this.upPressedBM, i, j);
    this.downPressedBM = getResizedBitmap(this.downPressedBM, i, j);
    this.leftPressedBM = getResizedBitmap(this.leftPressedBM, i, j);
    this.rightPressedBM = getResizedBitmap(this.rightPressedBM, i, j);
  }
  
  private String getNpdIds() {
    String str = "";
    for (MapCharacter mapCharacter : this.NPCs) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(mapCharacter.getCharacter().getId());
      stringBuilder.append(", ");
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  private int getTotalNumberOfBlocks() {
    byte b = 0;
    Iterator<Row> iterator = this.map.rows.iterator();
    while (iterator.hasNext()) {
      for (BlockPoint blockPoint : ((Row)iterator.next()).blockPoints)
        b++; 
    } 
    return b;
  }
  
  private void homeButtonAction() {
    openQuitMessage();
  }
  
  private boolean isWithinRange(BlockPoint paramBlockPoint) {
    BlockPoint blockPoint = getCentreBlock();
    int j = blockPoint.x - paramBlockPoint.x;
    int k = blockPoint.y - paramBlockPoint.y;
    int i = j;
    if (j < 0)
      i = j * -1; 
    j = k;
    if (k < 0)
      j = k * -1; 
    k = this.NPCRange;
    return (i <= k && j <= k);
  }
  
  private void killBlock(BlockPoint paramBlockPoint) {
    paramBlockPoint.setAlive(false);
  }
  
  private void killRow(Row paramRow) {
    for (byte b = 0; b < paramRow.blockPoints.size(); b++)
      killBlock(paramRow.blockPoints.get(b)); 
  }
  
  private void moveNPC(MapCharacter paramMapCharacter) {
    if (isWithinRange(paramMapCharacter.getBlockPoint())) {
      followOrFlee(paramMapCharacter);
    } else {
      randomMoveNPC(paramMapCharacter);
    } 
  }
  
  private void moveNPCs() {
    for (MapCharacter mapCharacter : this.NPCs) {
      if (mapCharacter.getCharacter().isFreeRoam())
        moveNPC(mapCharacter); 
    } 
    checkForCentreBlock();
  }
  
  private void openQuitMessage() {
    this.quitMessage = true;
    invalidate();
  }
  
  private void paintQuitMessage(Canvas paramCanvas) {
    Paint paint = new Paint();
    paint.setColor(-16777216);
    paramCanvas.drawRect(this.messageRect, paint);
    paint.setTypeface(this.paintFont);
    paint.setColor(-256);
    paramCanvas.drawRect(this.yesRect, paint);
    paramCanvas.drawRect(this.cancelRect, paint);
    paint.setColor(-1);
    paint.setTextSize(55.0F);
    paramCanvas.drawText("Are you sure you want to quit?", (screenWidth / 4), (screenHeight / 2 - 2), paint);
    paint.setTextSize(77.0F);
    paint.setColor(-16777216);
    paramCanvas.drawText("YES", 50.0F, this.yesRect.centerY(), paint);
    paramCanvas.drawText("NO", (this.cancelRect.left + 50), this.cancelRect.centerY(), paint);
  }
  
  private void paintWinningMessage(Canvas paramCanvas) {
    Paint paint = new Paint();
    paint.setColor(-16777216);
    paramCanvas.drawRect(this.messageRect, paint);
    paint.setTypeface(this.paintFont);
    paint.setColor(-256);
    paramCanvas.drawRect(this.winYesRect, paint);
    paint.setColor(-1);
    paint.setTextSize(77.0F);
    paramCanvas.drawText("YOU WIN THE GAME!", (screenWidth / 4), (screenHeight / 2 - 2), paint);
    paint.setTextSize(77.0F);
    paint.setColor(-16777216);
    paramCanvas.drawText("OKAY", (this.winYesRect.left + 50), this.winYesRect.centerY(), paint);
  }
  
  private void pathDecision(BlockPoint paramBlockPoint) {
    if (this.random.nextInt(100) > 95) {
      paramBlockPoint.isPath = false;
      int i = this.random.nextInt(7);
      paramBlockPoint.vBlocksToPlace = this.random.nextInt(7);
      paramBlockPoint.hBlocksToPlace = i;
    } 
  }
  
  private void playNoise() {
    MediaPlayer mediaPlayer = this.mpNoiseToPlay;
    if (mediaPlayer != null && this.audioPref) {
      if (mediaPlayer.isPlaying()) {
        boolean bool = false;
        mediaPlayer = this.mpNoiseToPlay;
        if (mediaPlayer == this.mpGoodNoise)
          bool = true; 
        mediaPlayer.stop();
        this.mpNoiseToPlay.release();
        setupAudio();
        if (bool) {
          this.mpNoiseToPlay = this.mpGoodNoise;
        } else {
          this.mpNoiseToPlay = this.mpBadNoise;
        } 
      } 
      this.mpNoiseToPlay.start();
    } 
  }
  
  private void printAdjacentBlocksNumbers(Canvas paramCanvas, BlockPoint paramBlockPoint) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(paramBlockPoint.getAdjacentBlocks().size());
    paramCanvas.drawText(stringBuilder.toString(), paramBlockPoint.x, paramBlockPoint.y, this.textPaint);
  }
  
  private void printAlive(Canvas paramCanvas, BlockPoint paramBlockPoint) {
    String str;
    if (paramBlockPoint.isAlive()) {
      str = "Livs";
    } else {
      str = "Ded";
    } 
    paramCanvas.drawText(str, paramBlockPoint.x, paramBlockPoint.y, this.textPaint);
  }
  
  private void printBlockType(Canvas paramCanvas, BlockPoint paramBlockPoint) {
    String str;
    if (paramBlockPoint.isBuilding) {
      str = "BLDNG";
    } else if (paramBlockPoint.isPath()) {
      str = "FLOOR";
    } else {
      str = "WALL";
    } 
    paramCanvas.drawText(str, (paramBlockPoint.x + 10), (paramBlockPoint.y + 10), this.cheatPaint);
  }
  
  private void printBlocksToPlace(Canvas paramCanvas, BlockPoint paramBlockPoint) {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("h.");
    stringBuilder1.append(paramBlockPoint.hBlocksToPlace);
    String str1 = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("v.");
    stringBuilder2.append(paramBlockPoint.vBlocksToPlace);
    String str2 = stringBuilder2.toString();
    paramCanvas.drawText(str1, (paramBlockPoint.x - this.squareLength / 2 + 1), (paramBlockPoint.y - 10), this.textPaint);
    paramCanvas.drawText(str2, (paramBlockPoint.x - this.squareLength / 2 + 1), (paramBlockPoint.y + 20), this.textPaint);
  }
  
  private void printCount(Canvas paramCanvas, BlockPoint paramBlockPoint, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("c:");
    stringBuilder.append(paramInt);
    paramCanvas.drawText(stringBuilder.toString(), (paramBlockPoint.x - this.squareLength / 2 + 1), (paramBlockPoint.y - 14), this.textPaint);
  }
  
  private void printIndexes(Canvas paramCanvas, BlockPoint paramBlockPoint) {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("bi/");
    stringBuilder1.append(paramBlockPoint.getBlockIndex());
    String str1 = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("ri/");
    stringBuilder2.append(paramBlockPoint.getRowIndex());
    String str2 = stringBuilder2.toString();
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("ab/");
    stringBuilder3.append(paramBlockPoint.absoluteIndexX);
    stringBuilder3.append(",");
    stringBuilder3.append(paramBlockPoint.absoluteIndexY);
    String str3 = stringBuilder3.toString();
    paramCanvas.drawText(str1, (paramBlockPoint.x - this.squareLength / 2 + 1), (paramBlockPoint.y - 10), this.cheatPaint);
    paramCanvas.drawText(str2, (paramBlockPoint.x - this.squareLength / 2 + 1), (paramBlockPoint.y + 20), this.cheatPaint);
    paramCanvas.drawText(str3, (paramBlockPoint.x - this.squareLength / 2 + 1), (paramBlockPoint.y + 50), this.cheatPaint);
  }
  
  private void quitCancel() {
    this.quitMessage = false;
    this.canTouch = true;
    invalidate();
  }
  
  private void randomMoveNPC(MapCharacter paramMapCharacter) {
    BlockPoint blockPoint = paramMapCharacter.getBlockPoint().getAdjacentBlocks().get(this.random.nextInt(paramMapCharacter.getBlockPoint().getAdjacentBlocks().size()));
    if (blockPoint.isPath() && blockPoint.isAlive())
      paramMapCharacter.setBlockPoint(blockPoint); 
  }
  
  private void resetAdjacentBlocks() {
    for (int i = this.map.rows.size() - 1; i > -1; i--) {
      for (byte b = 0; b < ((Row)this.map.rows.get(i)).blockPoints.size(); b++)
        getAdjacentBlocks(((Row)this.map.rows.get(i)).blockPoints.get(b)); 
    } 
  }
  
  private void setDimensions() {
    int i = this.numberOfRows;
    if (i < 18) {
      this.numberOfRows = 18;
    } else if (i > 37) {
      this.numberOfRows = 37;
    } 
    i = this.numberOfRows;
    this.coordinateAdjustment = i / 4;
    this.squareLength = screenWidth / i / 2;
    Dungeon dungeon2 = this.dungeon;
    Bitmap bitmap1 = dungeon2.getWallBitmap1();
    i = this.squareLength;
    dungeon2.setWallBitmap1(Character.getResizedBitmap(bitmap1, i, i));
    Dungeon dungeon1 = this.dungeon;
    Bitmap bitmap2 = dungeon1.getFloorBitmap();
    i = this.squareLength;
    dungeon1.setFloorBitmap(Character.getResizedBitmap(bitmap2, i, i));
  }
  
  private void setupAudio() {
    this.mpBadNoise = MediaPlayer.create(this.context, 2131689472);
    this.mpGoodNoise = MediaPlayer.create(this.context, 2131689473);
  }
  
  private void setupCharacters() {
    Character character1 = this.databaseHelper.getCharacter(1);
    this.playerCharacter = character1;
    int i = this.squareLength;
    character1.setAvatarImage(i, i);
    if (this.playerCharacter.getDungeonId() != this.dungeon.getDungeonNumber())
      this.playerCharacter.setLocation(this.dungeon.getDungeonNumber(), 0, 0); 
    this.nonHostileNpc = this.dungeon.getNpc(0);
    this.hostileNpc = this.dungeon.getNpc(1);
    character1 = this.nonHostileNpc;
    i = this.squareLength;
    character1.setAvatarImage(i, i);
    character1 = this.hostileNpc;
    i = this.squareLength;
    character1.setAvatarImage(i, i);
    Character character2 = this.dungeon.getNpc(10);
    i = this.squareLength;
    character2.setAvatarImage(i, i);
    MapCharacter mapCharacter = new MapCharacter(new BlockPoint(100000, 100000, 0, 0, character2.getAbsoluteIndexX(), character2.getAbsoluteIndexY()), true, false);
    mapCharacter.setCharacter(character2);
    this.NPCs.add(mapCharacter);
  }
  
  private void shiftBlocKPointUp(BlockPoint paramBlockPoint) {
    paramBlockPoint.setRowIndex(paramBlockPoint.getRowIndex() - 1);
    paramBlockPoint.y -= this.squareLength;
  }
  
  private void shiftBlockPointDown(BlockPoint paramBlockPoint) {
    paramBlockPoint.setRowIndex(paramBlockPoint.getRowIndex() + 1);
    paramBlockPoint.y += this.squareLength;
  }
  
  private void shiftBlockPointLeft(BlockPoint paramBlockPoint) {
    paramBlockPoint.x -= this.squareLength;
    paramBlockPoint.setBlockIndex(paramBlockPoint.getBlockIndex() - 1);
  }
  
  private void shiftBlockPointRight(BlockPoint paramBlockPoint) {
    paramBlockPoint.x += this.squareLength;
    paramBlockPoint.setBlockIndex(paramBlockPoint.getBlockIndex() + 1);
  }
  
  private boolean shiftMapDown() {
    BlockPoint blockPoint = getCentreBlock();
    if (!((BlockPoint)((Row)this.map.rows.get(blockPoint.getRowIndex() - 1)).blockPoints.get(blockPoint.getBlockIndex())).isPath)
      return false; 
    if (this.map.rows.size() > 0)
      killRow(this.map.rows.get(this.map.rows.size() - 1)); 
    int i;
    for (i = this.map.rows.size() - 1; i > 0; i--) {
      Row row1 = this.map.rows.get(i - 1);
      this.map.rows.set(i, this.map.rows.get(i - 1));
      for (byte b = 0; b < row1.blockPoints.size(); b++)
        shiftBlockPointDown(row1.blockPoints.get(b)); 
    } 
    Row row = createRow(0);
    this.map.rows.set(0, row);
    int k = (getBlockBelow((BlockPoint)row.blockPoints.get(0))).absoluteIndexX;
    int j = (getBlockBelow((BlockPoint)row.blockPoints.get(0))).absoluteIndexY;
    for (i = 0; i < row.blockPoints.size(); i++) {
      blockPoint = row.blockPoints.get(i);
      blockPoint.absoluteIndexX = k + i;
      blockPoint.absoluteIndexY = j - 1;
    } 
    resetAdjacentBlocks();
    filterNPCs();
    this.lastMapMove = "down";
    return true;
  }
  
  private boolean shiftMapLeft() {
    BlockPoint blockPoint = getCentreBlock();
    if (!((BlockPoint)((Row)this.map.rows.get(blockPoint.getRowIndex())).blockPoints.get(blockPoint.getBlockIndex() + 1)).isPath)
      return false; 
    for (byte b = 0; b < this.map.rows.size(); b++) {
      Row row = this.map.rows.get(b);
      killBlock(row.blockPoints.get(0));
      int i;
      for (i = 1; i < row.blockPoints.size(); i++) {
        row.blockPoints.set(i - 1, row.blockPoints.get(i));
        shiftBlockPointLeft(row.blockPoints.get(i));
      } 
      int j = this.map.rows.size();
      int k = this.coordinateAdjustment;
      i = this.squareLength;
      BlockPoint blockPoint1 = createBlockPoint((j - 1 - k) * i, (b - k) * i, b, this.map.rows.size() - 1, row.blockPoints);
      row.blockPoints.set(this.map.rows.size() - 1, blockPoint1);
      (getBlockLeft(blockPoint1)).absoluteIndexX++;
      blockPoint1.absoluteIndexY = (getBlockLeft(blockPoint1)).absoluteIndexY;
    } 
    resetAdjacentBlocks();
    filterNPCs();
    this.lastMapMove = "left";
    return true;
  }
  
  private boolean shiftMapRight() {
    BlockPoint blockPoint = getCentreBlock();
    if (!((BlockPoint)((Row)this.map.rows.get(blockPoint.getRowIndex())).blockPoints.get(blockPoint.getBlockIndex() - 1)).isPath)
      return false; 
    for (byte b = 0; b < this.map.rows.size(); b++) {
      Row row = this.map.rows.get(b);
      killBlock(row.blockPoints.get(row.blockPoints.size() - 1));
      int i;
      for (i = row.blockPoints.size() - 1; i > 0; i--) {
        row.blockPoints.set(i, row.blockPoints.get(i - 1));
        shiftBlockPointRight(row.blockPoints.get(i - 1));
      } 
      i = this.coordinateAdjustment;
      int j = this.squareLength;
      BlockPoint blockPoint1 = createBlockPoint(0 - i * j, (b - i) * j, b, 0, row.blockPoints);
      row.blockPoints.set(0, blockPoint1);
      (getBlockRight(blockPoint1)).absoluteIndexX--;
      blockPoint1.absoluteIndexY = (getBlockRight(blockPoint1)).absoluteIndexY;
    } 
    resetAdjacentBlocks();
    filterNPCs();
    this.lastMapMove = "right";
    return true;
  }
  
  private boolean shiftMapUp() {
    BlockPoint blockPoint = getCentreBlock();
    if (!((BlockPoint)((Row)this.map.rows.get(blockPoint.getRowIndex() + 1)).blockPoints.get(blockPoint.getBlockIndex())).isPath)
      return false; 
    if (this.map.rows.size() > 0)
      killRow(this.map.rows.get(0)); 
    byte b;
    for (b = 0; b < this.map.rows.size() - 1; b++) {
      Row row1 = this.map.rows.get(b + 1);
      this.map.rows.set(b, this.map.rows.get(b + 1));
      for (byte b1 = 0; b1 < row1.blockPoints.size(); b1++)
        shiftBlocKPointUp(row1.blockPoints.get(b1)); 
    } 
    Row row = createRow(this.numberOfRows - 1);
    this.map.rows.set(this.numberOfRows - 1, row);
    int i = (getBlockAbove((BlockPoint)row.blockPoints.get(0))).absoluteIndexX;
    int j = (getBlockAbove((BlockPoint)row.blockPoints.get(0))).absoluteIndexY;
    for (b = 0; b < row.blockPoints.size(); b++) {
      blockPoint = row.blockPoints.get(b);
      blockPoint.absoluteIndexX = i + b;
      blockPoint.absoluteIndexY = j + 1;
    } 
    resetAdjacentBlocks();
    filterNPCs();
    this.lastMapMove = "up";
    return true;
  }
  
  private void startIntentSequence(Intent paramIntent) {
    this.context.startActivity(paramIntent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private void toggleAudioPref() {
    boolean bool = this.databaseHelper.toggleAudioPref();
    this.audioPref = bool;
    if (bool) {
      this.audioPaint.setColor(this.preyColor);
    } else {
      boolean bool1 = false;
      this.audioPaint.setColor(this.predatorColor);
      if (this.mpBadNoise.isPlaying()) {
        this.mpBadNoise.stop();
        this.mpBadNoise.release();
        bool1 = true;
      } else if (this.mpGoodNoise.isPlaying()) {
        this.mpGoodNoise.stop();
        this.mpGoodNoise.release();
        bool1 = true;
      } 
      if (bool1)
        setupAudio(); 
    } 
    invalidate();
  }
  
  private void updateBuildings() {
    if (this.map.rows.size() < 1)
      return; 
    if (((Row)this.map.rows.get(0)).blockPoints.size() < 1)
      return; 
    BlockPoint blockPoint1 = ((Row)this.map.rows.get(0)).blockPoints.get(0);
    BlockPoint blockPoint2 = ((Row)this.map.rows.get(this.map.rows.size() - 1)).blockPoints.get(((Row)this.map.rows.get(this.map.rows.size() - 1)).blockPoints.size() - 1);
    for (byte b = 0; b < this.dungeon.getBuildings().size(); b++) {
      Map map = ((Building)this.dungeon.getBuildings().get(b)).map;
      for (byte b1 = 0; b1 < map.rows.size(); b1++) {
        Row row = map.rows.get(b1);
        for (byte b2 = 0; b2 < row.blockPoints.size(); b2++) {
          BlockPoint blockPoint = row.blockPoints.get(b2);
          if (blockPoint.absoluteIndexX >= blockPoint1.absoluteIndexX && blockPoint.absoluteIndexX <= blockPoint2.absoluteIndexX && blockPoint.absoluteIndexY >= blockPoint1.absoluteIndexY && blockPoint.absoluteIndexY <= blockPoint2.absoluteIndexY) {
            int i = blockPoint.absoluteIndexY - blockPoint1.absoluteIndexY;
            int j = blockPoint.absoluteIndexX - blockPoint1.absoluteIndexX;
            if (i >= 1 && i < this.map.rows.size() && j >= 1 && j < ((Row)this.map.rows.get(0)).blockPoints.size()) {
              Iterator<BlockPoint> iterator;
              Row row1 = this.map.rows.get(i);
              BlockPoint blockPoint5 = row1.blockPoints.get(j);
              if (blockPoint5.absoluteIndexY == blockPoint.absoluteIndexY && blockPoint5.absoluteIndexX == blockPoint.absoluteIndexX) {
                blockPoint.x = blockPoint5.x;
                blockPoint.y = blockPoint5.y;
                blockPoint.setAdjacentBlocks(blockPoint5.getAdjacentBlocks());
                blockPoint.setBlockIndex(blockPoint5.getBlockIndex());
                blockPoint.setRowIndex(blockPoint5.getRowIndex());
                iterator = blockPoint.getAdjacentBlocks().iterator();
                while (iterator.hasNext())
                  ((BlockPoint)iterator.next()).isPath = true; 
                row1.blockPoints.set(j, blockPoint);
              } else {
                ((BlockPoint)iterator).isPath ^= 0x1;
              } 
            } 
          } 
        } 
        BlockPoint blockPoint3 = ((Row)map.rows.get(0)).blockPoints.get(0);
        BlockPoint blockPoint4 = ((Row)map.rows.get(map.rows.size() - 1)).blockPoints.get(((Row)map.rows.get(map.rows.size() - 1)).blockPoints.size() - 1);
        if (blockPoint3.getRowIndex() > 0 && blockPoint3.getBlockIndex() > 0)
          ((BlockPoint)((Row)this.map.rows.get(blockPoint3.getRowIndex() - 1)).blockPoints.get(blockPoint3.getBlockIndex() - 1)).isPath = true; 
        if (blockPoint3.getRowIndex() > 0 && blockPoint4.getBlockIndex() < ((Row)this.map.rows.get(0)).blockPoints.size() - 1)
          ((BlockPoint)((Row)this.map.rows.get(blockPoint3.getRowIndex() - 1)).blockPoints.get(blockPoint4.getBlockIndex() + 1)).isPath = true; 
        if (blockPoint4.getRowIndex() < this.map.rows.size() - 1 && blockPoint4.getBlockIndex() < ((Row)this.map.rows.get(0)).blockPoints.size() - 1)
          ((BlockPoint)((Row)this.map.rows.get(blockPoint4.getRowIndex() + 1)).blockPoints.get(blockPoint4.getBlockIndex() + 1)).isPath = true; 
        if (blockPoint4.getRowIndex() < this.map.rows.size() - 1 && blockPoint3.getBlockIndex() > 0)
          ((BlockPoint)((Row)this.map.rows.get(blockPoint4.getRowIndex() + 1)).blockPoints.get(blockPoint3.getBlockIndex() - 1)).isPath = true; 
      } 
    } 
  }
  
  private void updateCharacterLocation(Character paramCharacter, BlockPoint paramBlockPoint) {
    paramCharacter.setLocation(this.dungeon.getDungeonNumber(), paramBlockPoint.absoluteIndexX, paramBlockPoint.absoluteIndexY);
  }
  
  private void updateOldBlockPoints() {
    for (MapCharacter mapCharacter : this.NPCs)
      mapCharacter.setOldBlockPoint(mapCharacter.getBlockPoint()); 
  }
  
  public boolean checkForAbsoluteIdentical(BlockPoint paramBlockPoint, Character paramCharacter) {
    return (paramCharacter.getAbsoluteIndexX() == paramBlockPoint.absoluteIndexX && paramCharacter.getAbsoluteIndexY() == paramBlockPoint.absoluteIndexY);
  }
  
  public Bitmap getResizedBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
    int j = paramBitmap.getWidth();
    int i = paramBitmap.getHeight();
    float f1 = paramInt1 / j;
    float f2 = paramInt2 / i;
    Matrix matrix = new Matrix();
    matrix.postScale(f1, f2);
    Bitmap bitmap = Bitmap.createBitmap(paramBitmap, 0, 0, j, i, matrix, false);
    paramBitmap.recycle();
    return bitmap;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.animateNPC && !this.characterEncountered)
      moveNPCs(); 
    drawMap(paramCanvas);
    drawCharacters(paramCanvas);
    drawPanel(paramCanvas);
    drawPoints(paramCanvas);
    if (this.animateMap) {
      this.canTouch = false;
      this.animateMap = false;
      this.midAnimating = true;
      this.handler.postDelayed(this.runnable, this.UPDATE_MILLIS);
    } else if (this.midAnimating) {
      this.canTouch = false;
      this.animateNPC = false;
      this.midAnimating = false;
      this.animateNPC = true;
      this.handler.postDelayed(this.runnable, this.UPDATE_MILLIS);
    } else if (this.animateNPC) {
      this.canTouch = false;
      this.animateNPC = false;
      updateOldBlockPoints();
      this.handler.postDelayed(this.runnable, this.UPDATE_MILLIS);
    } else {
      this.canTouch = true;
      drawPoints(paramCanvas);
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    this.characterEncountered = false;
    if (!this.canTouch)
      return true; 
    updateCharacterLocation(this.playerCharacter, getCentreBlock());
    this.databaseHelper.saveCharacterLocation(this.playerCharacter);
    this.touchX = (int)paramMotionEvent.getX();
    this.touchY = (int)paramMotionEvent.getY();
    if (paramMotionEvent.getAction() == 0) {
      this.canTouch = false;
      this.mapShifted = false;
      if (this.quitMessage) {
        if (this.yesRect.contains(this.touchX, this.touchY)) {
          backToMain();
        } else if (this.cancelRect.contains(this.touchX, this.touchY)) {
          quitCancel();
        } 
        this.canTouch = true;
        return true;
      } 
      if (this.upRect.contains(this.touchX, this.touchY)) {
        if (shiftMapDown())
          this.mapShifted = true; 
      } else if (this.downRect.contains(this.touchX, this.touchY)) {
        if (shiftMapUp())
          this.mapShifted = true; 
      } else if (this.leftRect.contains(this.touchX, this.touchY)) {
        if (shiftMapRight())
          this.mapShifted = true; 
      } else if (this.rightRect.contains(this.touchX, this.touchY)) {
        if (shiftMapLeft())
          this.mapShifted = true; 
      } else if (this.audioPrefRect.contains(this.touchX, this.touchY)) {
        toggleAudioPref();
        this.canTouch = true;
      } else if (this.homeButtonRect.contains(this.touchX, this.touchY)) {
        homeButtonAction();
      } 
      if (this.mapShifted) {
        updateBuildings();
        this.animateMap = true;
        resetAdjacentBlocks();
        checkForCentreBlock();
        invalidate();
      } 
      this.canTouch = true;
    } 
    return true;
  }
  
  public boolean sameCoordinates(BlockPoint paramBlockPoint1, BlockPoint paramBlockPoint2) {
    return (paramBlockPoint1 == paramBlockPoint2) ? true : ((paramBlockPoint1.x == paramBlockPoint2.x && paramBlockPoint1.y == paramBlockPoint2.y));
  }
  
  private class MapCharacter {
    private BlockPoint blockPoint;
    
    private Character character;
    
    boolean isAggressive;
    
    boolean isAlive;
    
    boolean isNPC;
    
    private BlockPoint oldBlockPoint;
    
    final DungeonView this$0;
    
    public MapCharacter(BlockPoint param1BlockPoint, boolean param1Boolean1, boolean param1Boolean2) {
      this.blockPoint = param1BlockPoint;
      this.oldBlockPoint = param1BlockPoint;
      this.isNPC = param1Boolean1;
      this.isAggressive = param1Boolean2;
      this.isAlive = true;
    }
    
    public BlockPoint getBlockPoint() {
      return this.blockPoint;
    }
    
    public Character getCharacter() {
      return this.character;
    }
    
    public BlockPoint getOldBlockPoint() {
      return this.oldBlockPoint;
    }
    
    public void kill() {
      this.isAlive = false;
    }
    
    public void setBlockPoint(BlockPoint param1BlockPoint) {
      this.blockPoint = param1BlockPoint;
    }
    
    public void setCharacter(Character param1Character) {
      this.character = param1Character;
    }
    
    public void setOldBlockPoint(BlockPoint param1BlockPoint) {
      this.oldBlockPoint = param1BlockPoint;
    }
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/DungeonView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */