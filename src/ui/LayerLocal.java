package ui;

import java.awt.Graphics;

public class LayerLocal extends LayerData {
	
	public LayerLocal(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		showData(Img.DISK, this.dto.getLocalRecord(), g);
	}

}
