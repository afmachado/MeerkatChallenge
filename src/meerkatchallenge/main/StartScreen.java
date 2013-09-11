package meerkatchallenge.main;

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
		setContentView(R.layout.activity_start_screen);
		
		ImageView goButton = (ImageView) findViewById(R.id.go_button);
		goButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(reference, LevelSelect.class);
				startActivity(intent);
			}
		});
		
	}
}
