package config;

import java.io.Serializable;

import org.dom4j.Element;

public class DataConfig implements Serializable {
	/**
	 * 数据库配置属性
	 */
	private final DataInterfaceConfig dataA;
	/**
	 * 本地磁盘配置属性
	 */
	private final DataInterfaceConfig dataB;

	public DataConfig(Element data) {
		this.dataA = new DataInterfaceConfig(data.element("dataA"));
		this.dataB = new DataInterfaceConfig(data.element("dataB"));
	}
	/**
	 * 获得数据库配置属性
	 */
	public DataInterfaceConfig getDataA() {
		return dataA;
	}
	/**
	 * 获得本地磁盘配置属性
	 */
	public DataInterfaceConfig getDataB() {
		return dataB;
	}
	
}
