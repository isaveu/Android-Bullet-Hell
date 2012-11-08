package edu.rosehulman.bullethell;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class NormalEnemyLeft extends IEnemyShip {
	private boolean oddFire;

	public NormalEnemyLeft(Bitmap bitmap, int width, int height) {
		this.setX(width / 2);
		this.setY(0);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(width / 2, height / 3));
		points.add(new Point(width / 4, height / 3));
		points.add(new Point(width / 4, height / 4));
		points.add(new Point(width / 2, height / 4));
		points.add(new Point(width, height));
		this.setMove_speed(8);
		this.setPointList(points);
		this.setBitmap(bitmap);
		this.setFireRate(250);
		this.setValue(100);
		this.setLastFire(System.currentTimeMillis());
		oddFire = true;
	}

	@Override
	public ArrayList<Bullet> getBullets(Bitmap b) {
		ArrayList<Bullet> list = new ArrayList<Bullet>();
		if (oddFire) {
			list.add(new Bullet(new Point(getX(), getY()), false, b,
					Bullet.SOUTH));
		} else {
			list.add(new Bullet(new Point(getX(), getY()), false, b,
					Bullet.SOUTHEAST));
//			list.add(new Bullet(new Point(getX(), getY()), false, b,
//					Bullet.SOUTHWEST));
		}
		oddFire = !oddFire;
		return list;
	}
}
