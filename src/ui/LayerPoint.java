package ui;
import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	/**
	 * ��ʾ�������λ
	 */
	private static final int MAXBIT = 5;
	/**
	 * ������ʾ��λ��
	 */
	private static int NUM_POSITION_X;
	/**
	 * ���������������
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		NUM_POSITION_X = this.w - PADDING - MAXBIT*NUM_W;
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		//��������
		g.drawImage(Img.POINT, this.x+PADDING, this.y+PADDING, null);
		//����
		drawNumberLeftPad(this.x+NUM_POSITION_X, this.y+PADDING, this.dto.getNowPoint(), MAXBIT, g);
		//���б���
		g.drawImage(Img.RMLINE, this.x+PADDING, this.y+NUM_H+PADDING*2, null);
		//������
		drawNumberLeftPad(this.x+NUM_POSITION_X, this.y+NUM_H+PADDING*2, this.dto.getNowRemoveLine(), MAXBIT, g);
		//����ֵ
		int rmLine = this.dto.getNowRemoveLine();
		int y = this.y+NUM_H*2+PADDING*3;
		drawRect(y, "��һ��", null, (double)(rmLine%LEVEL_UP)/(double)LEVEL_UP, g);
	}
	
}