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
		//获得数据源对象
		this.dto = dto;
		//连接游戏控制器
		this.gameControl = gameControl;
		//初始化按钮组件
		initComponent();
		//初始化层
		initLayer(this.dto);
		//安装键盘监听器
		this.addKeyListener(new PlayerControl(gameControl));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//调用基类的方法
		super.paintComponent(g);
		//绘制游戏界面
		for(int i=0; i<layers.size(); i++) {
			layers.get(i).paint(g);
		}
	}
	/**
	 * 设置按钮是否可以点击
	 * @param b
	 */
	public void setEnabled(boolean b) {
		this.bStart.setEnabled(b);
		//this.bUserConfig.setEnabled(b);
	}
	/**
	 * 初始化层
	 */
	private void initLayer(GameDto dto) {
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//获取层的配置参数
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
	 * 初始化按钮组件
	 */
	private void initComponent() {
		//自由布局
		this.setLayout(null);
		//设置按钮位置
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
		//添加开始按钮事件
		this.bStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				//获取焦点
				requestFocusInWindow();
			}
		});
		//添加设置按钮事件
		this.bUserConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dto.setPause(true);
				gameControl.setShowUserConfigUI();
			}
		});
		//添加按钮
		this.add(bStart);
		this.add(bUserConfig);
	}
}
