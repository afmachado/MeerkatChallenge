package meerkatchallenge.main;

// TODO: Remove when prototyping is complete
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Test Activity which displays a login screen to the user, offering
 * registration as well.
 */
public class StartLevelBounce extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_start_level_bounce);
		final LinearLayout wholeView = (LinearLayout) findViewById(R.id.login_form);
//		final Button b = (Button) findViewById(R.id.sign_in_button);
		final Animation fadeIn = AnimationUtils.loadAnimation(this,
				R.anim.anim_in);
		wholeView.setAnimation(fadeIn);
//		b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				wholeView.startAnimation(fadeIn);
//			}
//		});
	}
}
