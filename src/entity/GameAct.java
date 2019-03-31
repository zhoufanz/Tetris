package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;
/**
 * ����ʵ����
 * @author xy
 */
public class GameAct {

	/**
	 * ��������������
	 */
	public static final int MAX_TYPE = GameConfig.getSystemConfig().getMaxType();
	/**
	 * ���������
	 */
	private Point[] actPoints = null;
	/**
	 * x���������
	 */
	private final static int MAX_X = GameConfig.getSystemConfig().getMax_X();
	/**
	 * x����С����
	 */
	private final static int MIN_X = GameConfig.getSystemConfig().getMin_X();
	/**
	 *y���������
	 */
	private final static int MAX_Y = GameConfig.getSystemConfig().getMax_Y();
	/**
	 *y����С����
	 */
	private final static int MIN_Y = GameConfig.getSystemConfig().getMin_Y();
	/**
	 *���������
	 */
	private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	/**
	 * ��������ͱ��
	 */
	private int typeCode;
	
	public GameAct(int typeCode) {
		init(typeCode);
	}

	/**
	 * ����code��ֵ����ʼ��һ������
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
	 * ������ƶ�
	 * @param moveXΪX�����ƫ����
	 * @param moveYΪY�����ƫ����
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
	 * ������ת
	 */
	public void round(boolean[][] gameMap) {
		//����Ƿ����򣬲���Ҫ��ת
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
	 * �Ƿ񳬳��߽�
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
