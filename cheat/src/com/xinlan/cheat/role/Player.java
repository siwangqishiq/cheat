package com.xinlan.cheat.role;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.xinlan.cheat.R;
import com.xinlan.view.MainView;

/**
 * Player
 * 
 * @author Panyi
 * 
 */
public class Player {
	public static final int DWON = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;

	public static final int FRAME_NUM = 4;

	public int dirStatus;
	public float left, top, width, height;
	public float x, y;// 中心点位置

	private MainView context;
	private Rect src;// 源区域
	private RectF dst;// 显示区域
	private Bitmap mBitmap;// 位图数据
	private int srcWidth, srcHeight;
	private int frame;

	public Player(MainView context) {
		this.context = context;
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player);
		dirStatus = RIGHT;// 面向右方
		width = MainView.screenW / (context.room.COL);
		height = MainView.screenH / (context.room.ROW);
		srcWidth = mBitmap.getWidth() / 4;
		srcHeight = mBitmap.getHeight() / 4;
		frame = 0;
		src = new Rect();
		calculateSrcRect();
		dst = new RectF(left, top, left + width, top + height);
	}

	public void logic() {
		frame++;
		calculateSrcRect();
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	public void onTouch(MotionEvent e) {
		
	}

	private void calculateSrcRect() {
		src.left = frame * srcWidth;
		src.top = dirStatus * srcHeight;
		src.bottom = src.top + srcHeight;
		src.right = src.left + srcWidth;
	}
}// end class
