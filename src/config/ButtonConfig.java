package config;

import org.dom4j.Element;

import java.io.Serializable;

public class ButtonConfig implements Serializable {
	/**
	 * 开始按钮的x坐标
	 */
	private final int startX;
	/**
	 * 开始按钮的y坐标
	 */
	private final int startY;
	/**
	 * 设置按钮的x坐标
	 */
	private final int userConfigX;
	/**
	 * 设置按钮的y坐标
	 */
	private final int userConfigY;
	/**
	 * 按钮的宽度
	 */
	private final int w;
	/**
	 * 按钮的高度
	 */
	private final int h;
	
	public ButtonConfig(Element eButton) {
		this.w = Integer.parseInt(eButton.attributeValue("w"));
		this.h = Integer.parseInt(eButton.attributeValue("h"));
		this.startX = Integer.parseInt(eButton.element("start").attributeValue("x"));
		this.startY = Integer.parseInt(eButton.element("start").attributeValue("y"));
		this.userConfigX = Integer.parseInt(eButton.element("userConfig").attributeValue("x"));
		this.userConfigY = Integer.parseInt(eButton.element("userConfig").attributeValue("y"));
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getUserConfigX() {
		return userConfigX;
	}

	public int getUserConfigY() {
		return userConfigY;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
	
}
