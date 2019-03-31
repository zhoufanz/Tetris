package ui;


import javax.swing.JFrame;

import util.FrameUtil;
import config.FrameConfig;
import config.GameConfig;

public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame panel) {
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//���ñ���
		this.setTitle(fCfg.getTitle());
		//���ùرշ�ʽ
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//���ô�С
		this.setSize(fCfg.getWidth(), fCfg.getHeight());
		//���ò����Ե�����С
		this.setResizable(false);
		//���þ���
		FrameUtil.setFrameCenter(this);
		//���Panel
		this.add(panel);
		//����Ϊ�ɼ�����
		this.setVisible(true);
	}
	
}
