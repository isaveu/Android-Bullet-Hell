package edu.rosehulman.bullethell;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

public class BulletPreference extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.bullet_preference);
		((Preference) findPreference(getString(R.string.clear_scores_key)))
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						Log.d("RHBH", "Clear scores");
						final SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(BulletPreference.this);
						Editor e = prefs.edit();
						Score s = new Score("None", 0);
						e.putString(getString(R.string.high_score_1_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_1_value_key),
								s.score);
						e.putString(getString(R.string.high_score_2_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_2_value_key),
								s.score);
						e.putString(getString(R.string.high_score_3_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_3_value_key),
								s.score);
						e.putString(getString(R.string.high_score_4_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_4_value_key),
								s.score);
						e.putString(getString(R.string.high_score_5_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_5_value_key),
								s.score);
						e.putString(getString(R.string.high_score_6_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_6_value_key),
								s.score);
						e.putString(getString(R.string.high_score_7_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_7_value_key),
								s.score);
						e.putString(getString(R.string.high_score_8_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_8_value_key),
								s.score);
						e.putString(getString(R.string.high_score_9_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_9_value_key),
								s.score);
						e.putString(getString(R.string.high_score_10_name_key),
								s.name);
						e.putInt(getString(R.string.high_score_10_value_key),
								s.score);
						e.commit();
						Toast.makeText(BulletPreference.this, "Cleared High Scores", Toast.LENGTH_SHORT).show();
						return true;
					}
				});
	}

}
