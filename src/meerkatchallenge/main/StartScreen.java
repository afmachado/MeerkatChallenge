package meerkatchallenge.main;

import levels.LevelActivity;
import levels.Preferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

public class StartScreen extends Activity {
	final int SCALE = 4;
	StartScreen reference;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		reference = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		
		TextView title = (TextView) findViewById(R.id.title);
		TextView goButton = (TextView) findViewById(R.id.go_button);
		// Set the font size based on the height
		// We have to do this in a globallayoutlistener because the textview
		// doesn't have a height till after onCreate
		scaleFontSize(title);
		scaleFontSize(goButton);
		goButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(reference, LevelActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	private void scaleFontSize(final TextView tv) {
		final ViewTreeObserver observer = tv.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// Set the font size based on the height
				tv.setTextSize((float) (tv.getHeight() / SCALE));
			}
		});
	}
}
