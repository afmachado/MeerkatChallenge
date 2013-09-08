package levels;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	final static String PREFS_NAME = "MeerkatChallenge";

	public static void setLevel(Activity a, int levelNum) {
		SharedPreferences preferences = a.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("level", levelNum);
		editor.commit();
	}
	
	public static int getLevel(Activity a) {
		SharedPreferences preferences = a.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return preferences.getInt("level", 1);
	}
}
