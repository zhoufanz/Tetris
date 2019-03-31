package config;

import java.io.Serializable;

import org.dom4j.Element;

public class DataConfig implements Serializable {
	/**
	 * ���ݿ���������
	 */
	private final DataInterfaceConfig dataA;
	/**
	 * ���ش�����������
	 */
	private final DataInterfaceConfig dataB;

	public DataConfig(Element data) {
		this.dataA = new DataInterfaceConfig(data.element("dataA"));
		this.dataB = new DataInterfaceConfig(data.element("dataB"));
	}
	/**
	 * ������ݿ���������
	 */
	public DataInterfaceConfig getDataA() {
		return dataA;
	}
	/**
	 * ��ñ��ش�����������
	 */
	public DataInterfaceConfig getDataB() {
		return dataB;
	}
	
}
