package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import config.GameConfig;

public class FrameUtil {

	public static void setFrameCenter(JFrame jf) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		int x = (d.width - jf.getWidth())/2;
		int y = (d.height - jf.getHeight())/2 - GameConfig.getFrameConfig().getWindowUp();
		jf.setLocation(x, y);
	}
}
