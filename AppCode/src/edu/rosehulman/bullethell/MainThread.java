package edu.rosehulman.bullethell;

import android.graphics.Canvas;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	public boolean running;

	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;

	private final int FPS = 40;
	private final int FRAME_PERIOD = 1000 / FPS;
	private final int MAX_SKIPPED = 5;

	public MainThread(SurfaceHolder s, GamePanel g) {
		super();
		surfaceHolder = s;
		gamePanel = g;
	}

	public void setRunning(boolean r) {
		running = r;
	}

	@Override
	public void run() {
		Canvas canvas;
		long enemyCountDown = System.currentTimeMillis();
		long beginTime;
		int sleepTime;
		int framesSkipped;
		Looper.prepare();

		Log.d("TEST", "Thread started");
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;
					this.gamePanel.update();
					this.gamePanel.onDraw(canvas);

					;
					sleepTime = (int) (FRAME_PERIOD - (System
							.currentTimeMillis() - beginTime));

					if (sleepTime > 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							//Silent Catch
						}
					}
					while (sleepTime < 0 && framesSkipped < MAX_SKIPPED){
						this.gamePanel.update();
						framesSkipped++;
						sleepTime += FRAME_PERIOD;
					}
					
					if(System.currentTimeMillis() - 2000 > enemyCountDown){
						gamePanel.spawnEnemy();
						enemyCountDown = System.currentTimeMillis();
					}

				}
			} finally {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		Log.d("TEST", "Thread ended");

	}
}
