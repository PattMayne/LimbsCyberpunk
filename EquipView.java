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

public class EquipView extends View {
  static int screenHeight;
  
  static int screenWidth;
  
  long UPDATE_MILLIS = 55L;
  
  int buttonColor = Color.parseColor("#1c42ac");
  
  Paint buttonTextPaint;
  
  int clipSpaceBgColor = Color.parseColor("#ffd6b1");
  
  Paint clipSpacePaint;
  
  Rect clipSpaceRect;
  
  Context context;
  
  Joint currentEquippedJoint;
  
  Limb currentEquippedJointLimb;
  
  Joint currentLoadedJoint;
  
  DatabaseHelper databaseHelper;
  
  Rect equipOrSaveButtonRect;
  
  int equippedJointIndex = -1;
  
  int equippedLimbIndex = -1;
  
  ArrayList<Limb> equippedLimbs;
  
  Rect exitButtonRect;
  
  Paint greenPaint;
  
  int halfMeasure = 100;
  
  Handler handler;
  
  int inventoryBgColor = Color.parseColor("#698b6a");
  
  Paint jointPaint;
  
  int limbLength = 100;
  
  int loadedJointIndex = -1;
  
  Limb loadedLimb;
  
  Rect nextEquippedLimbJointButtonRect;
  
  Rect nextLoadedLimbJointButtonRect;
  
  int panelBgColor = Color.parseColor("#ffd6b1");
  
  Paint panelPaint;
  
  Rect panelRect;
  
  Character playerCharacter;
  
  boolean printSaved = false;
  
  Random random;
  
  Rect resetButtonRect;
  
  Runnable runnable;
  
  boolean saved = false;
  
  Typeface spaceFont;
  
  ArrayList<Limb> spareLimbs;
  
  Typeface squareFont;
  
  int textDarkColor = Color.parseColor("#180b00");
  
  int textLiteColor = Color.parseColor("#f3e7dc");
  
  Paint textPaint;
  
  int touchX;
  
  int touchY;
  
  Rect unloadButtonRect;
  
  Rect wholeScreenRect;
  
  public EquipView(Context paramContext) {
    super(paramContext);
    this.context = paramContext;
    this.databaseHelper = new DatabaseHelper(paramContext);
    this.random = new Random();
    this.handler = new Handler();
    this.playerCharacter = this.databaseHelper.getCharacter(1);
    Display display = ((Activity)paramContext).getWindowManager().getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    screenWidth = point.x;
    screenHeight = point.y;
    this.limbLength = screenWidth / 6 - 20;
    this.clipSpacePaint = new Paint();
    this.panelPaint = new Paint();
    this.textPaint = new Paint();
    this.greenPaint = new Paint();
    this.jointPaint = new Paint();
    this.buttonTextPaint = new Paint();
    this.spaceFont = ResourcesCompat.getFont(paramContext, 2131230722);
    Typeface typeface = ResourcesCompat.getFont(paramContext, 2131230721);
    this.squareFont = typeface;
    this.buttonTextPaint.setTypeface(typeface);
    this.clipSpacePaint.setColor(this.clipSpaceBgColor);
    this.panelPaint.setColor(this.panelBgColor);
    this.clipSpacePaint.setColor(-16776961);
    this.textPaint.setColor(this.textLiteColor);
    this.textPaint.setTextSize(96.0F);
    this.greenPaint.setColor(-16711936);
    this.buttonTextPaint.setColor(-16777216);
    this.buttonTextPaint.setTextSize(43.0F);
    createRects();
    getInventory();
    this.runnable = new Runnable() {
        final EquipView this$0;
        
        public void run() {
          EquipView.this.invalidate();
        }
      };
  }
  
