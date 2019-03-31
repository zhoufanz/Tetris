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

	private JButton bOk = new JButton("ȷ��");
	
	private JButton bCancel = new JButton("ȡ��");
	
	private JButton bApply = new JButton("Ӧ��");
	
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
	 * ���Ƽ�
	 */
	private TextControl[] controlKeys;
	
	public FrameConfig(GameControl gameControl) {
		//������Ϸ������
		this.gameControl = gameControl;
		//����Ϊ�߽粼��
		this.setLayout(new BorderLayout());
		//�������Ĵ�С
		this.setSize(674, 376);
		//���ñ���
		this.setTitle("����");
		//��ʼ������
		initKey();
		//������ʾ
		FrameUtil.setFrameCenter(this);
		//�����Ե�����С
		this.setResizable(false);
		//��������
		this.add(createMainPanel(), BorderLayout.CENTER);
		//��Ӱ�ť���
		this.add(createButtonPanel(), BorderLayout.SOUTH);
		//���ùرմ������¼�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FrameConfig.this.gameControl.setOver();
				//�����ڻ�ý���
				FrameConfig.this.gameControl.request();
			}
		});
}
	/**
	 * ��ʼ������
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
	 * ���������
	 * @return
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.add("����", createControlPanel());
		jtp.add("Ƥ��", createSkinPanel());
		jtp.add("����", createHelpPanel());
		return jtp;
	}
	/**
	 * �����������
	 * @return
	 */
	private JTextArea createHelpPanel() {
		String rt = "\r\n";
		String info = "���======��������" + rt +
				"����======��ͣ" + rt +
				"����======���׼�" + rt +
				"ԲȦ======��Ӱ����" + rt +
				"�������====����";
		return new JTextArea(info);
	}
	/**
	 * ����Ƥ�����
	 * @return
	 */
	private JPanel createSkinPanel() {
		//Ƥ��·��
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		//Ƥ��ͼƬ
		this.skinViewList = new Image[files.length];
		for (int i=0; i<files.length; i++) {
			//�����б���
			this.skinData.addElement(files[i].getName());
			//����Ƥ��ͼƬ
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "/view.png").getImage();
		}
		//����Ƥ����壨�߽粼�֣�
		JPanel pSkin = new JPanel(new BorderLayout());
		//�����б�
		this.skinList = new JList(this.skinData);
		//Ĭ��ѡ���һ��
		this.skinList.setSelectedIndex(0);
		//Ϊ�б�����¼�
		this.skinList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}
			
		});
		//���б�������
		pSkin.add(new JScrollPane(this.skinList), BorderLayout.WEST);
		//����Ƥ����ʾ���
		JPanel pShowSkin = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Image skinImage = skinViewList[skinList.getSelectedIndex()];
				int x = (this.getWidth() - skinImage.getWidth(null))/2;
				int y = (this.getHeight()-skinImage.getHeight(null))/2;
				g.drawImage(skinImage, x, y, null);
			}
		};
		//��Ƥ����ʾ�����뵽Ƥ�������
		pSkin.add(pShowSkin, BorderLayout.CENTER);
		return pSkin;
	}
	/**
	 * �����������
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
		//�����ֹ�������Ϊ��
		jp.setLayout(null);
		for(TextControl tc : this.controlKeys) {
			jp.add(tc);
		}
		return jp;
	}
	/**
	 * ������ť���
	 * @return
	 */
	private JPanel createButtonPanel() {
		//��ʽ���֣��Ҷ���
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//Ϊ��ť���ʱ��
		this.bOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()) {
					setVisible(false);
					gameControl.setOver();
					//�����ڻ�ý���
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
				//�����ڻ�ý���
				gameControl.request();
			}
		});
		//�����ʾ��Ϣ
		this.lable.setForeground(Color.RED);
		panel.add(lable);
		//��Ӱ�ť
		panel.add(bOk);
		panel.add(bCancel);
		panel.add(bApply);
		return panel;
	}
	/**
	 * �������ļ���д������Ϣ(���û�и��ļ�ϵͳ���Զ�������)
	 */
	private boolean writeConfig() {
		ObjectOutputStream oos = null;
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for(TextControl tc : this.controlKeys) {
			if(tc.getKeyCode() == 0) {
				this.lable.setText("�����ð�����");
				return false;
			}
			keySet.put(tc.getKeyCode(), tc.getMethodName());
		}
		if(keySet.size() < 8) {
			this.lable.setText("�����ظ��İ�����");
			return false;
		}
		//����Ƥ��
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