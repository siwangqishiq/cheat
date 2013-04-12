package com.xinlan.cheat.role;

import java.util.LinkedList;
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
	public static final int STOP = 0;
	public static final int MOVING = 1;

	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;

	public static final int FRAME_NUM = 4;

	public int moveStatus;
	public int dirStatus;
	public float left, top, width, height;
	public float x, y;// 中心点位置

	private MainView context;
	private Rect src;// 源区域
	private RectF dst;// 显示区域
	private Bitmap mBitmap;// 位图数据
	private int srcWidth, srcHeight;
	private int frame, frameDelay = 0;

	private int[][] map;
	private float cube_width, cube_height;
	private float dx, dy;
	private float speedx=3;
	private float speedy=3;
	private int cur_x, cur_y;
	private int goto_x, goto_y;
	private LinkedList<Point> pathQueue = new LinkedList<Point>();
	private AStar mAStar;

	public Player(MainView context) {
		this.context = context;
		map = context.room.map;
		cube_width = context.room.cube_width;
		cube_height = context.room.cube_height;
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player);
		mAStar = new AStar(map);
		dirStatus = RIGHT;// 面向右方
		moveStatus = MOVING;
		width = cube_width;
		height = cube_height ;
		srcWidth = mBitmap.getWidth() / 4;
		srcHeight = mBitmap.getHeight() / 4;
		frame = 0;

		src = new Rect();
		calculateSrcRect();
		dst = new RectF(left, top, left + width, top + height);

		cur_x = 0;
		cur_y = 1;
		x = cur_x * cube_width + cube_width / 2;
		y = cur_y * cube_height + cube_height / 2;
		top = y - cube_height;
		left = x - cube_width / 2;
	}

	public void logic() {
		if (moveStatus == MOVING &&pathQueue!=null && !pathQueue.isEmpty()) {
			Point wantPt = pathQueue.peek();
			if (cur_x - wantPt.x < 0 && cur_y - wantPt.y == 0) {// 往右走
				dx = speedx;
				dy = 0;
				dirStatus = RIGHT;
			} else if (cur_x - wantPt.x == 0 && cur_y - wantPt.y < 0) {// 往下走
				dx = 0;
				dy = speedy;
				dirStatus = DOWN;
			} else if (cur_x - wantPt.x > 0 && cur_y - wantPt.y == 0) {// 往左走
				dx = -speedx;
				dy = 0;
				dirStatus = LEFT;
			} else if (cur_x - wantPt.x == 0 && cur_y - wantPt.y > 0) {// 往上走
				dx = 0;
				dy = -speedy;
				dirStatus = UP;
			}
			x+=dx;
			y+=dy;

			if (cur_x == wantPt.x && cur_y == wantPt.y) {
				goto_x = cur_x;
				goto_y = cur_y;
				pathQueue.poll();
				if (pathQueue.isEmpty()) {
					frame = 0;
					moveStatus = STOP;
				}
			}// end if
			updateFrame();// 更新帧动画
		}
		top = y - cube_height/2;
		left = x - cube_width / 2;
		calculateSrcRect();
		dst.left = left;
		dst.top = top;
		dst.bottom = dst.top + height;
		dst.right = dst.left + height;
		
		cur_x = (int) (x / cube_width);
		cur_y = (int) (y / cube_height);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	public void onTouch(MotionEvent e) {
		float x = e.getX(), y = e.getY();
		int end_x = (int) (x / cube_width);
		int end_y = (int) (y / cube_height);
		if (end_x != goto_x || end_y != goto_y) {// 重新计算路径
			pathQueue.clear();
			goto_x = end_x;
			goto_y = end_y;
			pathQueue = mAStar.mazePathQueue(cur_x, cur_y, goto_x, goto_y);
			moveStatus = MOVING;
		}
	}

	private void updateFrame() {
		frameDelay++;
		if (frameDelay >= 5) {
			frameDelay = 0;
			frame++;
		}
		if (frame >= FRAME_NUM) {
			frame = 0;
		}
	}

	private void calculateSrcRect() {
		src.left = frame * srcWidth;
		src.top = dirStatus * srcHeight;
		src.bottom = src.top + srcHeight;
		src.right = src.left + srcWidth;
	}
}// end class
