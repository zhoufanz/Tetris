package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import control.GameControl;
import ui.Img;
import util.FrameUtil;

public class FrameConfig extends JFrame {

	private JButton bOk = new JButton("确定");
	
	private JButton bCancel = new JButton("取消");
	
	private JButton bApply = new JButton("应用");
	
	private JLabel lable = new JLabel();
	
	private JList skinList;
	
	private DefaultListModel skinData = new DefaultListModel();
	
	private Image[] skinViewList;
	
	private GameControl gameControl;
	
	private final String PATH = "data/control.dat";
	
	private final String[] METHOD_NAME = {
			"keyRight", "keyUp", "keyLeft", "keyDown", "keyFunLeft", "keyFunUp", "keyFunRight", "keyFunDown"
	};
	/**
	 * 控制键
	 */
	private TextControl[] controlKeys;
	
	public FrameConfig(GameControl gameControl) {
		//设置游戏控制器
		this.gameControl = gameControl;
		//设置为边界布局
		this.setLayout(new BorderLayout());
		//设置面板的大小
		this.setSize(674, 376);
		//设置标题
		this.setTitle("设置");
		//初始化按键
		initKey();
		//居中显示
		FrameUtil.setFrameCenter(this);
		//不可以调整大小
		this.setResizable(false);
		//添加主面板
		this.add(createMainPanel(), BorderLayout.CENTER);
		//添加按钮面板
		this.add(createButtonPanel(), BorderLayout.SOUTH);
		//设置关闭触发的事件
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FrameConfig.this.gameControl.setOver();
				//主窗口获得焦点
				FrameConfig.this.gameControl.request();
			}
		});
}
	/**
	 * 初始化按键
	 */
	private void initKey() {
		controlKeys = new TextControl[8];
		int x = 5;
		int y = 58;
		int w = 65;
		int h = 20;
		for(int i=0; i<4; i++) {
			controlKeys[i] = new TextControl(x, y, w, h, METHOD_NAME[i]);
			y += 33;
		}
		x = 585;
		y = 68;
		for(int i=4; i<8; i++) {
			controlKeys[i] = new TextControl(x, y, w, h, METHOD_NAME[i]);
			y += 30;
		}
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> keySet = (HashMap<Integer, String>) ois.readObject();
			Set<Map.Entry<Integer, String>> set = keySet.entrySet();
			for(Map.Entry<Integer, String> entry : set) {
				for(TextControl tc : controlKeys) {
					if(tc.getMethodName().equals(entry.getValue())) {
						tc.setKeyCode(entry.getKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 创建主面板
	 * @return
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.add("控制", createControlPanel());
		jtp.add("皮肤", createSkinPanel());
		jtp.add("帮助", createHelpPanel());
		return jtp;
	}
	/**
	 * 创建帮助面板
	 * @return
	 */
	private JTextArea createHelpPanel() {
		String rt = "\r\n";
		String info = "大叉======快速下落" + rt +
				"方块======暂停" + rt +
				"三角======作弊键" + rt +
				"圆圈======阴影开关" + rt +
				"方向键上====变形";
		return new JTextArea(info);
	}
	/**
	 * 创建皮肤面板
	 * @return
	 */
	private JPanel createSkinPanel() {
		//皮肤路径
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		//皮肤图片
		this.skinViewList = new Image[files.length];
		for (int i=0; i<files.length; i++) {
			//创建列表项
			this.skinData.addElement(files[i].getName());
			//加入皮肤图片
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "/view.png").getImage();
		}
		//创建皮肤面板（边界布局）
		JPanel pSkin = new JPanel(new BorderLayout());
		//创建列表
		this.skinList = new JList(this.skinData);
		//默认选择第一项
		this.skinList.setSelectedIndex(0);
		//为列表添加事件
		this.skinList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}
			
		});
		//将列表加入面板
		pSkin.add(new JScrollPane(this.skinList), BorderLayout.WEST);
		//创建皮肤显示面板
		JPanel pShowSkin = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Image skinImage = skinViewList[skinList.getSelectedIndex()];
				int x = (this.getWidth() - skinImage.getWidth(null))/2;
				int y = (this.getHeight()-skinImage.getHeight(null))/2;
				g.drawImage(skinImage, x, y, null);
			}
		};
		//将皮肤显示面板加入到皮肤面板中
		pSkin.add(pShowSkin, BorderLayout.CENTER);
		return pSkin;
	}
	/**
	 * 创建控制面板
	 * @return
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Img.PSP, 0, 0, null);
			}
		};
		//将布局管理器置为空
		jp.setLayout(null);
		for(TextControl tc : this.controlKeys) {
			jp.add(tc);
		}
		return jp;
	}
	/**
	 * 创建按钮面板
	 * @return
	 */
	private JPanel createButtonPanel() {
		//流式布局，右对齐
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//为按钮添加时间
		this.bOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()) {
					setVisible(false);
					gameControl.setOver();
					//主窗口获得焦点
					gameControl.request();
				}
			}
		});
		this.bApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeConfig();
				gameControl.setOver();
			}
		});
		this.bCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
				//主窗口获得焦点
				gameControl.request();
			}
		});
		//添加提示信息
		this.lable.setForeground(Color.RED);
		panel.add(lable);
		//添加按钮
		panel.add(bOk);
		panel.add(bCancel);
		panel.add(bApply);
		return panel;
	}
	/**
	 * 向配置文件中写配置信息(如果没有该文件系统会自动创建它)
	 */
	private boolean writeConfig() {
		ObjectOutputStream oos = null;
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for(TextControl tc : this.controlKeys) {
			if(tc.getKeyCode() == 0) {
				this.lable.setText("请设置按键！");
				return false;
			}
			keySet.put(tc.getKeyCode(), tc.getMethodName());
		}
		if(keySet.size() < 8) {
			this.lable.setText("存在重复的按键！");
			return false;
		}
		//设置皮肤
		String path = (String) this.skinData.get(this.skinList.getSelectedIndex());
		Img.setSkin(path);
		try {
			oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
		} catch (Exception e) {
			this.lable.setText(e.getMessage());
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				this.lable.setText(e.getMessage());
				return false;
			}
		}	
		this.lable.setText(null);
		return true;
	}
}