package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;

import dto.Player;

public abstract class LayerData extends Layer {

	/**
	 * ֵ�ۼ��
	 */
	private static int SPAN;
	/**
	 * ֵ�۵Ŀ��
	 */
	private static int RECT_H = Img.RECT.getHeight(null)+8;
	/**
	 * ֵ�۵ĸ���
	 */
	public static final int MAX_ROW = GameConfig.getFrameConfig().getMaxRow();
	/**
	 * ֵ�۳�ʼ��y����
	 */
	private static int START_Y;
	
	protected LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	/**
	 * ��ʾ���� 
	 * @param img ͼƬ
	 * @param players ���
	 * @param g
	 */
	public void showData(Image img, List<Player> players, Graphics g) {
		SPAN = (this.h-2*PADDING-MAX_ROW*RECT_H-img.getHeight(null))/MAX_ROW;
		START_Y = this.y + PADDING + img.getHeight(null) + SPAN;
		//��ʾ����
		g.drawImage(img, this.x+PADDING, this.y+PADDING, null);
		//��ʾֵ��
		for(int i=0; i<MAX_ROW; i++) {
			double percent = (double)this.dto.getNowPoint()/(double)players.get(i).getPoint();
			percent = percent > 1 ? 1.0 : percent;
			String name = players.get(i).getName();
			String point = name.equals("No Data") ? null : players.get(i).getPoint()+"";
			drawRect(START_Y+i*(RECT_H+SPAN), name, point, percent, g);
		}
	}
	
	@Override
	public abstract void paint(Graphics g);

}
