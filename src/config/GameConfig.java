package config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *����static �� final ��ʹ������
 *1.static �� final �������α���ʱ������������������ͬʱ��ʼ���ñ���
 *ԭ��
 *	1��final ���εı����������Ψһ�ĳ�ʼ����ֵ����ʹ����ǰ���븳����ֵ������λ�ÿ�����������һ��Ϊ��������ʱ����ʼ�����ڶ���Ϊ�������ĳ�ʼ����
 *  2��static ����ֻ�ܵ��þ�̬�ķ�����ʹ�����о�̬�ĳ�Ա��static ��ͬ��
 *  3��static �� final �������α���ʱ��������ڱ�����������ʼ������ô����static���εı������������ʱ�����������಻�ô����ö��󼴿�ʹ�����������ʱ
 *     �ڹ��������ٴγ�ʼ���ñ�����Ȼ��û���κ�����ġ�
 * @author xy
 */
public class GameConfig {

	/**
	 * ������������
	 */
	private static FrameConfig FRAME_CONFIG;
	/**
	 * ������������
	 */
	private static DataConfig DATA_CONFIG;
	/**
	 * ϵͳ��������
	 */
	private static SystemConfig SYSTEM_CONFIG;
	/**
	 * �Ƿ��ǿ����׶�
	 */
	private final static boolean IS_DEBUG = true;
	static {
		try {
			if(IS_DEBUG) {
				SAXReader reader = new SAXReader();
				Document doc = reader.read("data/cfg.xml");
				Element game = doc.getRootElement();
				//���ô���
				FRAME_CONFIG = new FrameConfig(game.element("frame"));
				//�������ݿ�����
				DATA_CONFIG = new DataConfig(game.element("data"));
				//����ϵͳ����
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
	 * ������˽�л�
	 */
	private GameConfig() {
	}

	/**
	 * ������������
	 * @return FrameConfig
	 */
	public static FrameConfig getFrameConfig() {
		return FRAME_CONFIG;
	}
	/**
	 * ������������
	 * @return DataConfig
	 */
	public static DataConfig getDataConfig() {
		return DATA_CONFIG;
	}
	/**
	 * ϵͳ��������
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
