package ca.spiralmachines.limbscyberpunk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BattleView extends View {
  private final int UPDATE_MILLIS = 59;
  
  private int animationCounter = 0;
  
  private Rect attackButtonRect;
  
  private int buttonHeight;
  
  private int buttonLength;
  
  private Paint buttonPaint;
  
  private Paint buttonTextPaint;
  
  private Rect cheatButtonRect;
  
  private Context context;
  
  private DatabaseHelper databaseHelper;
  
  private Dungeon dungeon;
  
  private Rect exitButtonRect;
  
  private boolean firstBlood = false;
  
  private int flashCountdown = 0;
  
  private Limb flashingLimb;
  
  private int flashingLimbAnimationCountdown = 0;
  
  private boolean flashingLimbAnimationSwitch = false;
  
  private boolean gameRunning = false;
  
  private Handler handler;
  
  private Rect healButtonRect;
  
  private boolean healLimb = false;
  
  private Limb healingLimb;
  
  private int heightOfListItem = 125;
  
  private Limb limbOpponentMoves;
  
  private boolean limbStolen = false;
  
  private Limb limbToAttack;
  
  private Limb limbToHeal;
  
  private Limb limbToSteal;
  
  private Limb limbToThrow;
  
  private ArrayList<Limb> limbsToDestroy;
  
  private String message;
  
  private Rect messageRect;
  
  private int messageTimer = 0;
  
  private Character nonPlayerCharacter;
  
  private Rect npcRect;
  
  private int oppoMovingLimbXAdder = 0;
  
  private boolean opponentAttacking = false;
  
  private boolean opponentHealing = false;
  
  private ArrayList<LimbBox> opponentLimbBoxes;
  
  private boolean opponentMoveLimbAnimation = false;
  
  private int opponentMoveLimbAnimationCounter = 0;
  
  boolean opponentStealing = false;
  
  private Paint panelPaint;
  
  private Rect panelRect;
  
  private Character playerCharacter;
  
  private ArrayList<LimbBox> playerEquippedLimbBoxes;
  
  private boolean playerFailsToRun = false;
  
  private boolean playerLimbHealed = false;
  
  private Rect playerRect;
  
  private boolean playerRunningAnimation = false;
  
  private int playerRunningCountdown = 0;
  
  private int playerRunningXSubtractor = 0;
  
  private ArrayList<LimbBox> playerSpareLimbBoxes;
  
  boolean playerStealingAnimation = false;
  
  private Random random;
  
  private Rect runButtonRect;
  
  private Runnable runnable;
  
  private int screenHeight;
  
  private int screenWidth;
  
  private boolean showAttackOptions = false;
  
  private boolean showFlashingLimbAnimation = false;
  
  private boolean showMessage = false;
  
  private Typeface spaceFont;
  
  private Typeface squareFont;
  
  private Paint statsTextPaint;
  
  int stealAnimationCountdown = 0;
  
  int stealAnimationXAdder = 0;
  
  private Rect stealButtonRect;
  
  private boolean stealLimb = false;
  
  boolean throwAnimation = false;
  
  int throwAnimationCountdown = 0;
  
  int throwAnimationXAdder = 0;
  
  private Rect throwButtonRect;
  
  private boolean throwLimb = false;
  
  private int touchX;
  
  private int touchY;
  
  private boolean victory = false;
  
  private Rect wholeScreenRect;
  
  private int yAdder = 7;
  
  public BattleView(Context paramContext, Character paramCharacter1, Character paramCharacter2, Dungeon paramDungeon, DatabaseHelper paramDatabaseHelper) {
    super(paramContext);
    this.context = paramContext;
    this.limbsToDestroy = new ArrayList<Limb>();
    this.playerCharacter = paramCharacter1;
    this.nonPlayerCharacter = paramCharacter2;
    this.dungeon = paramDungeon;
    this.databaseHelper = paramDatabaseHelper;
    this.random = new Random();
    this.handler = new Handler();
    this.opponentLimbBoxes = new ArrayList<LimbBox>();
    this.playerSpareLimbBoxes = new ArrayList<LimbBox>();
    this.playerEquippedLimbBoxes = new ArrayList<LimbBox>();
    this.gameRunning = true;
    Display display = ((Activity)paramContext).getWindowManager().getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    this.screenWidth = point.x;
    int i = point.y;
    this.screenHeight = i;
    this.buttonLength = this.screenWidth / 5;
    this.buttonHeight = i / 8;
    createRects();
    setCharacterPositions();
    createPaints();
    this.runnable = new Runnable() {
        final BattleView this$0;
        
        public void run() {
          BattleView.this.invalidate();
        }
      };
  }
  
  private void animateLimbs() {
    for (Limb limb1 : this.nonPlayerCharacter.getEquippedLimbs())
      limb1.setY(limb1.getY() + this.yAdder); 
    for (Limb limb1 : this.playerCharacter.getEquippedLimbs())
      limb1.setY(limb1.getY() - this.yAdder); 
    Limb limb = this.limbToThrow;
    if (limb != null)
      limb.setY(limb.getY() + this.yAdder); 
    limb = this.limbOpponentMoves;
    if (limb != null)
      limb.setY(limb.getY() + this.yAdder); 
  }
  
  private void attackButtonPressed() {
    if (this.throwLimb) {
      cancelThrowLimb();
    } else if (this.stealLimb) {
      cancelStealLimb();
    } else if (this.healLimb) {
      cancelHealLimb();
    } 
    if (this.showAttackOptions) {
      this.showAttackOptions = false;
    } else {
      playerPreparesToAttack();
    } 
  }
  
  private void cancelAttackLimb() {
    this.showAttackOptions = false;
  }
  
  private void cancelHealLimb() {
    this.limbToHeal = null;
    this.healingLimb = null;
    this.healLimb = false;
  }
  
  private void cancelStealLimb() {
    this.stealLimb = false;
    this.limbToSteal = null;
  }
  
  private void cancelThrowLimb() {
    this.limbToThrow = null;
    this.throwLimb = false;
    this.throwAnimation = false;
    this.throwAnimationCountdown = 0;
  }
  
  private void characterAttacksLimb(Character paramCharacter, Limb paramLimb) {
    characterAttacksLimb(paramCharacter, paramLimb, paramCharacter.getDamage());
  }
  
  private void characterAttacksLimb(Character paramCharacter, Limb paramLimb, int paramInt) {
    this.showAttackOptions = false;
    this.limbToAttack = paramLimb;
    paramLimb.setCurrentHp(paramLimb.getCurrentHp() - paramInt);
    this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
    this.databaseHelper.saveCharacter(this.playerCharacter);
    if (paramLimb.getCurrentHp() < 1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramCharacter.getName());
      stringBuilder.append(" destroys ");
      stringBuilder.append(paramLimb.getName());
      this.message = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramCharacter.getName());
      stringBuilder.append(" takes ");
      stringBuilder.append(paramInt);
      stringBuilder.append("HP from ");
      stringBuilder.append(paramLimb.getName());
      this.message = stringBuilder.toString();
    } 
    setLimbFlashingAnimation(paramLimb, this.message);
  }
  
  private void cheatButtonPressed() {
    for (Limb limb : this.nonPlayerCharacter.getEquippedLimbs()) {
      limb.setCurrentHp(0);
      limb.setEquipped(false);
    } 
    this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
    Intent intent = new Intent(this.context, PlayActivity.class);
    ((Activity)this.context).startActivity(intent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private boolean checkButtonsForTouch() {
    boolean bool1;
    boolean bool2 = false;
    if (this.runButtonRect.contains(this.touchX, this.touchY)) {
      runButtonPressed();
      bool1 = true;
    } else if (this.cheatButtonRect.contains(this.touchX, this.touchY)) {
      cheatButtonPressed();
      bool1 = true;
    } else if (this.exitButtonRect.contains(this.touchX, this.touchY)) {
      exitButtonPressed();
      bool1 = true;
    } else if (this.healButtonRect.contains(this.touchX, this.touchY)) {
      healButtonPressed();
      bool1 = true;
    } else if (this.attackButtonRect.contains(this.touchX, this.touchY)) {
      attackButtonPressed();
      bool1 = true;
    } else if (this.stealButtonRect.contains(this.touchX, this.touchY)) {
      stealButtonPressed();
      bool1 = true;
    } else if (this.throwButtonRect.contains(this.touchX, this.touchY)) {
      throwButtonPressed();
      bool1 = true;
    } else if ((this.showAttackOptions || (this.stealLimb && this.limbToSteal == null)) && checkOpponentLimbBoxesForTouch()) {
      bool1 = true;
    } else if (this.throwLimb && this.limbToThrow == null && checkPlayerSpareLimbBoxesForTouch()) {
      bool1 = true;
    } else if (this.healLimb && this.healingLimb == null && checkPlayerSpareLimbBoxesForTouch()) {
      bool1 = true;
    } else {
      bool1 = bool2;
      if (this.healLimb) {
        bool1 = bool2;
        if (this.healingLimb != null) {
          bool1 = bool2;
          if (this.limbToHeal == null) {
            bool1 = bool2;
            if (checkPlayerEquippedLimbBoxesForTouch())
              bool1 = true; 
          } 
        } 
      } 
    } 
    return bool1;
  }
  
  private boolean checkOpponentLimbBoxesForTouch() {
    Limb limb = null;
    for (LimbBox limbBox : this.opponentLimbBoxes) {
      if (limbBox.rect.contains(this.touchX, this.touchY))
        limb = limbBox.limb; 
    } 
    if (limb != null) {
      if (this.stealLimb && this.limbToSteal == null) {
        this.limbToSteal = limb;
      } else {
        characterAttacksLimb(this.playerCharacter, limb);
      } 
      return true;
    } 
    return false;
  }
  
  private boolean checkPlayerEquippedLimbBoxesForTouch() {
    Limb limb = null;
    for (LimbBox limbBox : this.playerEquippedLimbBoxes) {
      if (limbBox.rect.contains(this.touchX, this.touchY))
        limb = limbBox.limb; 
    } 
    if (this.healLimb && this.healingLimb != null && this.limbToHeal == null) {
      playerChoosesLimbToHeal(limb);
      return true;
    } 
    return false;
  }
  
  private boolean checkPlayerSpareLimbBoxesForTouch() {
    Limb limb = null;
    for (LimbBox limbBox : this.playerSpareLimbBoxes) {
      if (limbBox.rect.contains(this.touchX, this.touchY))
        limb = limbBox.limb; 
    } 
    if (this.throwLimb) {
      playerThrowsLimb(limb);
      return true;
    } 
    if (this.healLimb && this.healingLimb == null) {
      playerChoosesHealingLimb(limb);
      return true;
    } 
    return false;
  }
  
  private void concludeOpponentHealing() {
    this.opponentHealing = false;
    if (this.nonPlayerCharacter.getSpareLimbs().contains(this.healingLimb)) {
      this.nonPlayerCharacter.getSpareLimbs().remove(this.healingLimb);
      this.databaseHelper.deleteOneLimb(this.healingLimb.getId());
    } 
    this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
    this.limbToHeal = null;
    this.healingLimb = null;
  }
  
  private void concludeOpponentStealing() {
    if (this.playerCharacter.getEquippedLimbs().contains(this.limbOpponentMoves))
      this.playerCharacter.getEquippedLimbs().remove(this.limbOpponentMoves); 
    this.limbOpponentMoves.setEquipped(false);
    this.nonPlayerCharacter.getSpareLimbs().add(this.limbOpponentMoves);
    this.limbOpponentMoves.setCharacter(this.nonPlayerCharacter.getId());
    this.databaseHelper.saveCharacter(this.playerCharacter);
    this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
    this.opponentStealing = false;
    this.limbToSteal = null;
    this.limbOpponentMoves = null;
    this.opponentMoveLimbAnimation = false;
  }
  
  private void concludeRunning() {
    this.playerRunningAnimation = false;
    Intent intent = new Intent(this.context, MessageActivity.class);
    intent.putExtra("title", "You ran away!");
    intent.putExtra("body", "And lived to fight another day...");
    killOpponentLimbs();
    this.context.startActivity(intent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private void createButtonRects() {
    int i = this.screenHeight;
    this.attackButtonRect = new Rect(5, i - this.buttonHeight + 5, this.buttonLength, i - 5);
    i = this.buttonLength;
    int j = this.screenHeight;
    this.healButtonRect = new Rect(i + 5, j - this.buttonHeight + 5, i * 2, j - 5);
    i = this.buttonLength;
    j = this.screenHeight;
    this.throwButtonRect = new Rect(i * 2 + 5, j - this.buttonHeight + 5, i * 3, j - 5);
    j = this.buttonLength;
    i = this.screenHeight;
    this.stealButtonRect = new Rect(j * 3 + 5, i - this.buttonHeight + 5, j * 4, i - 5);
    j = this.buttonLength;
    i = this.screenHeight;
    this.runButtonRect = new Rect(j * 4 + 5, i - this.buttonHeight + 5, this.screenWidth - 5, i - 5);
    i = this.screenWidth;
    this.exitButtonRect = new Rect(i - this.buttonLength, 0, i, this.buttonHeight);
    this.cheatButtonRect = new Rect(0, 0, 333, 140);
  }
  
  private void createOpponentLimbBoxes() {
    this.opponentLimbBoxes = new ArrayList<LimbBox>();
    for (byte b = 0; b < this.nonPlayerCharacter.getEquippedLimbs().size(); b++) {
      int j = this.heightOfListItem;
      int i = (j + 5) * b + 5;
      Limb limb = this.nonPlayerCharacter.getEquippedLimbs().get(b);
      Rect rect = new Rect(this.npcRect.left + 10, i, this.screenWidth - 5, j + i);
      this.opponentLimbBoxes.add(new LimbBox(limb, rect));
    } 
  }
  
  private void createPaints() {
    this.panelPaint = new Paint();
    this.buttonPaint = new Paint();
    this.buttonTextPaint = new Paint();
    this.statsTextPaint = new Paint();
    this.panelPaint.setColor(-1);
    this.buttonPaint.setColor(-16777216);
    this.buttonTextPaint.setColor(Color.parseColor("#E5E500"));
    this.statsTextPaint.setColor(Color.parseColor("#664422"));
    this.spaceFont = ResourcesCompat.getFont(this.context, 2131230722);
    this.squareFont = ResourcesCompat.getFont(this.context, 2131230721);
    this.buttonTextPaint.setTextSize(43.0F);
    this.buttonTextPaint.setTypeface(this.squareFont);
  }
  
  private void createPlayerEquippedLimbBoxes() {
    this.playerEquippedLimbBoxes = new ArrayList<LimbBox>();
    for (byte b = 0; b < this.playerCharacter.getEquippedLimbs().size(); b++) {
      int i = this.heightOfListItem;
      int j = (i + 5) * b + 5;
      Limb limb = this.playerCharacter.getEquippedLimbs().get(b);
      Rect rect = new Rect(this.playerRect.left + 10, j, this.screenWidth - 5, i + j);
      this.playerEquippedLimbBoxes.add(new LimbBox(limb, rect));
    } 
  }
  
  private void createPlayerSpareLimbBoxes() {
    this.playerSpareLimbBoxes = new ArrayList<LimbBox>();
    for (byte b = 0; b < this.playerCharacter.getSpareLimbs().size(); b++) {
      int j = this.heightOfListItem;
      int i = (j + 5) * b + 5;
      Limb limb = this.playerCharacter.getSpareLimbs().get(b);
      Rect rect = new Rect(this.playerRect.left + 10, i, this.screenWidth - 5, j + i);
      this.playerSpareLimbBoxes.add(new LimbBox(limb, rect));
    } 
  }
  
  private void createRects() {
    this.wholeScreenRect = new Rect(0, 0, this.screenWidth, this.screenHeight);
    this.playerRect = new Rect(0, 0, this.screenWidth / 2, this.screenHeight);
    int i = this.screenWidth;
    this.npcRect = new Rect(i / 2, 0, i, this.screenHeight);
    i = this.screenHeight;
    this.panelRect = new Rect(0, i - i / 8, this.screenWidth, i);
    int i2 = this.screenWidth;
    int i1 = i2 / 2;
    int k = this.buttonLength;
    int m = k / 2;
    i = this.screenHeight;
    int j = i / 2;
    int n = this.buttonHeight;
    this.messageRect = new Rect(i1 - m, j - n / 2, i2 / 2 + k / 2, i / 2 + n / 2);
    createButtonRects();
  }
  
  private void defeat() {
    setMessage("DEFEAT!");
    this.victory = false;
    endGame();
  }
  
  private void drawAttackOptions(Canvas paramCanvas) {
    for (LimbBox limbBox : this.opponentLimbBoxes) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(limbBox.limb.getName());
      stringBuilder.append(" // ");
      stringBuilder.append(limbBox.limb.getCurrentHp());
      stringBuilder.append("HP");
      String str = stringBuilder.toString();
      paramCanvas.drawRect(limbBox.rect, this.buttonPaint);
      paramCanvas.drawText(str, (limbBox.rect.left + 10), (limbBox.rect.top + 50), this.buttonTextPaint);
    } 
  }
  
  private void drawCharacter(Canvas paramCanvas, Character paramCharacter) {
    for (Limb limb : paramCharacter.getEquippedLimbs()) {
      if (limb != this.flashingLimb || this.flashingLimbAnimationSwitch)
        paramCanvas.drawBitmap(limb.getImage(), limb.getX(), limb.getY(), null); 
    } 
  }
  
  private void drawEverything(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_1
    //   1: aload_0
    //   2: getfield dungeon : Lca/spiralmachines/limbscyberpunk/Dungeon;
    //   5: getfield bgBitmap : Landroid/graphics/Bitmap;
    //   8: fconst_0
    //   9: fconst_0
    //   10: aconst_null
    //   11: invokevirtual drawBitmap : (Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V
    //   14: aload_0
    //   15: getfield showAttackOptions : Z
    //   18: ifne -> 50
    //   21: aload_0
    //   22: getfield stealLimb : Z
    //   25: ifeq -> 38
    //   28: aload_0
    //   29: getfield limbToSteal : Lca/spiralmachines/limbscyberpunk/Limb;
    //   32: ifnonnull -> 38
    //   35: goto -> 50
    //   38: aload_0
    //   39: aload_1
    //   40: aload_0
    //   41: getfield nonPlayerCharacter : Lca/spiralmachines/limbscyberpunk/Character;
    //   44: invokespecial drawCharacter : (Landroid/graphics/Canvas;Lca/spiralmachines/limbscyberpunk/Character;)V
    //   47: goto -> 55
    //   50: aload_0
    //   51: aload_1
    //   52: invokespecial drawAttackOptions : (Landroid/graphics/Canvas;)V
    //   55: aload_0
    //   56: getfield throwLimb : Z
    //   59: ifeq -> 69
    //   62: aload_0
    //   63: getfield limbToThrow : Lca/spiralmachines/limbscyberpunk/Limb;
    //   66: ifnull -> 85
    //   69: aload_0
    //   70: getfield healLimb : Z
    //   73: istore_3
    //   74: iload_3
    //   75: ifeq -> 93
    //   78: aload_0
    //   79: getfield healingLimb : Lca/spiralmachines/limbscyberpunk/Limb;
    //   82: ifnonnull -> 93
    //   85: aload_0
    //   86: aload_1
    //   87: invokespecial drawPlayerSpareLimbs : (Landroid/graphics/Canvas;)V
    //   90: goto -> 128
    //   93: iload_3
    //   94: ifeq -> 119
    //   97: aload_0
    //   98: getfield healingLimb : Lca/spiralmachines/limbscyberpunk/Limb;
    //   101: ifnull -> 119
    //   104: aload_0
    //   105: getfield limbToHeal : Lca/spiralmachines/limbscyberpunk/Limb;
    //   108: ifnonnull -> 119
    //   111: aload_0
    //   112: aload_1
    //   113: invokespecial drawPlayerEquippedLimbs : (Landroid/graphics/Canvas;)V
    //   116: goto -> 128
    //   119: aload_0
    //   120: aload_1
    //   121: aload_0
    //   122: getfield playerCharacter : Lca/spiralmachines/limbscyberpunk/Character;
    //   125: invokespecial drawCharacter : (Landroid/graphics/Canvas;Lca/spiralmachines/limbscyberpunk/Character;)V
    //   128: aload_0
    //   129: getfield throwLimb : Z
    //   132: ifeq -> 172
    //   135: aload_0
    //   136: getfield limbToThrow : Lca/spiralmachines/limbscyberpunk/Limb;
    //   139: astore #4
    //   141: aload #4
    //   143: ifnull -> 172
    //   146: aload_1
    //   147: aload #4
    //   149: invokevirtual getImage : ()Landroid/graphics/Bitmap;
    //   152: aload_0
    //   153: getfield limbToThrow : Lca/spiralmachines/limbscyberpunk/Limb;
    //   156: invokevirtual getX : ()I
    //   159: i2f
    //   160: aload_0
    //   161: getfield limbToThrow : Lca/spiralmachines/limbscyberpunk/Limb;
    //   164: invokevirtual getY : ()I
    //   167: i2f
    //   168: aconst_null
    //   169: invokevirtual drawBitmap : (Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V
    //   172: aload_0
    //   173: getfield opponentMoveLimbAnimation : Z
    //   176: ifeq -> 216
    //   179: aload_0
    //   180: getfield limbOpponentMoves : Lca/spiralmachines/limbscyberpunk/Limb;
    //   183: astore #4
    //   185: aload #4
    //   187: ifnull -> 216
    //   190: aload_1
    //   191: aload #4
    //   193: invokevirtual getImage : ()Landroid/graphics/Bitmap;
    //   196: aload_0
    //   197: getfield limbOpponentMoves : Lca/spiralmachines/limbscyberpunk/Limb;
    //   200: invokevirtual getX : ()I
    //   203: i2f
    //   204: aload_0
    //   205: getfield limbOpponentMoves : Lca/spiralmachines/limbscyberpunk/Limb;
    //   208: invokevirtual getY : ()I
    //   211: i2f
    //   212: aconst_null
    //   213: invokevirtual drawBitmap : (Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V
    //   216: aload_0
    //   217: aload_1
    //   218: invokespecial drawPanel : (Landroid/graphics/Canvas;)V
    //   221: aload_0
    //   222: getfield showMessage : Z
    //   225: ifeq -> 271
    //   228: aload_0
    //   229: getfield messageTimer : I
    //   232: iconst_1
    //   233: isub
    //   234: istore_2
    //   235: aload_0
    //   236: iload_2
    //   237: putfield messageTimer : I
    //   240: iload_2
    //   241: iconst_1
    //   242: if_icmpge -> 266
    //   245: aload_0
    //   246: iconst_0
    //   247: putfield showMessage : Z
    //   250: aload_0
    //   251: getfield playerFailsToRun : Z
    //   254: ifeq -> 266
    //   257: aload_0
    //   258: iconst_0
    //   259: putfield playerFailsToRun : Z
    //   262: aload_0
    //   263: invokespecial opponentRespondsToPlayer : ()V
    //   266: aload_0
    //   267: aload_1
    //   268: invokespecial drawMessage : (Landroid/graphics/Canvas;)V
    //   271: return
  }
  
  private void drawMessage(Canvas paramCanvas) {
    paramCanvas.drawRect(this.messageRect, this.buttonPaint);
    paramCanvas.drawText(this.message, (this.messageRect.left + 15), (this.messageRect.top + this.messageRect.height() / 2), this.buttonTextPaint);
  }
  
  private void drawNumberOfOpponentLimbs(Canvas paramCanvas) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.nonPlayerCharacter.getEquippedLimbs().size());
    stringBuilder.append(" equipped limbs");
    String str = stringBuilder.toString();
    Paint paint = new Paint();
    paint.setColor(-256);
    paint.setTextSize(50.0F);
    paramCanvas.drawText(str, this.playerRect.centerX(), (this.playerRect.top + 50), paint);
  }
  
  private void drawPanel(Canvas paramCanvas) {
    paramCanvas.drawRect(this.panelRect, this.panelPaint);
    paramCanvas.drawRect(this.attackButtonRect, this.buttonPaint);
    paramCanvas.drawRect(this.healButtonRect, this.buttonPaint);
    paramCanvas.drawRect(this.throwButtonRect, this.buttonPaint);
    paramCanvas.drawRect(this.stealButtonRect, this.buttonPaint);
    paramCanvas.drawRect(this.runButtonRect, this.buttonPaint);
    paramCanvas.drawRect(this.exitButtonRect, this.buttonPaint);
    paramCanvas.drawRect(this.cheatButtonRect, this.buttonPaint);
    String str1 = "ATTACK";
    if (this.showAttackOptions)
      str1 = "CANCEL"; 
    String str2 = "STEAL LIMB";
    if (this.stealLimb)
      str2 = "CANCEL"; 
    String str3 = "THROW LIMB";
    if (this.throwLimb)
      str3 = "CANCEL"; 
    String str4 = "HEAL LIMB";
    if (this.healLimb)
      str4 = "CANCEL"; 
    paramCanvas.drawText(str1, (this.attackButtonRect.left + 15), (this.attackButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
    paramCanvas.drawText(str4, (this.healButtonRect.left + 15), (this.healButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
    paramCanvas.drawText(str3, (this.throwButtonRect.left + 15), (this.throwButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
    paramCanvas.drawText(str2, (this.stealButtonRect.left + 15), (this.stealButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
    paramCanvas.drawText("EXIT", (this.exitButtonRect.left + 15), (this.exitButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
    paramCanvas.drawText("RUN", (this.runButtonRect.left + 15), (this.runButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
    paramCanvas.drawText("WIN", (this.cheatButtonRect.left + 15), (this.cheatButtonRect.top + this.buttonHeight / 2), this.buttonTextPaint);
  }
  
  private void drawPlayerEquippedLimbs(Canvas paramCanvas) {
    for (LimbBox limbBox : this.playerEquippedLimbBoxes) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(limbBox.limb.getName());
      stringBuilder.append(" // ");
      stringBuilder.append(limbBox.limb.getCurrentHp());
      stringBuilder.append("HP");
      String str = stringBuilder.toString();
      paramCanvas.drawRect(limbBox.rect, this.buttonPaint);
      paramCanvas.drawText(str, (limbBox.rect.left + 10), (limbBox.rect.top + 50), this.buttonTextPaint);
    } 
  }
  
  private void drawPlayerSpareLimbs(Canvas paramCanvas) {
    for (LimbBox limbBox : this.playerSpareLimbBoxes) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(limbBox.limb.getName());
      stringBuilder.append(" // ");
      stringBuilder.append(limbBox.limb.getCurrentHp());
      stringBuilder.append("HP");
      String str = stringBuilder.toString();
      paramCanvas.drawRect(limbBox.rect, this.buttonPaint);
      paramCanvas.drawText(str, (limbBox.rect.left + 10), (limbBox.rect.top + 50), this.buttonTextPaint);
    } 
  }
  
  private void endGame() {
    String str1;
    String str2;
    this.gameRunning = false;
    saveCharacters();
    Intent intent = new Intent(getContext(), MessageActivity.class);
    if (this.victory) {
      str1 = "VICTORY";
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("You destroyed all of ");
      stringBuilder.append(this.nonPlayerCharacter.getName());
      stringBuilder.append("'s Limbs.");
      str2 = stringBuilder.toString();
    } else {
      str1 = "DEFEAT";
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.nonPlayerCharacter.getName());
      stringBuilder.append(" destroyed all of your Limbs.");
      str2 = stringBuilder.toString();
    } 
    intent.putExtra("title", str1);
    intent.putExtra("body", str2);
    intent.putExtra("victory", this.victory);
    ((Activity)this.context).startActivity(intent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private void exitButtonPressed() {
    this.gameRunning = false;
    Intent intent = new Intent(getContext(), PlayActivity.class);
    ((Activity)this.context).startActivity(intent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private void giveOpponentSpareLimbs() {
    Limb limb = LimbFactory.limb000(this.context, this.nonPlayerCharacter.getId());
    this.nonPlayerCharacter.getSpareLimbs().add(limb);
    limb = LimbFactory.limb001(this.context, this.nonPlayerCharacter.getId());
    this.nonPlayerCharacter.getSpareLimbs().add(limb);
    limb = LimbFactory.limb002(this.context, this.nonPlayerCharacter.getId());
    this.nonPlayerCharacter.getSpareLimbs().add(limb);
  }
  
  private void healButtonPressed() {
    if (this.throwLimb) {
      cancelThrowLimb();
    } else if (this.stealLimb) {
      cancelStealLimb();
    } else if (this.showAttackOptions) {
      cancelAttackLimb();
    } 
    if (this.playerCharacter.getSpareLimbs().size() < 1) {
      setMessage("NO SPARE LIMBS");
    } else if (this.healLimb) {
      cancelHealLimb();
    } else {
      playerPreparesToHealLimb();
    } 
  }
  
  private void injuryConsequences() {
    if (this.limbToAttack.getCurrentHp() < 1) {
      this.limbsToDestroy.add(this.limbToAttack);
      this.limbToAttack = null;
    } else {
      this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
      this.databaseHelper.saveCharacter(this.playerCharacter);
    } 
  }
  
  private void killOpponentLimbs() {
    for (Limb limb : this.nonPlayerCharacter.getEquippedLimbs()) {
      limb.setCurrentHp(0);
      limb.setEquipped(false);
    } 
    this.nonPlayerCharacter.setAlive(false);
    this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
  }
  
  private boolean opponentAttacksPlayer() {
    if (this.playerCharacter.getEquippedLimbs().size() < 1)
      return false; 
    this.opponentAttacking = true;
    int i = this.random.nextInt(this.playerCharacter.getEquippedLimbs().size());
    Limb limb = this.playerCharacter.getEquippedLimbs().get(i);
    this.limbToAttack = limb;
    characterAttacksLimb(this.nonPlayerCharacter, limb);
    return false;
  }
  
  private void opponentAttemptsToHeal() {
    StringBuilder stringBuilder2;
    ArrayList<Limb> arrayList2 = this.nonPlayerCharacter.getEquippedLimbs();
    ArrayList<Limb> arrayList1 = this.nonPlayerCharacter.getSpareLimbs();
    if (arrayList2.size() < 1 || arrayList1.size() < 1) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(this.nonPlayerCharacter.getName());
      stringBuilder2.append(" FAILS TO HEAL.");
      setMessage(stringBuilder2.toString());
      return;
    } 
    this.healingLimb = stringBuilder2.get(this.random.nextInt(stringBuilder2.size()));
    this.limbToHeal = arrayList2.get(this.random.nextInt(arrayList2.size()));
    int j = this.healingLimb.getCurrentHp() / 2;
    int i = j;
    if (j < 2)
      i = 2; 
    Limb limb = this.limbToHeal;
    limb.setCurrentHp(limb.getCurrentHp() + i);
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(this.nonPlayerCharacter.getName());
    stringBuilder1.append(" HEALED ");
    stringBuilder1.append(i);
    stringBuilder1.append("HP");
    String str = stringBuilder1.toString();
    setLimbFlashingAnimation(this.limbToHeal, str);
    this.opponentHealing = true;
  }
  
  private void opponentAttemptsToSteal() {
    ArrayList<Limb> arrayList1 = this.playerCharacter.getEquippedLimbs();
    ArrayList<Limb> arrayList2 = this.nonPlayerCharacter.getEquippedLimbs();
    if (arrayList1.size() < 1 || arrayList2.size() < 1) {
      setMessage("NO LIMBS TO STEAL", 19);
      return;
    } 
    if (this.random.nextInt(2) == 0) {
      opponentSteals(arrayList1, arrayList2);
    } else {
      opponentFailsToSteal();
    } 
  }
  
  private void opponentAttemptsToThrow() {
    giveOpponentSpareLimbs();
    ArrayList<Limb> arrayList2 = this.nonPlayerCharacter.getSpareLimbs();
    ArrayList<Limb> arrayList1 = this.nonPlayerCharacter.getEquippedLimbs();
    int i = this.playerCharacter.getEquippedLimbs().size();
    if (arrayList2.size() < 1 || i < 1 || arrayList1.size() < 1) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.nonPlayerCharacter.getName());
      stringBuilder1.append(" FAILS TO THROW.");
      setMessage(stringBuilder1.toString());
      return;
    } 
    this.opponentAttacking = true;
    Limb limb1 = this.playerCharacter.getEquippedLimbs().get(this.random.nextInt(i));
    this.opponentMoveLimbAnimation = true;
    this.opponentMoveLimbAnimationCounter = 25;
    Limb limb2 = arrayList2.get(this.random.nextInt(arrayList2.size()));
    this.limbOpponentMoves = limb2;
    limb2.setX(((Limb)arrayList1.get(0)).getX());
    this.limbOpponentMoves.setY(((Limb)arrayList1.get(0)).getY());
    this.oppoMovingLimbXAdder = (this.limbOpponentMoves.getX() - limb1.getX()) / this.opponentMoveLimbAnimationCounter;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.nonPlayerCharacter.getName());
    stringBuilder.append(" THROWs ");
    stringBuilder.append(this.limbOpponentMoves.getName());
    setMessage(stringBuilder.toString(), 25);
  }
  
  private void opponentFailsToSteal() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.nonPlayerCharacter.getName());
    stringBuilder.append(" FAILS TO STEAL");
    setMessage(stringBuilder.toString());
  }
  
  private void opponentRespondsToPlayer() {
    int k = 1;
    int j = 2;
    boolean bool = false;
    ArrayList<Limb> arrayList1 = this.nonPlayerCharacter.getSpareLimbs();
    ArrayList<Limb> arrayList2 = this.nonPlayerCharacter.getEquippedLimbs();
    if (arrayList1.size() > arrayList2.size()) {
      if (arrayList1.size() >= arrayList2.size() * 2) {
        i = 1 + 3;
        j = 2 + 2;
      } else {
        i = 1 + 2;
        j = 2 + 2;
      } 
      bool = true;
      k = i;
    } 
    if (totalBaseHP(arrayList2) > totalCurrentHP(arrayList2) * 2 && bool) {
      i = j + 5;
    } else {
      i = j;
      if (totalBaseHP(arrayList2) > totalCurrentHP(arrayList2))
        i = j + 1; 
    } 
    arrayList1 = new ArrayList<Limb>();
    for (j = 0; j < 19; j++)
      arrayList1.add(OpponentResponse.ATTACK); 
    for (j = 0; j < 2; j++)
      arrayList1.add(OpponentResponse.STEAL); 
    for (j = 0; j < k; j++)
      arrayList1.add(OpponentResponse.THROW); 
    for (j = 0; j < i; j++)
      arrayList1.add(OpponentResponse.HEAL); 
    OpponentResponse opponentResponse = (OpponentResponse)arrayList1.get(this.random.nextInt(arrayList1.size()));
    int i = null.$SwitchMap$ca$spiralmachines$limbscyberpunk$BattleView$OpponentResponse[opponentResponse.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 4)
            opponentAttemptsToThrow(); 
        } else {
          opponentAttacksPlayer();
        } 
      } else {
        opponentAttemptsToSteal();
      } 
    } else {
      opponentAttemptsToHeal();
    } 
  }
  
  private void opponentSteals(ArrayList<Limb> paramArrayList1, ArrayList<Limb> paramArrayList2) {
    this.limbToSteal = paramArrayList1.get(this.random.nextInt(paramArrayList1.size()));
    Limb limb1 = paramArrayList2.get(0);
    this.opponentMoveLimbAnimation = true;
    this.opponentMoveLimbAnimationCounter = 25;
    this.opponentStealing = true;
    Limb limb2 = this.limbToSteal;
    this.limbOpponentMoves = limb2;
    int j = limb2.getX();
    int i = limb1.getX();
    int k = this.opponentMoveLimbAnimationCounter;
    this.oppoMovingLimbXAdder = (j - i) / k;
    setMessage("Opponent STEALS LIMB", k);
  }
  
  private void playerAttemptsToStealLimb() {
    stealLimbDecision();
  }
  
  private void playerChoosesHealingLimb(Limb paramLimb) {
    this.healingLimb = paramLimb;
    createPlayerEquippedLimbBoxes();
  }
  
  private void playerChoosesLimbToHeal(Limb paramLimb) {
    this.limbToHeal = paramLimb;
  }
  
  private void playerFailsToRun() {
    this.playerFailsToRun = true;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.playerCharacter.getName());
    stringBuilder.append(" FAILS TO RUN!");
    setMessage(stringBuilder.toString(), 23);
  }
  
  private void playerFailsToStealLimb() {
    setLimbFlashingAnimation(this.limbToSteal, "Failed to Steal Limb");
    cancelStealLimb();
  }
  
  private void playerHealsLimb() {
    Limb limb = this.limbToHeal;
    limb.setCurrentHp(limb.getCurrentHp() + this.healingLimb.getCurrentHp() / 2);
    setLimbFlashingAnimation(this.limbToHeal, "LIMB HEALED");
    cancelHealLimb();
    this.playerLimbHealed = true;
  }
  
  private boolean playerPreparesToAttack() {
    if (this.nonPlayerCharacter.getEquippedLimbs().size() < 1) {
      victory();
      return true;
    } 
    createOpponentLimbBoxes();
    this.showAttackOptions = true;
    return false;
  }
  
  private void playerPreparesToHealLimb() {
    this.healLimb = true;
    this.limbToHeal = null;
    this.healingLimb = null;
    createPlayerSpareLimbBoxes();
  }
  
  private void playerPreparesToStealLimb() {
    this.stealLimb = true;
    this.limbToSteal = null;
    createOpponentLimbBoxes();
  }
  
  private void playerPreparesToThrowLimb() {
    this.limbToThrow = null;
    this.throwLimb = true;
    createPlayerSpareLimbBoxes();
  }
  
  private void playerRuns() {
    this.playerRunningAnimation = true;
    this.playerRunningCountdown = 30;
    this.playerRunningXSubtractor = (this.playerRect.centerX() + this.playerCharacter.getWidth()) / this.playerRunningCountdown;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.playerCharacter.getName());
    stringBuilder.append(" runs away!");
    setMessage(stringBuilder.toString());
  }
  
  private void playerStealsLimb() {
    this.nonPlayerCharacter.getEquippedLimbs().remove(this.limbToSteal);
    this.playerCharacter.getSpareLimbs().add(this.limbToSteal);
    this.limbToSteal.setCharacter(this.playerCharacter.getId());
    this.limbToSteal.setEquipped(false);
    this.databaseHelper.saveCharacter(this.playerCharacter);
    this.limbToSteal = null;
    this.limbStolen = false;
    this.stealLimb = false;
    this.playerStealingAnimation = false;
  }
  
  private void playerThrowsLimb(Limb paramLimb) {
    if (this.nonPlayerCharacter.getEquippedLimbs().size() < 1 || this.playerCharacter.getEquippedLimbs().size() < 1) {
      cancelThrowLimb();
      return;
    } 
    Limb limb = this.nonPlayerCharacter.getEquippedLimbs().get(0);
    this.limbToThrow = paramLimb;
    paramLimb.setX(this.playerRect.centerX());
    this.limbToThrow.setY(((Limb)this.playerCharacter.getEquippedLimbs().get(0)).getY());
    this.throwAnimation = true;
    this.throwAnimationCountdown = 15;
    this.throwAnimationXAdder = (limb.getX() - paramLimb.getX()) / this.throwAnimationCountdown;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.playerCharacter.getName());
    stringBuilder.append(" throws ");
    stringBuilder.append(this.limbToThrow.getName());
    setMessage(stringBuilder.toString());
  }
  
  private void playerWinsTheSteal() {
    this.stealLimb = false;
    this.limbStolen = true;
    startStealAnimation();
  }
  
  private void processEverythingBeforeDrawing() {
    if (this.limbsToDestroy.size() > 0) {
      for (Limb limb : this.limbsToDestroy) {
        if (this.nonPlayerCharacter.getEquippedLimbs().contains(limb)) {
          this.nonPlayerCharacter.getEquippedLimbs().remove(limb);
        } else if (this.playerCharacter.getEquippedLimbs().contains(limb)) {
          this.playerCharacter.getEquippedLimbs().remove(limb);
        } else if (this.nonPlayerCharacter.getSpareLimbs().contains(limb)) {
          this.nonPlayerCharacter.getSpareLimbs().remove(limb);
        } else if (this.playerCharacter.getSpareLimbs().contains(limb)) {
          this.playerCharacter.getSpareLimbs().remove(limb);
        } 
        this.databaseHelper.deleteOneLimb(limb.getId());
      } 
      this.limbsToDestroy.clear();
    } else if (this.stealLimb && this.limbToSteal != null && !this.showFlashingLimbAnimation) {
      playerAttemptsToStealLimb();
    } else if (this.throwLimb && this.limbToThrow != null) {
      progressThrowAnimation();
    } else if (this.healLimb && this.limbToHeal != null && this.healingLimb != null) {
      playerHealsLimb();
    } 
    if (this.nonPlayerCharacter.getEquippedLimbs().size() < 1) {
      if (this.firstBlood) {
        victory();
      } else {
        setMessage("OPPONENT DISAPPEARED FOR NO REASON!");
      } 
    } else if (this.playerCharacter.getEquippedLimbs().size() < 1) {
      defeat();
    } 
  }
  
  private void progressFlashingAnimation() {
    int j = this.flashCountdown - 1;
    this.flashCountdown = j;
    int i = this.flashingLimbAnimationCountdown - 1;
    this.flashingLimbAnimationCountdown = i;
    if (j < 1) {
      this.flashCountdown = 3;
      this.flashingLimbAnimationSwitch ^= 0x1;
    } 
    if (i < 1) {
      this.showFlashingLimbAnimation = false;
      if (!this.limbStolen) {
        if (this.playerLimbHealed || this.opponentHealing) {
          this.playerLimbHealed = false;
        } else if (this.limbToAttack != null) {
          injuryConsequences();
        } 
        if (this.opponentHealing) {
          concludeOpponentHealing();
        } else if (this.opponentAttacking) {
          this.opponentAttacking = false;
        } else {
          this.flashingLimb = null;
          opponentRespondsToPlayer();
          return;
        } 
      } 
      this.flashingLimb = null;
    } 
  }
  
  private void progressMainAnimation() {
    int i = this.animationCounter + 1;
    this.animationCounter = i;
    if (i > 8) {
      this.animationCounter = 0;
      this.yAdder *= -1;
    } 
    animateLimbs();
  }
  
  private void progressOppoMovingLimbAnimation() {
    this.opponentMoveLimbAnimationCounter--;
    Limb limb = this.limbOpponentMoves;
    limb.setX(limb.getX() - this.oppoMovingLimbXAdder);
    if (this.opponentMoveLimbAnimationCounter < 1) {
      this.opponentMoveLimbAnimation = false;
      if (this.opponentStealing) {
        concludeOpponentStealing();
      } else {
        this.databaseHelper.deleteOneLimb(this.limbOpponentMoves.getId());
        opponentAttacksPlayer();
        this.opponentAttacking = true;
      } 
    } 
  }
  
  private void progressPlayerRunningAnimation() {
    int i = this.playerRunningCountdown - 1;
    this.playerRunningCountdown = i;
    if (i < 1) {
      concludeRunning();
      return;
    } 
    for (Limb limb : this.playerCharacter.getEquippedLimbs())
      limb.setX(limb.getX() - this.playerRunningXSubtractor); 
  }
  
  private void progressStealAnimation() {
    Limb limb = this.limbToSteal;
    limb.setX(limb.getX() - this.stealAnimationXAdder);
    int i = this.stealAnimationCountdown - 1;
    this.stealAnimationCountdown = i;
    if (i < 1) {
      playerStealsLimb();
      opponentRespondsToPlayer();
    } 
  }
  
  private void progressThrowAnimation() {
    Limb limb = this.limbToThrow;
    limb.setX(limb.getX() + this.throwAnimationXAdder);
    int i = this.throwAnimationCountdown - 1;
    this.throwAnimationCountdown = i;
    if (i < 1)
      thrownLimbHitsOpponent(); 
  }
  
  private void runButtonPressed() {
    if (this.random.nextInt(2) == 0) {
      playerRuns();
    } else {
      playerFailsToRun();
    } 
  }
  
  private void saveCharacters() {
    savePlayerCharacter();
    saveNonPlayerCharacter();
  }
  
  private boolean saveNonPlayerCharacter() {
    this.databaseHelper.saveCharacter(this.nonPlayerCharacter);
    return true;
  }
  
  private boolean savePlayerCharacter() {
    this.databaseHelper.saveCharacter(this.playerCharacter);
    return true;
  }
  
  private void setCharacterPositions() {
    this.nonPlayerCharacter.baseCoordinatesOnZero();
    this.playerCharacter.baseCoordinatesOnZero();
    this.nonPlayerCharacter.setWidthAndHeight();
    this.playerCharacter.setWidthAndHeight();
    int k = this.playerRect.centerX();
    int j = this.playerCharacter.getWidth() / 2;
    int m = this.playerRect.centerY();
    int i = this.playerCharacter.getHeight();
    for (Limb limb : this.playerCharacter.getEquippedLimbs()) {
      limb.setX(limb.getX() + k - j);
      limb.setY(limb.getY() + m - i);
    } 
    j = this.npcRect.centerX();
    i = this.nonPlayerCharacter.getWidth() / 2;
    k = this.npcRect.centerY();
    m = this.nonPlayerCharacter.getHeight();
    for (Limb limb : this.nonPlayerCharacter.getEquippedLimbs()) {
      limb.setX(limb.getX() + j - i);
      limb.setY(limb.getY() + k - m);
    } 
  }
  
  private void setLimbFlashingAnimation(Limb paramLimb, String paramString) {
    this.flashingLimb = paramLimb;
    this.showFlashingLimbAnimation = true;
    this.flashingLimbAnimationCountdown = 30;
    this.flashCountdown = 3;
    setMessage(paramString);
  }
  
  private void setMessage(String paramString) {
    setMessage(paramString, 29);
  }
  
  private void setMessage(String paramString, int paramInt) {
    this.message = paramString;
    this.messageTimer = paramInt;
    this.showMessage = true;
  }
  
  private void startStealAnimation() {
    if (this.playerCharacter.getEquippedLimbs().size() < 1) {
      cancelStealLimb();
      return;
    } 
    Limb limb = this.playerCharacter.getEquippedLimbs().get(0);
    this.playerStealingAnimation = true;
    this.stealAnimationCountdown = 19;
    int k = this.limbToSteal.getX();
    int j = limb.getX();
    int i = this.stealAnimationCountdown;
    this.stealAnimationXAdder = (k - j) / i;
    setMessage("Player Steals LIMB", i);
  }
  
  private void stealButtonPressed() {
    if (this.showAttackOptions) {
      cancelAttackLimb();
    } else if (this.throwLimb) {
      cancelThrowLimb();
    } else if (this.healLimb) {
      cancelHealLimb();
    } 
    if (this.stealLimb) {
      this.stealLimb = false;
      this.limbToSteal = null;
    } else {
      playerPreparesToStealLimb();
    } 
  }
  
  private void stealLimbDecision() {
    float f2 = (this.limbToSteal.getBaseHp() / this.limbToSteal.getCurrentHp());
    float f3 = (this.playerCharacter.getIntelligence() / this.nonPlayerCharacter.getIntelligence());
    float f1 = (this.random.nextInt(4) + 19) / (this.random.nextInt(4) + 19);
    if (this.random.nextInt(this.limbToSteal.getBaseHp()) > f2 * f3 * f1) {
      playerFailsToStealLimb();
    } else {
      playerWinsTheSteal();
    } 
  }
  
  private void throwButtonPressed() {
    if (this.showAttackOptions) {
      cancelAttackLimb();
    } else if (this.stealLimb) {
      cancelStealLimb();
    } else if (this.healLimb) {
      cancelHealLimb();
    } 
    if (this.playerCharacter.getSpareLimbs().size() < 1) {
      setMessage("NO SPARE LIMBS");
    } else if (this.throwLimb) {
      cancelThrowLimb();
    } else {
      playerPreparesToThrowLimb();
    } 
  }
  
  private void thrownLimbHitsOpponent() {
    int i = this.random.nextInt(this.nonPlayerCharacter.getEquippedLimbs().size());
    Limb limb = this.nonPlayerCharacter.getEquippedLimbs().get(i);
    if (this.limbToThrow.getCurrentHp() < 2)
      this.limbToThrow.setCurrentHp(2); 
    i = this.limbToThrow.getCurrentHp() / 2;
    characterAttacksLimb(this.playerCharacter, limb, i);
    this.playerCharacter.getSpareLimbs().remove(this.limbToThrow);
    this.databaseHelper.deleteOneLimb(this.limbToThrow.getId());
    cancelThrowLimb();
  }
  
  private void victory() {
    setMessage("VICTORY!");
    this.victory = true;
    this.nonPlayerCharacter.setAlive(false);
    endGame();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.gameRunning)
      processEverythingBeforeDrawing(); 
    drawEverything(paramCanvas);
    if (this.gameRunning) {
      if (this.showFlashingLimbAnimation)
        progressFlashingAnimation(); 
      if (this.playerStealingAnimation)
        progressStealAnimation(); 
      if (this.opponentMoveLimbAnimation)
        progressOppoMovingLimbAnimation(); 
      if (this.playerRunningAnimation)
        progressPlayerRunningAnimation(); 
    } 
    progressMainAnimation();
    this.handler.postDelayed(this.runnable, 59L);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (this.showFlashingLimbAnimation || (this.throwLimb && this.limbToThrow != null) || this.showMessage)
      return true; 
    if (!this.firstBlood)
      this.firstBlood = true; 
    this.touchX = (int)paramMotionEvent.getX();
    this.touchY = (int)paramMotionEvent.getY();
    if (paramMotionEvent.getAction() == 0)
      checkButtonsForTouch(); 
    return true;
  }
  
  public int totalBaseHP(ArrayList<Limb> paramArrayList) {
    int i = 0;
    Iterator<Limb> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      i += ((Limb)iterator.next()).getBaseHp(); 
    return i;
  }
  
  public int totalCurrentHP(ArrayList<Limb> paramArrayList) {
    int i = 0;
    Iterator<Limb> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      i += ((Limb)iterator.next()).getCurrentHp(); 
    return i;
  }
  
  private class LimbBox {
    public Limb limb;
    
    public Rect rect;
    
    final BattleView this$0;
    
    private LimbBox(Limb param1Limb, Rect param1Rect) {
      this.limb = param1Limb;
      this.rect = param1Rect;
    }
  }
  
  private enum OpponentResponse {
    ATTACK, HEAL, STEAL, THROW;
    
    private static final OpponentResponse[] $VALUES;
    
    static {
      OpponentResponse opponentResponse2 = new OpponentResponse("ATTACK", 0);
      ATTACK = opponentResponse2;
      OpponentResponse opponentResponse1 = new OpponentResponse("THROW", 1);
      THROW = opponentResponse1;
      OpponentResponse opponentResponse4 = new OpponentResponse("STEAL", 2);
      STEAL = opponentResponse4;
      OpponentResponse opponentResponse3 = new OpponentResponse("HEAL", 3);
      HEAL = opponentResponse3;
      $VALUES = new OpponentResponse[] { opponentResponse2, opponentResponse1, opponentResponse4, opponentResponse3 };
    }
  }
}
