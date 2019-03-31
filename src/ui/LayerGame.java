package ui;

import java.awt.Graphics;
import java.awt.Point;

public class LayerGame extends Layer {
	/**
	 * ����ͼƬ�ĸ߶�
	 */
	private static final int IMG_SIZE = Img.IMG_RECT.getHeight(null);
	/**
	 * ����������X����
	 */
	private static final int LEFT_X = 9;
	/**
	 * �����ҷ���С��X����
	 */
	private static final int RIGHT_X = 0;
	
	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		//���ƴ���
		this.createWindow(g);
		if(this.dto.isStart()) {
			Point[] points = this.dto.getGameAct().getActPoints();
			//������Ӱ
			drawShadow(points, g);
			//���Ʒ���
			drawAct(points, g);
		}
		//������Ϸ��ͼ
		drawMap(g);
		//������ͣ
		if(this.dto.isPause() && this.dto.isStart()) {
			drawAtCenter(Img.PAUSE, g);
		}
	}
	/**
	 * ������Ϸ��ͼ
	 * @param points
	 * @param g
	 */
	private void drawMap(Graphics g) {
		boolean[][] map = this.dto.getGameMap();
		int lv = this.dto.getNowlevel();
		int imgIdx = lv == 0 ? 0 : (lv-1)%7+1;
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[x].length; y++) {
				if(map[x][y])
					draw(x, y, this.dto.isStart() ? imgIdx : 8, g);
			}
		}
	}

	/**
	 * ���Ʒ���
	 * @param points
	 * @param g
	 */
	private void drawAct(Point[] points, Graphics g) {
		//��÷������ͱ��
		int typeCode = this.dto.getGameAct().getTypeCode();
		for(int i=0; i<points.length; i++) {
			draw(points[i].x, points[i].y, typeCode+1, g);
		}
	}

	/**
	 * ������Ӱ
	 * @param points
	 * @param isShadow
	 * @param g
	 */
	private void drawShadow(Point[] points, Graphics g) {
		if(!this.dto.isShowShadow()) {
			return;
		}
		int leftX = LEFT_X;
		int rightX = RIGHT_X;
		for(Point p : points) {
			leftX = p.x < leftX ? p.x : leftX;
			rightX = p.x > rightX ? p.x : rightX;
		}
		//g.fillRect(this.x+BORDER+leftX*IMG_SIZE, this.y+BORDER, (rightX-leftX+1)*IMG_SIZE, this.h-2*BORDER);
		int w = (rightX-leftX+1)*IMG_SIZE;
		int h = this.h-2*BORDER;
		g.drawImage(Img.SHADOW, this.x+BORDER+leftX*IMG_SIZE, this.y+BORDER, this.x+BORDER+leftX*IMG_SIZE+w, this.y+BORDER+h, 0, 0, 1, 1, null);
	}

	/**
	 * ���Ʒ���
	 * @param x
	 * @param y
	 * @param imgIdx
	 * @param g
	 */
	private void draw(int x, int y, int imgIdx, Graphics g){
		g.drawImage(Img.IMG_RECT, this.x+x*IMG_SIZE+BORDER, this.y+y*IMG_SIZE+BORDER, this.x+x*IMG_SIZE+IMG_SIZE+BORDER, this.y+y*IMG_SIZE+IMG_SIZE+BORDER, imgIdx*IMG_SIZE, 0, imgIdx*IMG_SIZE+IMG_SIZE, IMG_SIZE, null);
	}
}
