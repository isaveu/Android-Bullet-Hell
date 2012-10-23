package edu.rosehulman.bullethell;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private static final int DIALOG_ID_ABOUT = 0;
	private static final int DIALOG_ID_HIGHSCORE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        ((Button)findViewById(R.id.play_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.about_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.options_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.high_score_button)).setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    @Override
    protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		
		switch (id){
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
		switch(v.getId()){
		case R.id.play_button:
			Log.d("Bullet Hell", "Play clicked");
			Intent gameIntent = new Intent(MainActivity.this,GameView.class);
			startActivity(gameIntent);
			break;
		case R.id.about_button:
			Log.d("Bullet Hell", "About clicked");
			showDialog(DIALOG_ID_ABOUT);
			break;
		case R.id.options_button:
			Log.d("Bullet Hell", "Options clicked");
			Intent intent = new Intent(MainActivity.this,BulletPreference.class);
			startActivity(intent);
			break;
		case R.id.high_score_button:
			Log.d("Bullet Hell", "High Score clicked");
			Intent highScoreIntent = new Intent(MainActivity.this,HighScoreActivity.class);
			startActivity(highScoreIntent);
			break;
		}
		
	}
}
