package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EquipActivity extends AppCompatActivity {
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(new EquipView((Context)this));
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/EquipActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */