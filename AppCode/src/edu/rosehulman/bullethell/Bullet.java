package edu.rosehulman.bullethell;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bullet {
	
	private Point location;
	private boolean friendly;
	private Bitmap image;
	private int xoffset;
	private int yoffset;
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int SOUTHEAST = 3;
	public static final int SOUTHWEST = 4;
	public int dir;
	public static final int HORIZONTAL_SPEED = 1;
	public static final int VERTICAL_SPEED = 6;
	
	public Bullet(Point location, boolean friendly, Bitmap image, int dir){
		this.location = location;
		this.friendly = friendly;
		this.image = image;
		xoffset = image.getWidth()/2;
		yoffset = image.getHeight()/2;
		this.dir = dir;
	}
	
	public void draw(Canvas c) {
		c.drawBitmap(image, location.x - xoffset,
				location.y-yoffset, null);
	}
	public Point move(){
		switch(dir){
		case NORTH:
			this.location.y -= VERTICAL_SPEED;
			return location;
		case SOUTH:
			this.location.y += VERTICAL_SPEED;
			return location;
		case SOUTHEAST:
			this.location.y += VERTICAL_SPEED;
			this.location.x += HORIZONTAL_SPEED;
			return location;
		case SOUTHWEST:
			this.location.y += VERTICAL_SPEED;
			this.location.x -= HORIZONTAL_SPEED;
			return location;
		}
		return null;
	}
	
	public Point getLocation() {
		return location;
	}

	public boolean isFriendly() {
		return friendly;
	}

	
	
}
