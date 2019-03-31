package config;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class SystemConfig implements Serializable {
	/**
	 * x的最小坐标
	 */
	private final int min_X;
	/**
	 * x的最大坐标
	 */
	private final int max_X;
	/**
	 *y的最小坐标
	 */
	private final int min_Y;
	/**
	 *y的最大坐标
	 */
	private final int max_Y;
	/**
	 * 方块的最大种类数
	 */
	private final int maxType;
	/**
	 * 升级所需行数
	 */
	private final int levelUp;
	/**
	 * 是否可以旋转
	 */
	private final List<Boolean> isRound;
	/**
	 *方块的坐标
	 */
	private final List<Point[]> typeConfig;
	/**
	 * 分数计算方式
	 */
	private final Map<Integer, Integer> scoreMap;
	
	public SystemConfig(Element system) {
		this.min_X = Integer.parseInt(system.attributeValue("min_X"));
		this.min_Y = Integer.parseInt(system.attributeValue("min_Y"));
		this.max_X = Integer.parseInt(system.attributeValue("max_X"));
		this.max_Y = Integer.parseInt(system.attributeValue("max_Y"));
		this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
		@SuppressWarnings("unchecked")
		List<Element> eScores = system.elements("score");
		scoreMap = new HashMap<Integer, Integer>();
		for (Element eScore : eScores) {
			scoreMap.put(Integer.parseInt(eScore.attributeValue("rm")), Integer.parseInt(eScore.attributeValue("point")));
		}
		isRound = new ArrayList<Boolean>();
		typeConfig = new ArrayList<Point[]>();
		@SuppressWarnings("unchecked")
		List<Element> eRects = system.elements("rect");
		this.maxType = eRects.size();
		for(Element eRect : eRects) {
			@SuppressWarnings("unchecked")
			List<Element> ePoints = eRect.elements("point");
			isRound.add(Boolean.parseBoolean(eRect.attributeValue("round")));
			Point[] points = new Point[ePoints.size()];
			for(int i=0; i<points.length; i++) {
				Element ePoint = ePoints.get(i);
				int x = Integer.parseInt(ePoint.attributeValue("x"));
				int y = Integer.parseInt(ePoint.attributeValue("y"));
				points[i] = new Point(x, y);
			}
			typeConfig.add(points);
		}
	}
	
	public int getMin_X() {
		return min_X;
	}
	public int getMax_X() {
		return max_X;
	}
	public int getMin_Y() {
		return min_Y;
	}
	public int getMax_Y() {
		return max_Y;
	}
	public List<Point[]> getTypeConfig() {
		return typeConfig;
	}

	public int getMaxType() {
		return maxType;
	}

	public int getLevelUp() {
		return levelUp;
	}

	public List<Boolean> getIsRound() {
		return isRound;
	}

	public Map<Integer, Integer> getScoreMap() {
		return scoreMap;
	}
}
