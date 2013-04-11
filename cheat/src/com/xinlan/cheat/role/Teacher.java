package com.xinlan.cheat.role;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.xinlan.view.MainView;

/**
 * 老师
 * @author Panyi 
 *
 */
public class Teacher {
	private MainView context;

	public float left, top, width, height;
	public float dx, dy;
	private float widthDiv2, heightDiv2;
	private float x, y;// 中心点位置

	private Paint paint;
	private RectF rectF;

	public Teacher(MainView context) {
		this.context = context;
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		x = MainView.screenW / 4;
		y = MainView.screenH / 4;
		height = width = MainView.screenW / 15;
		widthDiv2 = width / 2;
		heightDiv2 = height / 2;
		left = x - widthDiv2;
		top = y - heightDiv2;
		rectF = new RectF(left, top, left + width, top + height);
		x = rectF.centerX();
		y = rectF.centerY();
		
		dy=3;
	}

	public void logic() {
		y+=dy;
		
		if(y<0 || y>300){
			dy*=-1;
		}
	}

	public void draw(Canvas canvas) {
		rectF.set(x - widthDiv2, y - heightDiv2, x + widthDiv2, y + heightDiv2);
		canvas.drawRect(rectF, paint);
	}
	
	public void onTouch(MotionEvent e){
		
	}
}//end class
