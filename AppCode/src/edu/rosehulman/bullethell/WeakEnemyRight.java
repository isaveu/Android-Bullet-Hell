package edu.rosehulman.bullethell;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class WeakEnemyRight extends IEnemyShip {

	
	public WeakEnemyRight(Bitmap bitmap,int width, int height){
		this.setX(width-1);
		this.setY(1);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(width,0));
		points.add(new Point(width/4,height/2));
		points.add(new Point(width/4,height/4));
		points.add(new Point(width+100,height/4));
		this.setMove_speed(4);
		this.setPointList(points);
		this.setBitmap(bitmap);
	}
}
