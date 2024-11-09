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


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/DungeonActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */