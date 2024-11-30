package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  Context context;
  
  DatabaseHelper databaseHelper;
  
  private Button playButton;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492894);
    Button button = (Button)findViewById(2131296350);
    this.playButton = button;
    button.setOnClickListener(new View.OnClickListener() {
          final MainActivity this$0;
          
          public void onClick(View param1View) {
            Intent intent = new Intent((Context)MainActivity.this, PlayActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
            MainActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
  }
}
