package edu.rosehulman.bullethell;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class SimpleShip extends IEnemyShip {

	public SimpleShip(Bitmap bitmap,int width, int height){
		this.setX(width/2);
		this.setY(height/2);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		points.add(new Point(0,0));
		points.add(new Point(300,300));
		this.setMove_speed(4);
		this.setPointList(points);
		this.setBitmap(bitmap);
		this.setFireRate(200);
		this.setLastFire(System.currentTimeMillis());
	}

	@Override
	public ArrayList<Bullet> getBullets(Bitmap b) {
		ArrayList<Bullet> list = new ArrayList<Bullet>();
		list.add(new Bullet(new Point(getX(),getY()), false, b, Bullet.SOUTHWEST));
		return list;
	}
	
	@Override
	public boolean update() {
		return true;
	}

}
