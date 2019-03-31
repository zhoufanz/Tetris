package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {

	/**
	 * 标题图片的宽度
	 */
	private static final int LV_W = Img.LV.getWidth(null);

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//标题
		int centerX = (this.w-LV_W)/2;
		g.drawImage(Img.LV, this.x+centerX, this.y+PADDING, null);
		//显示分数
		drawNumberLeftPad(this.x+centerX, this.y+64, this.dto.getNowlevel(), 2, g);
	}
}
