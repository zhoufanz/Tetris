package config;

import java.io.Serializable;

public class LayerConfig implements Serializable {

	/**
	 * ����x����
	 */
	private int x;
	/**
	 * ����y����
	 */
	private int y;
	/**
	 * ���ڿ��
	 */
	private int w;
	/**
	 * ���ڸ߶�
	 */
	private int h;
	/**
	 * ��·��
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
