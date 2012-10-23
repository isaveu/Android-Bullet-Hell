package edu.rosehulman.bullethell;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BulletPreference extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.bullet_preference);
	}

}