  private boolean checkButtonsForTouch() {
    if (this.equipOrSaveButtonRect.contains(this.touchX, this.touchY)) {
      if (limbLoaded()) {
        equipLimb(this.loadedLimb);
      } else if (limbEquipped()) {
        save();
      } 
      return true;
    } 
    if (this.exitButtonRect.contains(this.touchX, this.touchY)) {
      exit();
      return true;
    } 
    if (this.resetButtonRect.contains(this.touchX, this.touchY)) {
      resetAll();
      return true;
    } 
    if (this.nextLoadedLimbJointButtonRect.contains(this.touchX, this.touchY)) {
      nextLoadedJoint();
      return true;
    } 
    if (this.nextEquippedLimbJointButtonRect.contains(this.touchX, this.touchY)) {
      nextEquippedJoint();
      return true;
    } 
    if (this.unloadButtonRect.contains(this.touchX, this.touchY)) {
      unloadLimb();
      return true;
    } 
    return false;
  }
  
  private boolean checkInventoryForTouch() {
    for (Limb limb : this.spareLimbs) {
      if (limb.getRect().contains(this.touchX, this.touchY)) {
        loadLimb(limb);
        return true;
      } 
    } 
    if (this.clipSpaceRect.contains(this.touchX, this.touchY))
      unloadLimb(); 
    return false;
  }
  
  private void createRects() {
    this.wholeScreenRect = new Rect(0, 0, screenWidth, screenHeight);
    int j = screenHeight;
    int i = screenWidth;
    int k = screenWidth;
    this.panelRect = new Rect(0, k, k, screenHeight);
    k = screenWidth;
    this.clipSpaceRect = new Rect(0, 0, k, k);
    k = screenWidth;
    this.equipOrSaveButtonRect = new Rect(k - 190, k - 100, k, k);
    k = screenWidth;
    this.resetButtonRect = new Rect(0, k - 100, 190, k);
    k = (screenWidth - 40) / 3;
    i = (j - i - 40) / 3;
    j = screenHeight;
    this.nextEquippedLimbJointButtonRect = new Rect(10, j - i - 10, k + 10, j - 10);
    this.nextLoadedLimbJointButtonRect = new Rect(10, screenWidth + i + 15, k + 10, screenHeight - i - 15);
    j = screenWidth;
    int m = screenHeight;
    this.unloadButtonRect = new Rect(j - k - 10, m - i - 10, j - 10, m - 10);
    i = screenWidth;
    this.exitButtonRect = new Rect(i - 190, 0, i, 100);
  }
  
  private void drawEquippedLimbs(Canvas paramCanvas) {
    for (Limb limb : this.equippedLimbs)
      paramCanvas.drawBitmap(limb.getImage(), limb.getX(), limb.getY(), null); 
  }
  
  private void drawExitButton(Canvas paramCanvas) {
    paramCanvas.drawRect(this.exitButtonRect, this.greenPaint);
    paramCanvas.drawText("EXIT", (this.exitButtonRect.left + 10), (this.exitButtonRect.bottom - 10), this.buttonTextPaint);
  }
  
  private void drawInventory(Canvas paramCanvas) {
    for (Limb limb : this.spareLimbs) {
      if (limb != this.loadedLimb)
        paramCanvas.drawBitmap(limb.getThumb(), limb.getX(), limb.getY(), null); 
    } 
  }
  
  private void drawJoints(Canvas paramCanvas) {
    for (Limb limb : this.equippedLimbs) {
      for (Joint joint : limb.getJoints()) {
        if (joint.isFree()) {
          this.jointPaint.setColor(-16711936);
        } else {
          this.jointPaint.setColor(-65536);
        } 
        paramCanvas.drawCircle((limb.getX() + joint.getX()), (limb.getY() + joint.getY()), 10.0F, this.jointPaint);
      } 
    } 
  }
  
  private void drawLoadedLimb(Canvas paramCanvas) {
    Limb limb = this.loadedLimb;
    if (limb != null) {
      paramCanvas.drawBitmap(limb.getImage(), this.loadedLimb.getX(), this.loadedLimb.getY(), null);
      paramCanvas.drawRect(this.equipOrSaveButtonRect, this.greenPaint);
      paramCanvas.drawText("EQUIP", (this.equipOrSaveButtonRect.left + 10), (this.equipOrSaveButtonRect.bottom - 10), this.buttonTextPaint);
    } 
  }
  
