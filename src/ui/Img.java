package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import entity.GameAct;
/**
 * ͼƬ��
 * @author xy
 */
public class Img {
	/**
	 * ͼƬ��·��
	 */
	public final static String GRAPHICS_PATH = "graphics";
	/**
	 * ����ͼƬ
	 */
	public static Image WINDOW;
	/**
	 * ���ݿ����ͼƬ
	 */
	public static Image DB;
	/**
	 * �������ݿ����ͼƬ
	 */
	public static Image DISK;
	/**
	 * ����ͼƬ
	 */
	public static Image IMG_RECT;
	/**
	 * ��ŷ���ͼƬ��ͼƬ����
	 */
	public static Image[] IMG_NEXT;
	/**
	 * ��ű���ͼƬ��ͼƬ����
	 */
	public static List<Image> BG_LIST;
	/**
	 * ��ӰͼƬ
	 */
	public static Image SHADOW;
	/**
	 * ��ʼ��ťͼƬ
	 */
	public static ImageIcon START;
	/**
	 * ���ð�ťͼƬ
	 */
	public static ImageIcon CONFIG;
	/**
	 * ��ͣͼƬ
	 */
	public static Image PAUSE;
	/**
	 * �ȼ�����ͼƬ
	 */
	public static Image LV;
	/**
	 * ��������ͼƬ
	 */
	public static Image POINT;
	/**
	 * ���б���ͼƬ
	 */
	public static Image RMLINE;
	/**
	 * ����ͼƬ
	 */
	public static Image NUM;
	/**
	 * Ѫ��ͼƬ
	 */
	public static Image RECT;
	/**
	 * ��Ϸ����ͼƬ
	 */
	public static Image PSP;
	/**
	 * ������Ϣ
	 */
	public static Image SIGN;
	
	static {
		setSkin("default");
	}
	
	public static void setSkin(String path) {
		String skinPath = GRAPHICS_PATH + "/" + path;
		//����ͼƬ
		WINDOW = new ImageIcon(skinPath + "/window/Window.png").getImage();
		//���ݿ����ͼƬ
		DB = new ImageIcon(skinPath + "/string/db.png").getImage();
		//�������ݿ����ͼƬ
		DISK = new ImageIcon(skinPath + "/string/disk.png").getImage();
		//����ͼƬ
		IMG_RECT = new ImageIcon(skinPath + "/game/rect.png").getImage();
		//��ŷ���ͼƬ��ͼƬ����
		IMG_NEXT = new Image[GameAct.MAX_TYPE];
		for(int i=0; i<IMG_NEXT.length; i++) {
			IMG_NEXT[i] = new ImageIcon(skinPath + "/game/"+i+".png").getImage();
		}
		//��ű���ͼƬ��ͼƬ����
		BG_LIST = new ArrayList<Image>();
		File dir = new File(skinPath + "/background");
		File[] files = dir.listFiles();
		for(int i=0; i<files.length; i++) {
			if(files[i].isFile()) {
				BG_LIST.add(new ImageIcon(files[i].getPath()).getImage());
			}
		}
		//��ӰͼƬ
	    SHADOW = new ImageIcon(skinPath + "/game/shadow.png").getImage();
		//��ʼ��ťͼƬ
        START = new ImageIcon(skinPath + "/string/start.png");
		//���ð�ťͼƬ
		CONFIG = new ImageIcon(skinPath + "/string/config.png");
		//��ͣͼƬ
		PAUSE = new ImageIcon(skinPath + "/string/pause.png").getImage();
		//�ȼ�����ͼƬ
        LV = new ImageIcon(skinPath + "/string/level.png").getImage();
		//��������ͼƬ
		POINT = new ImageIcon(skinPath + "/string/point.png").getImage();
		//���б���ͼƬ
		RMLINE = new ImageIcon(skinPath + "/string/rmline.png").getImage();
		//����ͼƬ
		NUM = new ImageIcon(skinPath + "/string/num.png").getImage();
		//Ѫ��ͼƬ
		RECT = new ImageIcon(skinPath + "/window/rect.png").getImage();
		//��Ϸ����ͼƬ
		PSP = new ImageIcon(skinPath + "/other/psp.png").getImage();
		//������Ϣ
		SIGN = new ImageIcon(skinPath + "/string/sign.png").getImage();
	}
	
	private Img() {}
	
}
