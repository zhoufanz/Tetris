package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;
/**
 * 方块实体类
 * @author xy
 */
public class GameAct {

	/**
	 * 方块的最大种类数
	 */
	public static final int MAX_TYPE = GameConfig.getSystemConfig().getMaxType();
	/**
	 * 方块的坐标
	 */
	private Point[] actPoints = null;
	/**
	 * x的最大坐标
	 */
	private final static int MAX_X = GameConfig.getSystemConfig().getMax_X();
	/**
	 * x的最小坐标
	 */
	private final static int MIN_X = GameConfig.getSystemConfig().getMin_X();
	/**
	 *y的最大坐标
	 */
	private final static int MAX_Y = GameConfig.getSystemConfig().getMax_Y();
	/**
	 *y的最小坐标
	 */
	private final static int MIN_Y = GameConfig.getSystemConfig().getMin_Y();
	/**
	 *方块的坐标
	 */
	private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	/**
	 * 方块的类型编号
	 */
	private int typeCode;
	
	public GameAct(int typeCode) {
		init(typeCode);
	}

	/**
	 * 根据code的值，初始化一个方块
	 * @param code
	 */
	public void init(int typeCode) {
		this.typeCode = typeCode;
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for(int i=0; i<points.length; i++) {
			this.actPoints[i] = new Point(points[i].x, points[i].y);
		}
	}
	
	public Point[] getActPoints() {
		return actPoints;
	}
	/**
	 * 方块的移动
	 * @param moveX为X方向的偏移量
	 * @param moveY为Y方向的偏移量
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		for(int i=0; i<this.actPoints.length; i++) {
			int newPointX = actPoints[i].x + moveX;
			int newPointY = actPoints[i].y + moveY;
			if(isOverZone(newPointX, newPointY, gameMap))
				return false;
		}
		for(int i=0; i<this.actPoints.length; i++) {
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}
		return true;
	}
	/**
	 * 方块旋转
	 */
	public void round(boolean[][] gameMap) {
		//如果是方块则，不需要旋转
		if(!GameConfig.getSystemConfig().getIsRound().get(typeCode))
			return;
		for(int i=1; i<this.actPoints.length; i++) {
			int newPointX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newPointY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			if(isOverZone(newPointX, newPointY, gameMap))
				return;
		}
		for(int i=1; i<this.actPoints.length; i++) {
			int newPointX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newPointY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x = newPointX;
			actPoints[i].y = newPointY;
		}
	}
	/**
	 * 是否超出边界
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isOverZone(int x, int y, boolean[][] gameMap) {
		return x<MIN_X || x>MAX_X || y<MIN_Y || y>MAX_Y || gameMap[x][y];
	}

	public int getTypeCode() {
		return typeCode;
	}
}
