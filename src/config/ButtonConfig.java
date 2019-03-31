package config;

import org.dom4j.Element;

import java.io.Serializable;

public class ButtonConfig implements Serializable {
	/**
	 * ��ʼ��ť��x����
	 */
	private final int startX;
	/**
	 * ��ʼ��ť��y����
	 */
	private final int startY;
	/**
	 * ���ð�ť��x����
	 */
	private final int userConfigX;
	/**
	 * ���ð�ť��y����
	 */
	private final int userConfigY;
	/**
	 * ��ť�Ŀ��
	 */
	private final int w;
	/**
	 * ��ť�ĸ߶�
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
