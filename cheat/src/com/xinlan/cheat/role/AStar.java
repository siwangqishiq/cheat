package com.xinlan.cheat.role;

import java.util.LinkedList;
import java.util.Stack;

/**
 * A*组件
 * 
 * @author Panyi
 * 
 */
public class AStar {
	public int[][] map;
	public int startx, starty, endx, endy;
	private LinkedList<Point> openList = new LinkedList<Point>();
	private LinkedList<Point> closeList = new LinkedList<Point>();
	private Stack<Point> retList = new Stack<Point>();

	public AStar(int[][] map) {
		this.map = map;
	}

	/**
	 * ] 输入开始点 结束点 自动计算最优路径
	 * 
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @return
	 */
	public Stack<Point> mazePath(int startx, int starty, int endx, int endy) {
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;

		retList.clear();// 清空原有返回栈
		openList.clear();// 清空原有开启列表
		closeList.clear();// 清空原有关闭列表
		System.gc();// 回收无用资源
		Point start = new Point(startx, starty);
		openList.add(start);// 开始点加入open列表
		Point curPt = null;// 当前点
		do {
			curPt = selectMinFPoint(openList);// 选择出F值最小的节点 设为当前节点
			if (curPt == null)
				break;
			openList.remove(curPt);// 将当前节点从开放列表中删除
			closeList.add(curPt);// 将当前节点放入关闭列表中
			checkSurroundPoint(curPt, openList, closeList);// 检查周围点 设置开启关闭列表
		} while (!isInList(endx, endy, openList) || openList.isEmpty());

		Point p = getPointFromList(endx, endy, openList);// 取出目的点
		while (p != null) {// 回溯链表
			retList.push(p);
			p = p.parent;// 寻找父节点
		}
		System.out.println(retList.size());
		return retList;
	}

	/**
	 * 检查指定节点周围节点
	 * 
	 * @param pt
	 */
	public void checkSurroundPoint(Point pt, LinkedList<Point> openList,
			LinkedList<Point> closeList) {
		processSide(pt.x + 1, pt.y, pt, openList, closeList);// 检查右边
		processSide(pt.x, pt.y + 1, pt, openList, closeList);// 检查下边
		processSide(pt.x - 1, pt.y, pt, openList, closeList);// 检查左边
		processSide(pt.x, pt.y - 1, pt, openList, closeList);// 检查上边
	}

	public void processSide(int newx, int newy, Point pt,
			LinkedList<Point> openList, LinkedList<Point> closeList) {
		if (!isInList(newx, newy, closeList) && canGo(newx, newy)) {// 右侧点
			if (isInList(newx, newy, openList)) {// 新增节点在开放列表中
				// 比较新的G值与原有G值
				int newg = pt.f + 10 + getHuffmanValue(newx, newy, endx, endy);
				Point p = getPointFromList(newx, newy, openList);
				if (newg < p.g) {// 新的G值小于原来的
					p.parent = pt;
					p.f = pt.f + 10;
					p.h = getHuffmanValue(newx, newy, endx, endy);
				}
			} else {// 新增节点不在开放列表中
				openList.add(new Point(newx, newy, pt.f + 10, getHuffmanValue(
						newx, newy, endx, endy), pt));
			}// end is inopenList
		}
	}

	/**
	 * 选择出F值最小的节点
	 * 
	 * @param list
	 * @return
	 */
	private Point selectMinFPoint(LinkedList<Point> list) {
		Point minPoint = null;// 返回值
		int curMinFValue = Integer.MAX_VALUE;// 当前最小F值
		for (int i = 0; i < list.size(); i++) {
			Point pt = list.get(i);
			if (curMinFValue > pt.f) {
				minPoint = pt;
				curMinFValue = minPoint.f;
			}
		}// end for i
		return minPoint;
	}

	/**
	 * 检查节点是否在列表内
	 * 
	 * @param pt
	 * @param list
	 * @return
	 */
	private boolean isInList(int x, int y, LinkedList<Point> list) {
		for (int i = 0; i < list.size(); i++) {
			Point pp = list.get(i);
			if (x == pp.x && y == pp.y) {
				return true;
			}
		}// end for i
		return false;
	}

	/**
	 * 从列表中选择出指定点
	 * 
	 * @param x
	 * @param y
	 * @param list
	 * @return
	 */
	private Point getPointFromList(int x, int y, LinkedList<Point> list) {
		for (int i = 0; i < list.size(); i++) {
			Point p = list.get(i);
			if (p.x == x && p.y == y) {
				return p;
			}
		}// end for i
		return null;
	}

	/**
	 * 判断指定点是否可达
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean canGo(int x, int y) {
		if (y < 0 || y >= map.length) {// 行越界情况
			return false;
		} else if (x < 0 || x >= map[y].length) {// 列越界情况
			return false;
		}
		if (map[y][x] != 0) {// 障碍物
			return false;
		}
		return true;
	}

	/**
	 * 求出两点之间霍夫曼距离
	 * 
	 * @param x
	 * @param y
	 * @param endx
	 * @param endy
	 * @return
	 */
	private int getHuffmanValue(int x, int y, int endx, int endy) {
		return Math.abs(endx - x) + Math.abs(endy - y);
	}
}// end class
