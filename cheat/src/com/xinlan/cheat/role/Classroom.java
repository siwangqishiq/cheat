package com.xinlan.cheat.role;

import com.xinlan.view.MainView;

public class Classroom {
	public static final int ROW=100;
	public static final int COL=50;
	private MainView context;
	public int[][] map;
	
	public Classroom(MainView mainview){
		context=mainview;
	}
}//end class
