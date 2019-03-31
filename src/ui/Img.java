package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import entity.GameAct;
/**
 * 图片库
 * @author xy
 */
public class Img {
	/**
	 * 图片的路径
	 */
	public final static String GRAPHICS_PATH = "graphics";
	/**
	 * 窗口图片
	 */
	public static Image WINDOW;
	/**
	 * 数据库标题图片
	 */
	public static Image DB;
	/**
	 * 本地数据库标题图片
	 */
	public static Image DISK;
	/**
	 * 方块图片
	 */
	public static Image IMG_RECT;
	/**
	 * 存放方块图片的图片数组
	 */
	public static Image[] IMG_NEXT;
	/**
	 * 存放背景图片的图片数组
	 */
	public static List<Image> BG_LIST;
	/**
	 * 阴影图片
	 */
	public static Image SHADOW;
	/**
	 * 开始按钮图片
	 */
	public static ImageIcon START;
	/**
	 * 配置按钮图片
	 */
	public static ImageIcon CONFIG;
	/**
	 * 暂停图片
	 */
	public static Image PAUSE;
	/**
	 * 等级标题图片
	 */
	public static Image LV;
	/**
	 * 分数标题图片
	 */
	public static Image POINT;
	/**
	 * 消行标题图片
	 */
	public static Image RMLINE;
	/**
	 * 数字图片
	 */
	public static Image NUM;
	/**
	 * 血槽图片
	 */
	public static Image RECT;
	/**
	 * 游戏配置图片
	 */
	public static Image PSP;
	/**
	 * 制作信息
	 */
	public static Image SIGN;
	
	static {
		setSkin("default");
	}
	
	public static void setSkin(String path) {
		String skinPath = GRAPHICS_PATH + "/" + path;
		//窗口图片
		WINDOW = new ImageIcon(skinPath + "/window/Window.png").getImage();
		//数据库标题图片
		DB = new ImageIcon(skinPath + "/string/db.png").getImage();
		//本地数据库标题图片
		DISK = new ImageIcon(skinPath + "/string/disk.png").getImage();
		//方块图片
		IMG_RECT = new ImageIcon(skinPath + "/game/rect.png").getImage();
		//存放方块图片的图片数组
		IMG_NEXT = new Image[GameAct.MAX_TYPE];
		for(int i=0; i<IMG_NEXT.length; i++) {
			IMG_NEXT[i] = new ImageIcon(skinPath + "/game/"+i+".png").getImage();
		}
		//存放背景图片的图片数组
		BG_LIST = new ArrayList<Image>();
		File dir = new File(skinPath + "/background");
		File[] files = dir.listFiles();
		for(int i=0; i<files.length; i++) {
			if(files[i].isFile()) {
				BG_LIST.add(new ImageIcon(files[i].getPath()).getImage());
			}
		}
		//阴影图片
	    SHADOW = new ImageIcon(skinPath + "/game/shadow.png").getImage();
		//开始按钮图片
        START = new ImageIcon(skinPath + "/string/start.png");
		//配置按钮图片
		CONFIG = new ImageIcon(skinPath + "/string/config.png");
		//暂停图片
		PAUSE = new ImageIcon(skinPath + "/string/pause.png").getImage();
		//等级标题图片
        LV = new ImageIcon(skinPath + "/string/level.png").getImage();
		//分数标题图片
		POINT = new ImageIcon(skinPath + "/string/point.png").getImage();
		//消行标题图片
		RMLINE = new ImageIcon(skinPath + "/string/rmline.png").getImage();
		//数字图片
		NUM = new ImageIcon(skinPath + "/string/num.png").getImage();
		//血槽图片
		RECT = new ImageIcon(skinPath + "/window/rect.png").getImage();
		//游戏配置图片
		PSP = new ImageIcon(skinPath + "/other/psp.png").getImage();
		//制作信息
		SIGN = new ImageIcon(skinPath + "/string/sign.png").getImage();
	}
	
	private Img() {}
	
}
