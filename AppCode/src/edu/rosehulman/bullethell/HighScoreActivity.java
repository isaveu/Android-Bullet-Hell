package edu.rosehulman.bullethell;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScoreActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_high_scores);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		 ArrayList<Score> scores = new ArrayList<Score>();
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_10_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_10_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_1_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_1_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_2_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_2_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_3_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_3_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_4_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_4_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_5_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_5_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_6_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_6_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_7_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_7_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_8_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_8_value_key), -1)));
		 scores.add(new Score(prefs.getString(getString(R.string.high_score_9_name_key), "None"),
	 				prefs.getInt(getString(R.string.high_score_9_value_key), -1)));
		 Collections.sort(scores);
		 
		 final ListView list = (ListView) findViewById(R.id.list_view);
		 ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this,android.R.layout.simple_list_item_1,scores);
		 list.setAdapter(adapter);
	}
}
