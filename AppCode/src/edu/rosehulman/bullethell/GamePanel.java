package edu.rosehulman.bullethell;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private PlayerShip playerShip;
	private MainThread thread;
	private ArrayList<IEnemyShip> enemies = new ArrayList<IEnemyShip>();
	private int width;
	private int height;
	private int enemyCount = 0;
	private boolean keepSpawning;
	private long nextWaveTime;
	private int waveNumber = 1;

	public GamePanel(Context context) {
		super(context);
		width = context.getResources().getDisplayMetrics().widthPixels;
		height = context.getResources().getDisplayMetrics().heightPixels;
		playerShip = new PlayerShip(BitmapFactory.decodeResource(
				getResources(), R.drawable.ic_player_ship), width, height);

		getHolder().addCallback(this);

		setFocusable(true);

		thread = new MainThread(getHolder(), this);
		nextWaveTime = System.currentTimeMillis();
		keepSpawning = true;

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		while (true) {
			try {
				thread.running = false;
				thread.join();

				break;
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			playerShip.handleActionDown((int) event.getX(), (int) event.getY());
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (playerShip.isTouched()) {

				playerShip.setX((int) event.getX());
				playerShip.setY((int) event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			playerShip.setTouched(false);
			Log.d("test", "released" + event.getX() + "," + event.getY());

		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		playerShip.draw(canvas);
		for (IEnemyShip e : enemies) {
			e.draw(canvas);
		}
	}

	public void stop() {
		boolean attempt = true;
		while (attempt) {
			try {
				thread.running = false;
				thread.join();
				attempt = false;
			} catch (Exception e) {
			}
		}
		setFocusable(false);
	}

	public void update() {
		ArrayList<IEnemyShip> removed = new ArrayList<IEnemyShip>();
		for (IEnemyShip e : enemies) {
			if (!e.update()) {
				removed.add(e);

			}
			if (isCollision(playerShip.getCollisionVals(), e.getCollisionVals())) {
				// playerShip.kill();
				removed.add(e);
				playerShip.kill();
				this.post(new Runnable() {
					public void run() {
						Toast.makeText(getContext(), R.string.you_died,
								Toast.LENGTH_SHORT).show();
					}
				});
			}

		}
		if (enemies.removeAll(removed)) {

			Log.d("Removal", "Removed an enemy!");
		}
	}

	private boolean isCollision(int[] playerVals, int[] enemyVals) {
		Point PCorner1 = new Point(playerVals[0], playerVals[1]);
		Point PCorner2 = new Point(playerVals[0], playerVals[3]);
		Point PCorner3 = new Point(playerVals[2], playerVals[1]);
		Point PCorner4 = new Point(playerVals[2], playerVals[3]);
		Point ECorner1 = new Point(enemyVals[0], enemyVals[1]);
		Point ECorner2 = new Point(enemyVals[0], enemyVals[3]);
		Point ECorner3 = new Point(enemyVals[2], enemyVals[1]);
		Point ECorner4 = new Point(enemyVals[2], enemyVals[3]);
		return isInside(PCorner1, enemyVals) || isInside(PCorner2, enemyVals)
				|| isInside(PCorner3, enemyVals)
				|| isInside(PCorner4, enemyVals)
				|| isInside(ECorner1, playerVals)
				|| isInside(ECorner2, playerVals)
				|| isInside(ECorner3, playerVals)
				|| isInside(ECorner4, playerVals);
	}

	// vals in form [x1 (left),y1(bottom),x2(right),y2(top)]
	private boolean isInside(Point p, int[] vals) {
		return vals[0] <= p.getX() && p.getX() <= vals[2]
				&& vals[1] <= p.getY() && p.getY() <= vals[3];
	}

	public void spawnEnemy() {
		if (keepSpawning) {
			enemyCount += 2;
			if (waveNumber % 3 != 0) {
				if (enemyCount <= 14) {
					enemies.add(new WeakEnemyLeft(BitmapFactory.decodeResource(
							getResources(), R.drawable.ic_weak_enemy), width,
							height));
					enemies.add(new WeakEnemyRight(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.ic_weak_enemy), width, height));
				} else {
					Log.d("Removal", "No more enemies " + enemies.size());
					keepSpawning = false;
					nextWaveTime = System.currentTimeMillis() + 5000;

				}
			} else {
				enemyCount+=2;
				if (enemyCount <= 28) {
					enemies.add(new NormalEnemyLeft(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.ic_normal_enemy), width, height));
					enemies.add(new NormalEnemyRight(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.ic_normal_enemy), width, height));
					enemies.add(new WeakEnemyLeft(BitmapFactory.decodeResource(
							getResources(), R.drawable.ic_weak_enemy), width,
							height));
					enemies.add(new WeakEnemyRight(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.ic_weak_enemy), width, height));
				} else {
					Log.d("Removal", "No more enemies " + enemies.size());
					keepSpawning = false;
					nextWaveTime = System.currentTimeMillis() + 5000;

				}
			}
		} else if (enemies.isEmpty()
				&& System.currentTimeMillis() > nextWaveTime) {
			keepSpawning = true;
			enemyCount = 0;
			waveNumber++;
			this.post(new Runnable() {
				public void run() {
					Toast.makeText(getContext(), R.string.new_wave,
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

}