  private void drawLoadedPanel(Canvas paramCanvas) {
    paramCanvas.drawRect(this.nextLoadedLimbJointButtonRect, this.greenPaint);
    paramCanvas.drawText("NEXT LOADED JOINT", this.nextLoadedLimbJointButtonRect.left, (this.nextLoadedLimbJointButtonRect.bottom - 10), this.buttonTextPaint);
    paramCanvas.drawRect(this.nextEquippedLimbJointButtonRect, this.greenPaint);
    paramCanvas.drawText("NEXT EQUIPPED JOINT", this.nextEquippedLimbJointButtonRect.left, (this.nextEquippedLimbJointButtonRect.bottom - 10), this.buttonTextPaint);
    paramCanvas.drawRect(this.unloadButtonRect, this.greenPaint);
    paramCanvas.drawText("UNLOAD", (this.unloadButtonRect.left + 10), (this.unloadButtonRect.bottom - 10), this.buttonTextPaint);
  }
  
  private void drawSaveButton(Canvas paramCanvas) {
    paramCanvas.drawRect(this.equipOrSaveButtonRect, this.greenPaint);
    paramCanvas.drawText("SAVE", (this.equipOrSaveButtonRect.left + 10), (this.equipOrSaveButtonRect.bottom - 10), this.buttonTextPaint);
  }
  
  private void drawTestStats(Canvas paramCanvas) {
    String str = "Joints: ";
    this.textPaint.setTextSize(50.0F);
    Iterator<Limb> iterator = this.equippedLimbs.iterator();
    while (iterator.hasNext()) {
      for (Joint joint : ((Limb)iterator.next()).getJoints()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("//x=");
        stringBuilder.append(joint.getX());
        stringBuilder.append(", y=");
        stringBuilder.append(joint.getY());
        str = stringBuilder.toString();
      } 
    } 
    this.textPaint.setColor(-16777216);
    paramCanvas.drawText(str, 55.0F, (screenHeight - 55), this.textPaint);
  }
  
  private void equipLimb(Limb paramLimb) {
    if (this.spareLimbs.contains(paramLimb)) {
      this.equippedLimbs.add(paramLimb);
      this.spareLimbs.remove(paramLimb);
      this.loadedLimb = null;
      paramLimb.setEquipped(true);
      Joint joint = this.currentLoadedJoint;
      if (joint != null)
        joint.setFree(false); 
      joint = this.currentEquippedJoint;
      if (joint != null)
        joint.setFree(false); 
    } 
  }
  
  private void exit() {
    Intent intent = new Intent(getContext(), PlayActivity.class);
    ((Activity)this.context).startActivity(intent);
    ((Activity)this.context).finish();
    ((Activity)this.context).overridePendingTransition(17432576, 17432577);
  }
  
  private ArrayList<Joint> getFreeLoadedJoints() {
    ArrayList<Joint> arrayList = new ArrayList();
    Iterator<Limb> iterator = this.equippedLimbs.iterator();
    while (iterator.hasNext()) {
      for (Joint joint : ((Limb)iterator.next()).getJoints()) {
        if (joint.isFree())
          arrayList.add(joint); 
      } 
    } 
    return arrayList;
  }
  
  private void getInventory() {
    this.equippedLimbs = new ArrayList<Limb>();
    ArrayList<Limb> arrayList = this.databaseHelper.getCharacterLimbs(1);
    this.spareLimbs = arrayList;
    if (arrayList.size() < 1)
      this.spareLimbs = LimbFactory.getTestLimbs(this.context, 1); 
    resetAll();
    setInventoryCoordinates();
  }
  
