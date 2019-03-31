package ui;
import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	/**
	 * 显示的最大数位
	 */
	private static final int MAXBIT = 5;
	/**
	 * 数字显示的位置
	 */
	private static int NUM_POSITION_X;
	/**
	 * 升级所需的消行数
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		NUM_POSITION_X = this.w - PADDING - MAXBIT*NUM_W;
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		//分数标题
		g.drawImage(Img.POINT, this.x+PADDING, this.y+PADDING, null);
		//分数
		drawNumberLeftPad(this.x+NUM_POSITION_X, this.y+PADDING, this.dto.getNowPoint(), MAXBIT, g);
		//消行标题
		g.drawImage(Img.RMLINE, this.x+PADDING, this.y+NUM_H+PADDING*2, null);
		//消行数
		drawNumberLeftPad(this.x+NUM_POSITION_X, this.y+NUM_H+PADDING*2, this.dto.getNowRemoveLine(), MAXBIT, g);
		//经验值
		int rmLine = this.dto.getNowRemoveLine();
		int y = this.y+NUM_H*2+PADDING*3;
		drawRect(y, "下一级", null, (double)(rmLine%LEVEL_UP)/(double)LEVEL_UP, g);
	}
	
}