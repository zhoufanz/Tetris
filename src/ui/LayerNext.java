package ui;

import java.awt.Graphics;

public class LayerNext extends Layer {
	
	public LayerNext(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		//���ƴ���
		this.createWindow(g);
		//������һ������
		if(this.dto.isStart()) {
			drawAtCenter(Img.IMG_NEXT[this.dto.getNext()], g);
		}
	}
}
