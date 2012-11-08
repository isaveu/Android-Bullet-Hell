package edu.rosehulman.bullethell;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
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
	private long lastKilled;
	private static final int INVINCIBLE_TIMER = 3000;
	private ArrayList<Bullet> playerBullets;
	private ArrayList<Bullet> enemyBullets;
	private static final int FIRE_RATE = 500;
	private long lastFire;
	private Bitmap bulletImage;
	private int enemyWait = 1500;
	private static final int EASY_WAVE_WAIT = 1500;
	private static final int HARD_WAVE_WAIT = 1000;
	private int score;

	private Paint textPaint;
	private int lives = 3;
	public GameView parent;
	private Bitmap bubble;

	public GamePanel(GameView context) {
		super(context);
		width = context.getResources().getDisplayMetrics().widthPixels;
		height = context.getResources().getDisplayMetrics().heightPixels;

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String shipName = prefs.getString(
				context.getString(R.string.ship_select_k), "Galaga");
		Bitmap ship;
		Log.d("RHBH",shipName);
		if (shipName.equals("Galaga")) {
			ship = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_player_ship);
		} else if (shipName.equals("Asteroid")) {
			ship = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_player_astroid);
		} else {
			ship = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_player_ship);

		}
		playerShip = new PlayerShip(ship, width, height);

		getHolder().addCallback(this);

		setFocusable(true);

		bulletImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_bullet);
		thread = new MainThread(getHolder(), this);
		nextWaveTime = System.currentTimeMillis();
		lastFire = System.currentTimeMillis();
		keepSpawning = true;
		playerBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(30);
		parent = context;
		bubble = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_bubble_shield);
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
		if (playerShip.isInvincible()) {
			playerShip.drawBubble(bubble, canvas);
		}
		playerShip.draw(canvas);
		for (IEnemyShip e : enemies) {
			e.draw(canvas);
		}
		for (Bullet b : playerBullets) {
			b.draw(canvas);
		}
		for (Bullet b : enemyBullets) {
			b.draw(canvas);
		}
		canvas.drawText("" + score, 10, 50, textPaint);
		canvas.drawText("Lives : " + (lives - 1), width - 120, 50, textPaint);
	}

	public void stop() {
		boolean attempt = true;
		while (attempt) {
			Log.d("RHBH", "Trying...");
			try {
				thread.running = false;
				thread.join();
				attempt = false;
			} catch (Exception e) {
				Log.d("RHBH", "Exception: " + e.getMessage());
			}
		}
		setFocusable(false);
	}

	public void update() {
		if (playerShip.isInvincible()
				&& System.currentTimeMillis() > lastKilled + INVINCIBLE_TIMER) {
			playerShip.setInvincible(false);
		}
		if (System.currentTimeMillis() > lastFire + FIRE_RATE) {
			playerBullets.add(new Bullet(playerShip.getLocation(), true,
					bulletImage, Bullet.NORTH));
			lastFire = System.currentTimeMillis();
		}
		ArrayList<Bullet> bulletsToDespawn = new ArrayList<Bullet>();
		for (Bullet b : playerBullets) {
			Point p = b.move();
			if (outSideScreen(p)) {
				bulletsToDespawn.add(b);
			} else {
				ArrayList<IEnemyShip> toRemove = new ArrayList<IEnemyShip>();
				for (IEnemyShip e : enemies) {
					if (isInside(p, e.getCollisionVals())) {
						bulletsToDespawn.add(b);
						score += e.getValue();
						toRemove.add(e);
						break;
					}
				}
				enemies.removeAll(toRemove);
			}
		}
		playerBullets.removeAll(bulletsToDespawn);
		ArrayList<IEnemyShip> removed = new ArrayList<IEnemyShip>();
		for (IEnemyShip e : enemies) {
			if (!e.update()) {
				removed.add(e);
			}
			if (!playerShip.isInvincible()
					&& isCollision(playerShip.getCollisionVals(),
							e.getCollisionVals())) {
				removed.add(e);
				playerShip.kill();
				lives--;
				lastKilled = System.currentTimeMillis();
				deathToast();
				break;
			}
			if (e.isFire()) {
				enemyBullets.addAll(e.getBullets(bulletImage));
				e.setFireTime();
			}
		}
		if (enemies.removeAll(removed)) {

			Log.d("Removal", "Removed an enemy!");
		}
		bulletsToDespawn.clear();
		for (Bullet b : enemyBullets) {
			Point p = b.move();
			if (outSideScreen(p)) {
				bulletsToDespawn.add(b);
			} else if (!playerShip.isInvincible()
					&& isInside(p, playerShip.getCollisionVals())) {
				bulletsToDespawn.add(b);
				playerShip.kill();
				lives--;
				lastKilled = System.currentTimeMillis();
				deathToast();
			}
		}
		enemyBullets.removeAll(bulletsToDespawn);
	}

	public boolean isGameOver() {
		return lives <= 0;
	}

	private void deathToast() {

		this.post(new Runnable() {
			public void run() {
				Toast.makeText(getContext(), R.string.you_died,
						Toast.LENGTH_SHORT).show();
			}
		});
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
				&& vals[1] >= p.getY() && p.getY() >= vals[3];
	}

	public void spawnEnemy() {
		if (keepSpawning) {
			if (waveNumber % 3 != 0) {
				spawnWave1();
			} else {
				spawnWave2();
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

	private void spawnWave2() {
		enemyWait = HARD_WAVE_WAIT;
		enemyCount += 2;
		if (enemyCount <= 20) {
			if (enemyCount % 4 == 0) {
				enemies.add(new NormalEnemyLeft(BitmapFactory.decodeResource(
						getResources(), R.drawable.ic_normal_enemy), width,
						height));
				enemies.add(new NormalEnemyRight(BitmapFactory.decodeResource(
						getResources(), R.drawable.ic_normal_enemy), width,
						height));
			} else {
				enemies.add(new WeakEnemyLeft(BitmapFactory.decodeResource(
						getResources(), R.drawable.ic_weak_enemy), width,
						height));
				enemies.add(new WeakEnemyRight(BitmapFactory.decodeResource(
						getResources(), R.drawable.ic_weak_enemy), width,
						height));
			}
		} else {
			waveCleanUp();
		}
	}

	private void waveCleanUp() {
		Log.d("Removal", "No more enemies " + enemies.size());
		keepSpawning = false;
		nextWaveTime = System.currentTimeMillis() + 1000;
	}

	private void spawnWave1() {
		enemyWait = EASY_WAVE_WAIT;
		enemyCount += 2;
		if (enemyCount <= 14) {
			enemies.add(new WeakEnemyLeft(BitmapFactory.decodeResource(
					getResources(), R.drawable.ic_weak_enemy), width, height));
			enemies.add(new WeakEnemyRight(BitmapFactory.decodeResource(
					getResources(), R.drawable.ic_weak_enemy), width, height));
		} else {
			waveCleanUp();
		}
	}

	private void spawnSimpleWave() {
		if (enemyCount == 0) {
			enemyCount++;
			enemies.add(new SimpleShip(BitmapFactory.decodeResource(
					getResources(), R.drawable.ic_weak_enemy), width, height));
		} else {
			waveCleanUp();
		}
	}

	private boolean outSideScreen(Point p) {
		return p.x < 0 || p.x > width || p.y < 0 || p.y > height;
	}

	public int getEnemyWait() {
		return enemyWait;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
