package edu.rosehulman.bullethell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

public class GameView extends Activity {

	GamePanel panel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		panel = new GamePanel(this);
		setContentView(panel);
	}

	@Override
	public void onBackPressed() {
		Log.d("TEST", "BACK PRESSED");
		panel.stop();
		//finish();
		//setResult(RESULT_CANCELED);
		//super.onBackPressed();
	}

	public void endGame() {
		Log.d("RHBH", "GAME ENDED");
		int score = panel.getScore();
		panel.stop();
		Log.d("RHBH", "PANEL STOPPED");
		Intent returnIntent = new Intent(this,MainActivity.class);
		returnIntent.putExtra(MainActivity.SCORE_KEY, score);
		setResult(RESULT_OK,returnIntent);
		finish();

	}
}
