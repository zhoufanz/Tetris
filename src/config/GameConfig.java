package config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *关于static 与 final 的使用问题
 *1.static 与 final 连用修饰变量时，必须在声明变量的同时初始化该变量
 *原因：
 *	1）final 修饰的变量必须进行唯一的初始化赋值（即使用其前必须赋给它值），其位置可以有两处：一处为变量声明时即初始化，第二处为构造器的初始化。
 *  2）static 方法只能调用静态的方法，使用类中静态的成员。static 块同理。
 *  3）static 与 final 连用修饰变量时，如果不在变量声明处初始化，那么由于static修饰的变量会在类加载时创建，其他类不用创建该对象即可使用其变量，这时
 *     在构造器中再次初始化该变量显然是没有任何意义的。
 * @author xy
 */
public class GameConfig {

	/**
	 * 窗口属性配置
	 */
	private static FrameConfig FRAME_CONFIG;
	/**
	 * 数据属性配置
	 */
	private static DataConfig DATA_CONFIG;
	/**
	 * 系统属性配置
	 */
	private static SystemConfig SYSTEM_CONFIG;
	/**
	 * 是否是开发阶段
	 */
	private final static boolean IS_DEBUG = true;
	static {
		try {
			if(IS_DEBUG) {
				SAXReader reader = new SAXReader();
				Document doc = reader.read("data/cfg.xml");
				Element game = doc.getRootElement();
				//配置窗口
				FRAME_CONFIG = new FrameConfig(game.element("frame"));
				//配置数据库连接
				DATA_CONFIG = new DataConfig(game.element("data"));
				//配置系统属性
				SYSTEM_CONFIG = new SystemConfig(game.element("system"));
			} else {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/frameConfig.dat"));
				FRAME_CONFIG = (FrameConfig)ois.readObject();
				ois = new ObjectInputStream(new FileInputStream("data/systemConfig.dat"));
				SYSTEM_CONFIG = (SystemConfig)ois.readObject();
				ois = new ObjectInputStream(new FileInputStream("data/dataConfig.dat"));
				DATA_CONFIG = (DataConfig)ois.readObject();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造器私有化
	 */
	private GameConfig() {
	}

	/**
	 * 窗口属性配置
	 * @return FrameConfig
	 */
	public static FrameConfig getFrameConfig() {
		return FRAME_CONFIG;
	}
	/**
	 * 数据属性配置
	 * @return DataConfig
	 */
	public static DataConfig getDataConfig() {
		return DATA_CONFIG;
	}
	/**
	 * 系统属性配置
	 * @return SystemConfig
	 */
	public static SystemConfig getSystemConfig() {
		return SYSTEM_CONFIG;
	}
	
	public static void main(String[] args) throws Exception {
		ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("data/frameConfig.dat"));
		ois.writeObject(FRAME_CONFIG);
		ois = new ObjectOutputStream(new FileOutputStream("data/systemConfig.dat"));
		ois.writeObject(SYSTEM_CONFIG);
		ois = new ObjectOutputStream(new FileOutputStream("data/dataConfig.dat"));
		ois.writeObject(DATA_CONFIG);
	}
}
