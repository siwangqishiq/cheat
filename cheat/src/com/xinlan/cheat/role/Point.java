package com.xinlan.cheat.role;

public class Point {
	int x, y;
	Point parent;
	int f, g, h;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.parent = null;
		f = g = h = 0;
	}

	public Point(int x, int y, int g, int h, Point parent) {
		this.x = x;
		this.y = y;
		this.g = g;
		this.h = h;
		f = g + h;
		this.parent = parent;
	}
}//end class
