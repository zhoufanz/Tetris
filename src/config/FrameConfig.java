package config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig implements Serializable {

	/**
	 * ���ڵĿ��
	 */
	private int width;
	/**
	 * ���ڸ߶�
	 */
	private int height;
	/**
	 * �ڱ߾�
	 */
	private int padding;
	/**
	 * �߿���
	 */
	private int border;
	/**
	 * ���ڰθ�
	 */
	private int windowUp;
	/**
	 * ����
	 */
	private String title;
	/**
	 * ���������
	 */
	private int maxRow;
	/**
	 * ͼ������
	 */
	private List<LayerConfig> layerConfig = new ArrayList<LayerConfig>();
	/**
	 * ��ť����
	 */
	private ButtonConfig bConfig;
	/**
	 * ���췽��
	 */
	@SuppressWarnings("unchecked")
	public FrameConfig(Element frame) {
		this.title = frame.attributeValue("title");
		this.width = Integer.parseInt(frame.attributeValue("width"));
		this.height = Integer.parseInt(frame.attributeValue("height"));
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		this.border = Integer.parseInt(frame.attributeValue("border"));
		this.maxRow = Integer.parseInt(frame.attributeValue("maxRow"));
		List<Element> lays = frame.elements("layer");
		for(Element layer : lays) {
			LayerConfig lc = new LayerConfig(
				Integer.parseInt(layer.attributeValue("x")),
				Integer.parseInt(layer.attributeValue("y")),
				Integer.parseInt(layer.attributeValue("w")),
				Integer.parseInt(layer.attributeValue("h")),
				layer.attributeValue("className")
			);
			layerConfig.add(lc);
		}
		this.bConfig = new ButtonConfig(frame.element("button"));
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}

	public int getBorder() {
		return border;
	}

	public List<LayerConfig> getLayerConfig() {
		return layerConfig;
	}

	public int getWindowUp() {
		return windowUp;
	}

	public String getTitle() {
		return title;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public ButtonConfig getbConfig() {
		return bConfig;
	}
}
