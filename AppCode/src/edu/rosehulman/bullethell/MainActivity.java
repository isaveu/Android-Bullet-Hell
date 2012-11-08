package edu.rosehulman.bullethell;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	private static final int DIALOG_ID_ABOUT = 0;
	private static final int DIALOG_ID_HIGHSCORE = 1;
	public static int REQUEST_CODE = 12341234;
	public static String SCORE_KEY = "KEY";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.play_button)).setOnClickListener(this);
		((Button) findViewById(R.id.about_button)).setOnClickListener(this);
		((Button) findViewById(R.id.options_button)).setOnClickListener(this);
		((Button) findViewById(R.id.high_score_button))
				.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;

		switch (id) {
		case DIALOG_ID_ABOUT:
			AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(this);

			aboutBuilder.setMessage(getString(R.string.about_description));
			aboutBuilder.setCancelable(true);
			aboutBuilder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					});

			dialog = aboutBuilder.create();
			break;
		}
		return dialog;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_button:
			Log.d("Bullet Hell", "Play clicked");
			Intent gameIntent = new Intent(MainActivity.this, GameView.class);
			startActivityForResult(gameIntent, REQUEST_CODE);
			break;
		case R.id.about_button:
			Log.d("Bullet Hell", "About clicked");
			showDialog(DIALOG_ID_ABOUT);
			break;
		case R.id.options_button:
			Log.d("Bullet Hell", "Options clicked");
			Intent intent = new Intent(MainActivity.this,
					BulletPreference.class);
			startActivity(intent);
			break;
		case R.id.high_score_button:
			Log.d("Bullet Hell", "High Score clicked");
			Intent highScoreIntent = new Intent(MainActivity.this,
					HighScoreActivity.class);
			startActivity(highScoreIntent);
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("RHBH", "Returned");
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			Log.d("RHBH", "Score = " + data.getIntExtra(SCORE_KEY, -1));
			final int score = data.getIntExtra(SCORE_KEY, -1);
			final SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			int tenthScore = prefs.getInt(
					getString(R.string.high_score_10_value_key), -1);
			if (tenthScore <= score) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("High Score");
				alert.setMessage("New High Score!\nEnter your name:");

				// Set an EditText view to get user input
				final EditText input = new EditText(this);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String name = input.getText().toString();
								editHighScores(name, score, prefs);
							}
						});
				alert.setCancelable(false);
				alert.show();

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void editHighScores(String newName, int newScore, SharedPreferences prefs){
		 ArrayList<Score> scores = new ArrayList<Score>();
		 scores.add(new Score(newName, newScore));
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
		 Editor e = prefs.edit();
		 e.putString(getString(R.string.high_score_1_name_key), scores.get(0).name);
		 e.putInt(getString(R.string.high_score_1_value_key), scores.get(0).score);
		 e.putString(getString(R.string.high_score_2_name_key), scores.get(1).name);
		 e.putInt(getString(R.string.high_score_2_value_key), scores.get(1).score);
		 e.putString(getString(R.string.high_score_3_name_key), scores.get(2).name);
		 e.putInt(getString(R.string.high_score_3_value_key), scores.get(2).score);
		 e.putString(getString(R.string.high_score_4_name_key), scores.get(3).name);
		 e.putInt(getString(R.string.high_score_4_value_key), scores.get(3).score);
		 e.putString(getString(R.string.high_score_5_name_key), scores.get(4).name);
		 e.putInt(getString(R.string.high_score_5_value_key), scores.get(4).score);
		 e.putString(getString(R.string.high_score_6_name_key), scores.get(5).name);
		 e.putInt(getString(R.string.high_score_6_value_key), scores.get(5).score);
		 e.putString(getString(R.string.high_score_7_name_key), scores.get(6).name);
		 e.putInt(getString(R.string.high_score_7_value_key), scores.get(6).score);
		 e.putString(getString(R.string.high_score_8_name_key), scores.get(7).name);
		 e.putInt(getString(R.string.high_score_8_value_key), scores.get(7).score);
		 e.putString(getString(R.string.high_score_9_name_key), scores.get(8).name);
		 e.putInt(getString(R.string.high_score_9_value_key), scores.get(8).score);
		 e.putString(getString(R.string.high_score_10_name_key), scores.get(9).name);
		 e.putInt(getString(R.string.high_score_10_value_key), scores.get(9).score);
		 e.commit();
	}



}
