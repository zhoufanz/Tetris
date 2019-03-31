package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 * 创建窗口
 * 
 * @author xy
 * 
 */
public abstract class Layer {

	/**
	 * 内边距
	 */
	protected static final int PADDING;
	/**
	 * 边框宽度
	 */
	protected static final int BORDER;
	static {
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	/**
	 * 窗口图片的宽度
	 */
	private static final int IMG_W = Img.WINDOW.getWidth(null);
	/**
	 * 窗口图片的高度
	 */
	private static final int IMG_H = Img.WINDOW.getHeight(null);
	/**
	 * 数字图片的宽度
	 */
	protected static final int NUM_W = Img.NUM.getWidth(null)/10;
	/**
	 * 数字图片的高度
	 */
	protected static final int NUM_H = Img.NUM.getHeight(null);
	/**
	 * 血槽图片宽度
	 */
	private static final int RECT_W = Img.RECT.getWidth(null);
	/**
	 * 血槽图片高度
	 */
	private static final int RECT_H = Img.RECT.getHeight(null);
	/**
	 * 窗口所在的X坐标
	 */
	protected int x;
	/**
	 * 窗口所在的Y坐标
	 */
	protected int y;
	/**
	 * 窗口所在的宽度
	 */
	protected int w;
	/**
	 * 窗口所在的高度
	 */
	protected int h;
	/**
	 * 数据对象
	 */
	protected GameDto dto;
	
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	protected void createWindow(Graphics g) {
		// 左上
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER,
				null);
		// 中上
		g.drawImage(Img.WINDOW, x + BORDER, y, w + x - BORDER, y + BORDER, BORDER, 0,
				IMG_W - BORDER, BORDER, null);
		// 右上
		g.drawImage(Img.WINDOW, w + x - BORDER, y, w + x, y + BORDER, IMG_W - BORDER,
				0, IMG_W, BORDER, null);
		// 左中
		g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, h + y - BORDER, 0, BORDER,
				BORDER, IMG_H - BORDER, null);
		// 中间
		g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER,
				BORDER, BORDER, IMG_W - BORDER, IMG_H - BORDER, null);
		// 右中
		g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, w + x, h + y - BORDER,
				IMG_W - BORDER, BORDER, IMG_W, IMG_H - BORDER, null);
		// 左下
		g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, h + y, 0, IMG_H
				- BORDER, BORDER, IMG_H, null);
		// 中下
		g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, h + y,
				BORDER, IMG_H - BORDER, IMG_W - BORDER, IMG_H, null);
		// 右下
		g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, IMG_W
				- BORDER, IMG_H - BORDER, IMG_W, IMG_H, null);
	}

	abstract public void paint(Graphics g);
	
	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	/**
	 * 显示数字(左填充/右对齐)
	 * @param x 左上角x坐标
	 * @param y 左上角y坐标
	 * @param num 要显示的数字
	 * @param maxBit 显示数字的最大位数
	 * @param g 画笔对象
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String sNum = num + "";
		for(int i=0; i<maxBit; i++) {
			//前i位不显示（填充）
			if(i >= maxBit-sNum.length()) {
				//显示字符第maxBit-sNum.length()位为数字，i - (maxBit-sNum.length())为字符串的下标
				int idx = i - (maxBit-sNum.length());
				num = sNum.charAt(idx)-'0';
				//绘制数字
				g.drawImage(Img.NUM, x+i*NUM_W, y, x+NUM_W+i*NUM_W, y+NUM_H, num*NUM_W, 0, (num+1)*NUM_W, NUM_H, null);
			}
		}
		
	}
	/**
	 * 绘制值槽
	 * @param y y坐标
	 * @param title 值槽标题
	 * @param number 值
	 * @param value 当前值
	 * @param maxValue 最大值
	 * @param g 画笔对象
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		//绘制背景
		g.setColor(Color.BLACK);
		g.fillRect(this.x+PADDING, y, this.w-PADDING*2, NUM_H);
		g.setColor(Color.WHITE);
		g.fillRect(this.x+PADDING+2, y+2, this.w-PADDING*2-4, NUM_H-4);
		g.setColor(Color.BLACK);
		g.fillRect(this.x+PADDING+4, y+4, this.w-PADDING*2-8, NUM_H-8);
		g.setColor(Color.GREEN);
		//绘制值槽
		int w =  (int)(percent*(this.w-PADDING*2-8));
//		if(value==0 && !(this.dto.getNowlevel()==0))
//			w = this.w-PADDING*2-8;
//		//g.fillRect(this.x+PADDING+4, this.y+Img.NUM_H*2+PADDING*3+4, w, Img.NUM_H-8);
		int imgW = (int)(percent*(RECT_W-1));
//		if(value==0)
//			imgW = RECT_W-1;
		g.drawImage(Img.RECT, this.x+PADDING+4, y+4, this.x+PADDING+4+w, y+4+RECT_H, imgW, 0, imgW+1, RECT_H, null);
		//绘制标题文字
		g.setColor(Color.WHITE);
		g.setFont(new Font("黑体", Font.BOLD, 24));
		g.drawString(title, this.x+PADDING+6, y+RECT_H);
		//右对齐
		if(number != null) {
			for(int i=0; i<5; i++) {
				//前i位不显示（填充）
				if(i >= 5-number.length()) {
					//显示字符第maxBit-sNum.length()位为数字，i - (maxBit-sNum.length())为字符串的下标
					int idx = i - (5-number.length());
					String sNum = (number.charAt(idx)-'0') + "";
					//绘制数字
					g.drawString(sNum, (this.x+this.w-PADDING-6-g.getFont().getSize()*5/2)+i*g.getFont().getSize()/2, y+RECT_H);
				}
			}
		}
	}
	/**
	 * 正中绘图
	 * @param img 显示的图片对象
	 * @param g
	 */
	protected void drawAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x+(this.w-imgW)/2, this.y+(this.h-imgH)/2, null);
	}
}
