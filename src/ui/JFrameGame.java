package ui;


import javax.swing.JFrame;

import util.FrameUtil;
import config.FrameConfig;
import config.GameConfig;

public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame panel) {
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//设置标题
		this.setTitle(fCfg.getTitle());
		//设置关闭方式
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//设置大小
		this.setSize(fCfg.getWidth(), fCfg.getHeight());
		//设置不可以调整大小
		this.setResizable(false);
		//设置居中
		FrameUtil.setFrameCenter(this);
		//添加Panel
		this.add(panel);
		//设置为可见窗口
		this.setVisible(true);
	}
	
}
