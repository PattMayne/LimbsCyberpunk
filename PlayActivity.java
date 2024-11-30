package ca.spiralmachines.limbscyberpunk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Text;

public class PlayActivity extends AppCompatActivity {
  Button battleButton;
  
  Context context;
  
  DatabaseHelper databaseHelper;
  
  private Button dialogOkayButton;
  
  private Text dialogText;
  
  private TextView dialogTextView;
  
  Button dungeonButton;
  
  Button equipButton;
  
  private Dialog messageDialog;
  
  Button shrineButton;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492896);
    this.context = (Context)this;
    this.databaseHelper = new DatabaseHelper(this.context);
    Dialog dialog = new Dialog(this.context);
    this.messageDialog = dialog;
    dialog.setContentView(2131492914);
    this.dialogOkayButton = (Button)this.messageDialog.findViewById(2131296349);
    this.dialogTextView = (TextView)this.messageDialog.findViewById(2131296678);
    this.messageDialog.setCancelable(true);
    this.equipButton = (Button)findViewById(2131296346);
    this.battleButton = (Button)findViewById(2131296350);
    this.dungeonButton = (Button)findViewById(2131296345);
    Button button = (Button)findViewById(2131296352);
    this.shrineButton = button;
    button.setOnClickListener(new View.OnClickListener() {
          final PlayActivity this$0;
          
          public void onClick(View param1View) {
            Intent intent = new Intent((Context)PlayActivity.this, ShrineActivity.class);
            PlayActivity.this.startActivity(intent);
            PlayActivity.this.finish();
            PlayActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
    this.equipButton.setOnClickListener(new View.OnClickListener() {
          final PlayActivity this$0;
          
          public void onClick(View param1View) {
            Character character = PlayActivity.this.databaseHelper.getCharacter(1);
            if (character.getEquippedLimbs().size() < 1 && character.getSpareLimbs().size() < 1) {
              PlayActivity.this.dialogTextView.setText("You have no limbs");
              PlayActivity.this.messageDialog.show();
              return;
            } 
            Intent intent = new Intent((Context)PlayActivity.this, EquipActivity.class);
            PlayActivity.this.startActivity(intent);
            PlayActivity.this.finish();
            PlayActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
    this.battleButton.setOnClickListener(new View.OnClickListener() {
          final PlayActivity this$0;
          
          public void onClick(View param1View) {
            Character character2 = PlayActivity.this.databaseHelper.getCharacter(1);
            Character character1 = PlayActivity.this.databaseHelper.getCharacter(2);
            if (character2.getEquippedLimbs().size() < 1) {
              PlayActivity.this.dialogTextView.setText("You have no equipped limbs.");
              PlayActivity.this.messageDialog.show();
              return;
            } 
            if (character1.getEquippedLimbs().size() < 1) {
              PlayActivity.this.dialogTextView.setText("You have no opponent to battle.");
              PlayActivity.this.messageDialog.show();
              return;
            } 
            Intent intent = new Intent((Context)PlayActivity.this, BattleActivity.class);
            PlayActivity.this.startActivity(intent);
            PlayActivity.this.finish();
            PlayActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
    this.dungeonButton.setOnClickListener(new View.OnClickListener() {
          final PlayActivity this$0;
          
          public void onClick(View param1View) {
            Character character1 = PlayActivity.this.databaseHelper.getCharacter(1);
            Character character2 = PlayActivity.this.databaseHelper.getCharacter(2);
            if (character1.getEquippedLimbs().size() < 1) {
              PlayActivity.this.dialogTextView.setText("You have no equipped limbs");
              PlayActivity.this.messageDialog.show();
              return;
            } 
            if (character2.getEquippedLimbs().size() > 1) {
              PlayActivity.this.dialogTextView.setText("You must finish your current battle before entering the dungeon.");
              PlayActivity.this.messageDialog.show();
              return;
            } 
            Intent intent = new Intent((Context)PlayActivity.this, DungeonActivity.class);
            PlayActivity.this.startActivity(intent);
            PlayActivity.this.finish();
            PlayActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
    this.dialogOkayButton.setOnClickListener(new View.OnClickListener() {
          final PlayActivity this$0;
          
          public void onClick(View param1View) {
            PlayActivity.this.messageDialog.dismiss();
          }
        });
  }
}