  private boolean limbEquipped() {
    ArrayList<Limb> arrayList = this.equippedLimbs;
    if (arrayList != null)
      return (arrayList.size() > 0); 
    this.equippedLimbs = new ArrayList<Limb>();
    return false;
  }
  
  private boolean limbLoaded() {
    return (this.loadedLimb != null);
  }
  
  private void loadFirstLimb(Limb paramLimb) {
    if (this.equippedLimbs.size() < 1) {
      this.loadedLimb = paramLimb;
      int i = screenWidth / 2 - paramLimb.getImage().getWidth() / 2;
      paramLimb.setX(i);
      paramLimb.setY(i);
    } 
  }
  
  private void loadLimb(Limb paramLimb) {
    unloadLimb();
    setInventoryCoordinates();
    if (this.equippedLimbs.size() < 1) {
      loadFirstLimb(paramLimb);
    } else {
      boolean bool = false;
      byte b = 0;
      while (b < this.equippedLimbs.size()) {
        boolean bool1;
        Limb limb = this.equippedLimbs.get(b);
        byte b1 = 0;
        while (true) {
          bool1 = bool;
          if (b1 < limb.getJoints().size()) {
            Joint joint = limb.getJoints().get(b1);
            if (joint.isFree() && paramLimb.getJoints().size() > 0) {
              this.loadedLimb = paramLimb;
              Joint joint1 = paramLimb.getJoints().get(0);
              setLoadedLimbCoordinates(limb, joint, joint1);
              this.currentEquippedJoint = joint;
              this.currentLoadedJoint = joint1;
              this.currentEquippedJointLimb = limb;
              this.equippedJointIndex = b1;
              this.loadedJointIndex = 0;
              this.equippedLimbIndex = b;
              bool1 = true;
              break;
            } 
            b1++;
            continue;
          } 
          break;
        } 
        if (bool1)
          break; 
        b++;
        bool = bool1;
      } 
    } 
  }
  
  private void nextEquippedJoint() {
    boolean bool = false;
    int i = 0;
    while (!bool) {
      if (i > this.equippedLimbs.size() * 2)
        bool = true; 
      int k = this.equippedJointIndex + 1;
      this.equippedJointIndex = k;
      int j = i;
      if (k > this.currentEquippedJointLimb.getJoints().size() - 1) {
        this.currentEquippedJointLimb = this.equippedLimbs.get(this.equippedLimbIndex);
        this.equippedJointIndex = 0;
        k = this.equippedLimbIndex + 1;
        this.equippedLimbIndex = k;
        j = i;
        if (k > this.equippedLimbs.size() - 1) {
          j = i + 1;
          this.equippedLimbIndex = 0;
        } 
      } 
      Limb limb = this.equippedLimbs.get(this.equippedLimbIndex);
      this.currentEquippedJointLimb = limb;
      this.currentEquippedJoint = limb.getJoints().get(this.equippedJointIndex);
      i = j;
      if (((Joint)((Limb)this.equippedLimbs.get(this.equippedLimbIndex)).getJoints().get(this.equippedJointIndex)).isFree()) {
        bool = true;
        limb = this.equippedLimbs.get(this.equippedLimbIndex);
        this.currentEquippedJointLimb = limb;
        this.currentEquippedJoint = limb.getJoints().get(this.equippedJointIndex);
        i = j;
      } 
    } 
    setLoadedLimbCoordinates(this.currentEquippedJointLimb, this.currentEquippedJoint, this.currentLoadedJoint);
  }
  
  private void nextLoadedJoint() {
    if (!limbLoaded())
      return; 
    int i = this.loadedJointIndex + 1;
    this.loadedJointIndex = i;
    if (i > this.loadedLimb.getJoints().size() - 1)
      this.loadedJointIndex = 0; 
    Joint joint = this.loadedLimb.getJoints().get(this.loadedJointIndex);
    this.currentLoadedJoint = joint;
    setLoadedLimbCoordinates(this.currentEquippedJointLimb, this.currentEquippedJoint, joint);
  }
  
