package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;


import config.ButtonConfig;
import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

public class JPanelGame extends JPanel{

	private List<Layer> layers = new ArrayList<Layer>();
	
	private JButton bStart = new JButton(Img.START);
	
	private JButton bUserConfig = new JButton(Img.CONFIG);
	
	private GameControl gameControl;
	
	private GameDto dto;
	
	public JPanelGame(GameDto dto, GameControl gameControl) {
		//�������Դ����
		this.dto = dto;
		//������Ϸ������
		this.gameControl = gameControl;
		//��ʼ����ť���
		initComponent();
		//��ʼ����
		initLayer(this.dto);
		//��װ���̼�����
		this.addKeyListener(new PlayerControl(gameControl));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//���û���ķ���
		super.paintComponent(g);
		//������Ϸ����
		for(int i=0; i<layers.size(); i++) {
			layers.get(i).paint(g);
		}
	}
	/**
	 * ���ð�ť�Ƿ���Ե��
	 * @param b
	 */
	public void setEnabled(boolean b) {
		this.bStart.setEnabled(b);
		//this.bUserConfig.setEnabled(b);
	}
	/**
	 * ��ʼ����
	 */
	private void initLayer(GameDto dto) {
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//��ȡ������ò���
		List<LayerConfig> layerCfg = fCfg.getLayerConfig();
		try {
			for(LayerConfig lc : layerCfg) {
				Class<?> cls = Class.forName(lc.getClassName());
				Constructor<?> c = cls.getConstructor(int.class, int.class, int.class, int.class);
				Layer lay = (Layer) c.newInstance(lc.getX(), lc.getY(), lc.getW(), lc.getH());
				lay.setDto(dto);
				layers.add(lay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ʼ����ť���
	 */
	private void initComponent() {
		//���ɲ���
		this.setLayout(null);
		//���ð�ťλ��
		ButtonConfig bConfig= GameConfig.getFrameConfig().getbConfig();
		List<LayerConfig> layerConfigs = GameConfig.getFrameConfig().getLayerConfig();
		for(int i=0; i<layerConfigs.size(); i++) {
			LayerConfig layerButtonConfig = layerConfigs.get(i);
			if(layerButtonConfig.getClassName().equals("ui.LayerButton")) {
				int x = layerButtonConfig.getX();
				int y = layerButtonConfig.getY();
				int h = layerButtonConfig.getH();
				int w = layerButtonConfig.getW();
				int buttonW = bConfig.getW();
				int buttonH = bConfig.getH();
				int paddingX = (w-2*buttonW)/4;
				int paddingY = (h-buttonH)/2;
				this.bStart.setBounds(x+paddingX, y+paddingY, bConfig.getW(), bConfig.getH());
				this.bUserConfig.setBounds(x+3*paddingX+bConfig.getW(), y+paddingY, bConfig.getW(), bConfig.getH());
				break;
			}
		}
		//��ӿ�ʼ��ť�¼�
		this.bStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				//��ȡ����
				requestFocusInWindow();
			}
		});
		//������ð�ť�¼�
		this.bUserConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dto.setPause(true);
				gameControl.setShowUserConfigUI();
			}
		});
		//��Ӱ�ť
		this.add(bStart);
		this.add(bUserConfig);
	}
}
