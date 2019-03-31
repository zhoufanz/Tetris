package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 * ��������
 * 
 * @author xy
 * 
 */
public abstract class Layer {

	/**
	 * �ڱ߾�
	 */
	protected static final int PADDING;
	/**
	 * �߿���
	 */
	protected static final int BORDER;
	static {
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	/**
	 * ����ͼƬ�Ŀ��
	 */
	private static final int IMG_W = Img.WINDOW.getWidth(null);
	/**
	 * ����ͼƬ�ĸ߶�
	 */
	private static final int IMG_H = Img.WINDOW.getHeight(null);
	/**
	 * ����ͼƬ�Ŀ��
	 */
	protected static final int NUM_W = Img.NUM.getWidth(null)/10;
	/**
	 * ����ͼƬ�ĸ߶�
	 */
	protected static final int NUM_H = Img.NUM.getHeight(null);
	/**
	 * Ѫ��ͼƬ���
	 */
	private static final int RECT_W = Img.RECT.getWidth(null);
	/**
	 * Ѫ��ͼƬ�߶�
	 */
	private static final int RECT_H = Img.RECT.getHeight(null);
	/**
	 * �������ڵ�X����
	 */
	protected int x;
	/**
	 * �������ڵ�Y����
	 */
	protected int y;
	/**
	 * �������ڵĿ��
	 */
	protected int w;
	/**
	 * �������ڵĸ߶�
	 */
	protected int h;
	/**
	 * ���ݶ���
	 */
	protected GameDto dto;
	
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	protected void createWindow(Graphics g) {
		// ����
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER,
				null);
		// ����
		g.drawImage(Img.WINDOW, x + BORDER, y, w + x - BORDER, y + BORDER, BORDER, 0,
				IMG_W - BORDER, BORDER, null);
		// ����
		g.drawImage(Img.WINDOW, w + x - BORDER, y, w + x, y + BORDER, IMG_W - BORDER,
				0, IMG_W, BORDER, null);
		// ����
		g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, h + y - BORDER, 0, BORDER,
				BORDER, IMG_H - BORDER, null);
		// �м�
		g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER,
				BORDER, BORDER, IMG_W - BORDER, IMG_H - BORDER, null);
		// ����
		g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, w + x, h + y - BORDER,
				IMG_W - BORDER, BORDER, IMG_W, IMG_H - BORDER, null);
		// ����
		g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, h + y, 0, IMG_H
				- BORDER, BORDER, IMG_H, null);
		// ����
		g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, h + y,
				BORDER, IMG_H - BORDER, IMG_W - BORDER, IMG_H, null);
		// ����
		g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, IMG_W
				- BORDER, IMG_H - BORDER, IMG_W, IMG_H, null);
	}

	abstract public void paint(Graphics g);
	
	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	/**
	 * ��ʾ����(�����/�Ҷ���)
	 * @param x ���Ͻ�x����
	 * @param y ���Ͻ�y����
	 * @param num Ҫ��ʾ������
	 * @param maxBit ��ʾ���ֵ����λ��
	 * @param g ���ʶ���
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String sNum = num + "";
		for(int i=0; i<maxBit; i++) {
			//ǰiλ����ʾ����䣩
			if(i >= maxBit-sNum.length()) {
				//��ʾ�ַ���maxBit-sNum.length()λΪ���֣�i - (maxBit-sNum.length())Ϊ�ַ������±�
				int idx = i - (maxBit-sNum.length());
				num = sNum.charAt(idx)-'0';
				//��������
				g.drawImage(Img.NUM, x+i*NUM_W, y, x+NUM_W+i*NUM_W, y+NUM_H, num*NUM_W, 0, (num+1)*NUM_W, NUM_H, null);
			}
		}
		
	}
	/**
	 * ����ֵ��
	 * @param y y����
	 * @param title ֵ�۱���
	 * @param number ֵ
	 * @param value ��ǰֵ
	 * @param maxValue ���ֵ
	 * @param g ���ʶ���
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		//���Ʊ���
		g.setColor(Color.BLACK);
		g.fillRect(this.x+PADDING, y, this.w-PADDING*2, NUM_H);
		g.setColor(Color.WHITE);
		g.fillRect(this.x+PADDING+2, y+2, this.w-PADDING*2-4, NUM_H-4);
		g.setColor(Color.BLACK);
		g.fillRect(this.x+PADDING+4, y+4, this.w-PADDING*2-8, NUM_H-8);
		g.setColor(Color.GREEN);
		//����ֵ��
		int w =  (int)(percent*(this.w-PADDING*2-8));
//		if(value==0 && !(this.dto.getNowlevel()==0))
//			w = this.w-PADDING*2-8;
//		//g.fillRect(this.x+PADDING+4, this.y+Img.NUM_H*2+PADDING*3+4, w, Img.NUM_H-8);
		int imgW = (int)(percent*(RECT_W-1));
//		if(value==0)
//			imgW = RECT_W-1;
		g.drawImage(Img.RECT, this.x+PADDING+4, y+4, this.x+PADDING+4+w, y+4+RECT_H, imgW, 0, imgW+1, RECT_H, null);
		//���Ʊ�������
		g.setColor(Color.WHITE);
		g.setFont(new Font("����", Font.BOLD, 24));
		g.drawString(title, this.x+PADDING+6, y+RECT_H);
		//�Ҷ���
		if(number != null) {
			for(int i=0; i<5; i++) {
				//ǰiλ����ʾ����䣩
				if(i >= 5-number.length()) {
					//��ʾ�ַ���maxBit-sNum.length()λΪ���֣�i - (maxBit-sNum.length())Ϊ�ַ������±�
					int idx = i - (5-number.length());
					String sNum = (number.charAt(idx)-'0') + "";
					//��������
					g.drawString(sNum, (this.x+this.w-PADDING-6-g.getFont().getSize()*5/2)+i*g.getFont().getSize()/2, y+RECT_H);
				}
			}
		}
	}
	/**
	 * ���л�ͼ
	 * @param img ��ʾ��ͼƬ����
	 * @param g
	 */
	protected void drawAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x+(this.w-imgW)/2, this.y+(this.h-imgH)/2, null);
	}
}
