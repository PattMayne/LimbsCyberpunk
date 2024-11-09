package ca.spiralmachines.limbscyberpunk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {
  String body;
  
  TextView bodyTextView;
  
  Context context;
  
  Button okayButton;
  
  String title;
  
  TextView titleTextView;
  
  boolean victory;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492895);
    this.context = (Context)this;
    this.body = getIntent().getStringExtra("body");
    this.title = getIntent().getStringExtra("title");
    this.victory = getIntent().getBooleanExtra("victory", false);
    this.bodyTextView = (TextView)findViewById(2131296677);
    this.titleTextView = (TextView)findViewById(2131296679);
    this.bodyTextView.setText(this.body);
    this.titleTextView.setText(this.title);
    Button button = (Button)findViewById(2131296344);
    this.okayButton = button;
    button.setOnClickListener(new View.OnClickListener() {
          final MessageActivity this$0;
          
          public void onClick(View param1View) {
            Intent intent = new Intent(MessageActivity.this.context, PlayActivity.class);
            MessageActivity.this.startActivity(intent);
            MessageActivity.this.finish();
            MessageActivity.this.overridePendingTransition(17432576, 17432577);
          }
        });
  }
}


/* Location:              /home/pattmayne/Prog/Android/dex to jar/LimbsCyberpunk-dex2jar.jar!/ca/spiralmachines/limbscyberpunk/MessageActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */