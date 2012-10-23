package edu.rosehulman.bullethell;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

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
		finish();
		super.onBackPressed();
	}


}
