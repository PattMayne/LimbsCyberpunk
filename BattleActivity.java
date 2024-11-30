package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BattleActivity extends AppCompatActivity {
  Context context;
  
  DatabaseHelper databaseHelper;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.context = (Context)this;
    this.databaseHelper = new DatabaseHelper(this.context);
    TestDungeon testDungeon = new TestDungeon((Context)this, 0);
    Character character2 = this.databaseHelper.getCharacter(1);
    Character character1 = this.databaseHelper.getCharacter(2);
    if (character1 == null || character2 == null) {
      Intent intent = new Intent(this.context, MessageActivity.class);
      intent.putExtra("body", "A character failed to load.");
      intent.putExtra("title", "MISSING CHARACTER");
      startActivity(intent);
      finish();
      overridePendingTransition(17432576, 17432577);
    } 
    setContentView(new BattleView(this.context, character2, character1, testDungeon, this.databaseHelper));
  }
}
