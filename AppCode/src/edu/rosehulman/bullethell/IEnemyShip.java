package edu.rosehulman.bullethell;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public abstract class IEnemyShip {

	private Bitmap bitmap;
	private int x;
	private int y;
	private ArrayList<Point> pointList;
	private int move_speed;
	private static int COLLISION_BUFFER = 4;
	private long lastFire;
	private int value = 100;
	public long getLastFire() {
		return lastFire;
	}

	public void setLastFire(long lastFire) {
		this.lastFire = lastFire;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}


	private int fireRate;
	
	
	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}

	public void setPointList(ArrayList<Point> pointList) {
		this.pointList = pointList;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	


	public int getRight() {
		return x + (bitmap.getWidth() / 2) - COLLISION_BUFFER;
	}

	public int getTop() {
		return y - (bitmap.getHeight() / 2) + COLLISION_BUFFER;
	}

	public int getLeft() {
		return x - (bitmap.getWidth() / 2) + COLLISION_BUFFER;
	}

	public int getBottom() {
		return y + (bitmap.getHeight() / 2) - COLLISION_BUFFER;
	}

	public int[] getCollisionVals(){
		return new int[]{getLeft(),getBottom(),getRight(),getTop()};
	}

	public void draw(Canvas c) {
		c.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
				y - (bitmap.getHeight() / 2), null);
	}
	
	public boolean update(){
		if (pointList.size() > 0){
			Point next = getNextPoint();
			int diffX = next.getX() - this.x;
			int diffY = next.getY() - this.y;
			if(diffX > 0){
				this.x += (diffX<move_speed)?diffX:move_speed;
			}
			else if(diffX < 0){
				this.x += (-diffX<move_speed)?diffX:-move_speed;
			}
			if(diffY > 0){
				this.y += (diffY<move_speed)?diffY:move_speed;
			}
			else if(diffY < 0){
				this.y += (-diffY<move_speed)?diffY:-move_speed;
			}
			
			if(this.x == next.getX() && this.y == next.getY()){
				pointList.remove(0);
			}
			return true;
		}
		return false;
	}
	public boolean isFire(){
		return System.currentTimeMillis() > lastFire + fireRate;
	}
	private Point getNextPoint(){
		return pointList.get(0);
	}
	
	public void setFireTime(){
		lastFire = System.currentTimeMillis();
	}
	
	
	public abstract ArrayList<Bullet> getBullets(Bitmap b);

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	

}
