package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DungeonActivity extends AppCompatActivity {
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(new DungeonView((Context)this, DungeonFactory.getDungeon((Context)this, 1)));
  }
}
