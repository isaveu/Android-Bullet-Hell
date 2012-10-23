package edu.rosehulman.bullethell;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class PlayerShip {
	private Bitmap bitmap;
	private int x;
	private int y;
	boolean touched = false;
	private int width;
	private int height;
	private static int COLLISION_BUFFER = 4;

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public PlayerShip(Bitmap b, int width, int height) {
		this.width = width;
		this.height = height;
		bitmap = b;
		x = width/2;
		y = (height*8)/10;
	}

	public int getRight() {
		return x + (bitmap.getWidth() / 2) - COLLISION_BUFFER;
	}

	public int getTop() {
		return y + (bitmap.getHeight() / 2) + COLLISION_BUFFER;
	}

	public int getLeft() {
		return x - (bitmap.getWidth() / 2) - COLLISION_BUFFER;
	}

	public int getBottom() {
		return y - (bitmap.getHeight() / 2) + COLLISION_BUFFER;
	}

	public int[] getCollisionVals(){
		return new int[]{getLeft(),getBottom(),getRight(),getTop()};
	}
	
	public Bitmap getBitmap() {
		return bitmap;
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

	public void draw(Canvas c) {
		c.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
				y - (bitmap.getHeight() / 2), null);
	}

	public void handleActionDown(int eX, int eY) {
		if (eX >= (x - bitmap.getWidth() / 2) + 10
				&& (eX <= (x + bitmap.getWidth() / 2) + 10)) {

			if (eY >= (y - bitmap.getHeight() / 2) + 10
					&& (eY <= (y + bitmap.getHeight() / 2) + 10)) {
				setTouched(true);
				Log.d("TEST", "touched");
			} else {
				setTouched(false);
			}

		} else {
			setTouched(false);
		}
	}
	
	public void kill(){
		touched = false;
		x = width/2;
		y = (height*8)/10;
	}

}