  private void printSaved(Canvas paramCanvas) {
    int i = screenWidth;
    paramCanvas.drawText("SAVED", (i / 3), (i / 3), this.textPaint);
    this.printSaved = false;
  }
  
  private void resetAll() {
    this.loadedLimb = null;
    null = this.spareLimbs.iterator();
    while (null.hasNext())
      ((Limb)null.next()).setEquipped(false); 
    for (Limb limb : this.equippedLimbs) {
      this.spareLimbs.add(limb);
      limb.setEquipped(false);
    } 
    this.currentEquippedJoint = null;
    setAllFree(this.spareLimbs);
    this.equippedLimbs = new ArrayList<Limb>();
    setInventoryCoordinates();
  }
  
  private void save() {
    this.playerCharacter.setEquippedLimbs(this.equippedLimbs);
    this.playerCharacter.setSpareLimbs(this.spareLimbs);
    this.databaseHelper.saveCharacter(this.playerCharacter);
    this.printSaved = true;
  }
  
  private void setAllFree(ArrayList<Limb> paramArrayList) {
    Iterator<Limb> iterator = paramArrayList.iterator();
    while (iterator.hasNext()) {
      Iterator<Joint> iterator1 = ((Limb)iterator.next()).getJoints().iterator();
      while (iterator1.hasNext())
        ((Joint)iterator1.next()).setFree(true); 
    } 
  }
  
  private void setInventoryCoordinates() {
    if (this.spareLimbs == null)
      return; 
    for (byte b = 0; b < this.spareLimbs.size(); b++) {
      Limb limb = this.spareLimbs.get(b);
      if (limb != this.loadedLimb) {
        int i = this.limbLength;
        limb.setThumb(i, i);
        i = (limb.getThumb().getWidth() + 10) * b;
        int j = this.panelRect.top + 10;
        if (b == 0)
          this.halfMeasure = limb.getImage().getWidth() / 2; 
        limb.setX(i);
        limb.setY(j);
        limb.setRect(new Rect(i, j, limb.getThumb().getWidth() + i, limb.getThumb().getWidth() + j));
      } 
    } 
  }
  
  private void setLoadedLimbCoordinates(Limb paramLimb, Joint paramJoint1, Joint paramJoint2) {
    if (paramJoint1 != null && paramJoint2 != null && paramLimb != null) {
      this.loadedLimb.setX(paramLimb.getX() + paramJoint1.getX() - paramJoint2.getX());
      this.loadedLimb.setY(paramLimb.getY() + paramJoint1.getY() - paramJoint2.getY());
    } 
  }
  
  private void unloadLimb() {
    if (this.loadedLimb != null) {
      this.loadedLimb = null;
      setInventoryCoordinates();
    } 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.clipSpaceRect == null)
      createRects(); 
    paramCanvas.drawRect(this.clipSpaceRect, this.clipSpacePaint);
    paramCanvas.drawRect(this.panelRect, this.panelPaint);
    drawEquippedLimbs(paramCanvas);
    if (limbLoaded()) {
      drawLoadedLimb(paramCanvas);
      drawLoadedPanel(paramCanvas);
    } else {
      drawInventory(paramCanvas);
    } 
    if (limbEquipped()) {
      paramCanvas.drawRect(this.resetButtonRect, this.greenPaint);
      paramCanvas.drawText("RESET", this.resetButtonRect.left, (this.resetButtonRect.bottom - 10), this.buttonTextPaint);
      if (!limbLoaded())
        drawSaveButton(paramCanvas); 
    } 
    drawExitButton(paramCanvas);
    if (this.printSaved)
      printSaved(paramCanvas); 
    drawJoints(paramCanvas);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    this.touchX = (int)paramMotionEvent.getX();
    this.touchY = (int)paramMotionEvent.getY();
    if (paramMotionEvent.getAction() == 0 && !checkButtonsForTouch() && !checkInventoryForTouch())
      unloadLimb(); 
    invalidate();
    return true;
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/EquipView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */