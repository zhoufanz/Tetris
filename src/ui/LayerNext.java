package ui;

import java.awt.Graphics;

public class LayerNext extends Layer {
	
	public LayerNext(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		//绘制窗口
		this.createWindow(g);
		//绘制下一个方块
		if(this.dto.isStart()) {
			drawAtCenter(Img.IMG_NEXT[this.dto.getNext()], g);
		}
	}
}
