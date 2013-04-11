package com.xinlan.cheat.role;

import java.util.Stack;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.xinlan.view.MainView;

public class Classroom {
	public static final int ROW = 25;
	public static final int COL = 15;
	public static float cube_width, cube_height;
	private MainView context;
	public int[][] map = //
	{ { 0, 0, 0, 0, 7, 0, 0, 0, 7, 7, 7, 0, 0, 0, 7 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0 },
			{ 0, 0, 0, 7, 7, 7, 7, 7, 7, 7, 0, 0, 0, 0, 7 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 0 },
			{ 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 7, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0 },
			{ 0, 0, 7, 0, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 7 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 7, 7, 7, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 7, 0, 0, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 7, 0, 0, 0, 7, 7, 0, 0, 0, 0, 0, 0, 7, 0 },
			{ 0, 7, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0 },
			{ 0, 7, 0, 0, 0, 0, 0, 7, 7, 7, 0, 0, 0, 7, 0 },
			{ 0, 7, 0, 0, 7, 0, 0, 0, 0, 7, 7, 0, 0, 7, 0 },
			{ 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 7, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 7, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7 },
			{ 0, 0, 0, 0, 7, 7, 7, 7, 7, 7, 7, 7, 0, 7, 7 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 7 },
			{ 7, 7, 0, 0, 0, 0, 7, 7, 7, 7, 7, 7, 0, 0, 7 },
			{ 0, 7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7 },
			{ 0, 7, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7 },
			{ 0, 7, 0, 0, 0, 7, 7, 0, 0, 0, 7, 7, 7, 7, 7 },
			{ 0, 7, 0, 0, 0, 7, 0, 0, 0, 0, 7, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0 } };

	private Paint paint;
	private int start_x = 0, start_y = 0;
	private int goto_x = 11, goto_y = 23;
	private Stack<Point> path;
	private AStar mAStar;
		
	public Classroom(MainView mainview) {
		context = mainview;
		cube_width = MainView.screenW / (float) COL;
		cube_height = MainView.screenH / (float) ROW;
		mAStar = new AStar(map);
		paint = new Paint();
		paint.setColor(Color.CYAN);
	}

	public void draw(Canvas canvas) {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (map[i][j] == 0) {
					paint.setColor(Color.BLACK);
					canvas.drawRect(j * cube_width, i * cube_height, (j + 1)
							* cube_width, (i + 1) * cube_height, paint);
				} else {
					paint.setColor(Color.YELLOW);
					canvas.drawRect(j * cube_width, i * cube_height, (j + 1)
							* cube_width, (i + 1) * cube_height, paint);
				}
			}// end for j
		}// end for i
		//drawPath(canvas);
	}
	
	private void drawPath(Canvas canvas){
		paint.setColor(Color.RED);
		float pre_x = 0, pre_y = 0;
		for (int i = 0; i < path.size(); i++) {
			Point pt = path.get(i);
			if (i == 0) {
				canvas.drawLine(cube_width * pt.x + cube_width / 2, cube_height
						* pt.y + cube_height / 2, cube_width * pt.x
						+ cube_width / 2, cube_height * pt.y + cube_height / 2,
						paint);
			} else {
				canvas.drawLine(pre_x, pre_y, cube_width * pt.x + cube_width
						/ 2, cube_height * pt.y + cube_height / 2, paint);
			}
			pre_x = cube_width * pt.x + cube_width / 2;
			pre_y = cube_height * pt.y + cube_height / 2;
		}// end for i
	}

	public void onTouch(MotionEvent event) {
		float x = event.getX(), y = event.getY();
		goto_x = (int) (x / cube_width);
		goto_y = (int) (y / cube_height);
	}
	
	public void logic() {
		long t1=System.currentTimeMillis();
		path=mAStar.mazePath(start_x, start_y, goto_x, goto_y);
		long t2=System.currentTimeMillis();
		System.out.println("耗时--->"+(t2-t1));
	}
}// end class
