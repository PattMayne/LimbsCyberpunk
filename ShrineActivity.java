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

public class ShrineActivity extends AppCompatActivity {
  Button backButton;
  
  Context context;
  
  DatabaseHelper databaseHelper;
  
  private Button dialogOkayButton;
  
  private Text dialogText;
  
  private TextView dialogTextView;
  
  Button getLimbsButton;
  
  private Dialog messageDialog;
  
  Character playerCharacter;
  
  private void givePlayerLimbs() {
    Limb limb = LimbFactory.limb000(this.context, this.playerCharacter.getId());
    this.playerCharacter.getSpareLimbs().add(limb);
    limb = LimbFactory.limb001(this.context, this.playerCharacter.getId());
    this.playerCharacter.getSpareLimbs().add(limb);
    limb = LimbFactory.limb002(this.context, this.playerCharacter.getId());
    this.playerCharacter.getSpareLimbs().add(limb);
    limb = LimbFactory.limb003(this.context, this.playerCharacter.getId());
    this.playerCharacter.getSpareLimbs().add(limb);
    limb = LimbFactory.limb004(this.context, this.playerCharacter.getId());
    this.playerCharacter.getSpareLimbs().add(limb);
    this.databaseHelper.saveCharacter(this.playerCharacter);
    this.dialogTextView.setText("You now have new Limbs.");
    this.messageDialog.show();
  }
  
  private void startActivitySequence(Intent paramIntent) {
    startActivity(paramIntent);
    finish();
    overridePendingTransition(17432576, 17432577);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492897);
    this.context = (Context)this;
    DatabaseHelper databaseHelper = new DatabaseHelper(this.context);
    this.databaseHelper = databaseHelper;
    this.playerCharacter = databaseHelper.getCharacter(1);
    this.getLimbsButton = (Button)findViewById(2131296347);
    this.backButton = (Button)findViewById(2131296351);
    Dialog dialog = new Dialog(this.context);
    this.messageDialog = dialog;
    dialog.setContentView(2131492914);
    this.dialogOkayButton = (Button)this.messageDialog.findViewById(2131296349);
    this.dialogTextView = (TextView)this.messageDialog.findViewById(2131296678);
    this.messageDialog.setCancelable(true);
    this.getLimbsButton.setOnClickListener(new View.OnClickListener() {
          final ShrineActivity this$0;
          
          public void onClick(View param1View) {
            ShrineActivity.this.givePlayerLimbs();
          }
        });
    this.backButton.setOnClickListener(new View.OnClickListener() {
          final ShrineActivity this$0;
          
          public void onClick(View param1View) {
            Intent intent = new Intent(ShrineActivity.this.context, PlayActivity.class);
            ShrineActivity.this.startActivitySequence(intent);
          }
        });
    this.dialogOkayButton.setOnClickListener(new View.OnClickListener() {
          final ShrineActivity this$0;
          
          public void onClick(View param1View) {
            ShrineActivity.this.messageDialog.dismiss();
          }
        });
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/ShrineActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */