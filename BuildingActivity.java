package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class BuildingActivity extends AppCompatActivity {
  Button backToGameButton;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492892);
    Button button = (Button)findViewById(2131296348);
    this.backToGameButton = button;
    button.setOnClickListener(new View.OnClickListener() {
          final BuildingActivity this$0;
          
          public void onClick(View param1View) {
            Intent intent = new Intent((Context)BuildingActivity.this, DungeonActivity.class);
            BuildingActivity.this.startActivity(intent);
            BuildingActivity.this.finish();
            BuildingActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/BuildingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */