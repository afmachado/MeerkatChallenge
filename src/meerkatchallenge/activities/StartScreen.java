package meerkatchallenge.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class StartScreen extends Activity {
	StartScreen reference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		reference = this;
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		setContentView(R.layout.activity_start_screen);
		
		ImageView goButton = (ImageView) findViewById(R.id.go_button);
		goButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(StartScreen.this, LevelSelect.class);
				startActivity(intent); 
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});	
	}
	
	// Disables the back button
	@Override
	public void onBackPressed() {
	}
}
