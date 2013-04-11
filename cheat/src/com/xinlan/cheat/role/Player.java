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
	private int frame,frameDelay=0;

	private int[][] map;
	private float cube_width, cube_height;
	
	public Player(MainView context) {
		this.context = context;
		map=context.room.map;
		cube_width=context.room.cube_width;
		cube_height=context.room.cube_height;
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player);
		dirStatus = RIGHT;// 面向右方
		width = cube_width;
		height = cube_height+cube_height/2;
		srcWidth = mBitmap.getWidth() / 4;
		srcHeight = mBitmap.getHeight() / 4;
		frame = 0;
		
		src = new Rect();
		calculateSrcRect();
		top=cube_height/2;//context.room.cube_height;
		dst = new RectF(left, top, left + width, top +height);
	}

	public void logic() {
		updateFrame();//更新帧动画
		calculateSrcRect();
		dst.left = left;
		dst.top = top;
		dst.bottom = dst.top + height;
		dst.right = dst.left + height;
		
		left+=2;
		if(left>MainView.screenW-width){
			left=MainView.screenW-width;
		}
		int grid_x=(int)((left+cube_width)/cube_width);
		int grid_y=(int)((top+cube_height)/cube_height);
		
		if(map[grid_y][grid_x]!=0){
			left-=2;
		}
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	public void onTouch(MotionEvent e) {
		
	}

	private void updateFrame(){
		frameDelay++;
		if(frameDelay>=5){
			frameDelay=0;
			frame++;
		}
		if(frame>=FRAME_NUM){
			frame=0;
		}
	}
	
	private void calculateSrcRect() {
		src.left = frame * srcWidth;
		src.top = dirStatus * srcHeight;
		src.bottom = src.top + srcHeight;
		src.right = src.left + srcWidth;
	}
}// end class
