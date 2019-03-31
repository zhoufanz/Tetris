package ui;

import java.awt.Graphics;

public class LayerBackground extends Layer {

	public LayerBackground(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(Img.BG_LIST.get(this.dto.getNowlevel()%Img.BG_LIST.size()), this.x, this.y, this.w, this.h, null);
	}
}
