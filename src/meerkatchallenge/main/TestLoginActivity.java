package meerkatchallenge.main;
// TODO: Remove when prototyping is complete
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Test Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class TestLoginActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.activity_test_login);

		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mLoginFormView = findViewById(R.id.login_form);

		final Button b = (Button) findViewById(R.id.sign_in_button);
		final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_in);
		b.setAnimation(fadeIn);
		b.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						b.startAnimation(fadeIn);
					}
				});
	}
}
