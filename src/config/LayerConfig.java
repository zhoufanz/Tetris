package config;

import java.io.Serializable;

public class LayerConfig implements Serializable {

	/**
	 * 窗口x坐标
	 */
	private int x;
	/**
	 * 窗口y坐标
	 */
	private int y;
	/**
	 * 窗口宽度
	 */
	private int w;
	/**
	 * 窗口高度
	 */
	private int h;
	/**
	 * 类路径
	 */
	private String className;
	
	
	
	public LayerConfig(int x, int y, int w, int h, String className) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.className = className;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	public String getClassName() {
		return className;
	}
	
	

}
