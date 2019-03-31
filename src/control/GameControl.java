package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetrisService;
import ui.JFrameGame;
import ui.JPanelGame;
import ui.window.FrameConfig;
import ui.window.JFrameSavePoint;

/**
 * ������Ҽ����¼�
 * ���ƻ���
 * ������Ϸ�߼�
 * @author xy
 */
public class GameControl {
	/**
	 *��Ϸ����
	 */
	private JPanelGame panelGame;
	/**
	 * ��Ϸ�߼�����
	 */
	private GameService gameService;
	/**
	 * ���ݿ�
	 */
	private Data dataA;
	/**
	 * ���ش���
	 */
	private Data dataB;
	/**
	 * ����Դ����
	 */
	private GameDto dto;
	/**
	 * ��Ϸ���ô���
	 */
	private FrameConfig frameConfig;
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer, Method> actionList;
	/**
	 * �����������
	 */
	private JFrameSavePoint frameSavePoint;
	/**
	 * ��Ϸ�߳�
	 */
	private Thread gameThread = null;
	
	public GameControl() {
		//��������Դ
		this.dto = new GameDto();
		//������Ϸ�߼��飨������ϷԴ��
		this.gameService = new GameTetrisService(dto);
		//�������ݿ����
		dataA = this.creatDataObject(GameConfig.getDataConfig().getDataA());
		//ȡ�����ݿ�����
		this.dto.setDbRecord(dataA.loadData());
		//�������ش��̶���
		dataB = this.creatDataObject(GameConfig.getDataConfig().getDataB());
		//ȡ�����ش�������
		this.dto.setLocalRecord(dataB.loadData());
		//������Ϸ���
		this.panelGame = new JPanelGame(dto, this);
		//��ȡ�û���������
		setControlConfig();
		//������Ϸ���ô���
		this.frameConfig = new FrameConfig(this);
		//����������Ϸ�Ĵ���
		this.frameSavePoint = new JFrameSavePoint(this);
		//������Ϸ���ڣ���װ��Ϸ��壩
	    new JFrameGame(this.panelGame);
	}

	/**
	 * �����û�������������Ӧ����
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {
		if(!this.dto.isStart())
			return;
		if(this.dto.isPause()) {
			for(Map.Entry<Integer, Method> e : actionList.entrySet()) {
				try {
					if(e.getValue().getName().equals("keyFunLeft")) {
						if(e.getKey().equals(keyCode)) {
							this.gameService.keyFunLeft();
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return;
		}
		try {
			if(actionList.containsKey(keyCode)) {
				actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		panelGame.repaint();
	}
	/**
	 * ��ʾ�û����ô���
	 */
	public void setShowUserConfigUI() {
		this.frameConfig.setVisible(true);
	}
	/**
	 * �Ӵ��ڵ������ڵ��л�
	 */
	public void setOver() {
		//���¶�ȡ�û���������
		this.setControlConfig();
		//�������ػ�
		this.panelGame.repaint();
	}
	/**
	 * ��ȡ�û���������
	 */
	private void setControlConfig() {
		actionList = new HashMap<Integer, Method>();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> keySet = (HashMap<Integer, String>) ois.readObject();
			for(Map.Entry<Integer, String> e : keySet.entrySet()) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
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
	 * �������ݶ���
	 * @param data
	 * @return
	 */
	private Data creatDataObject(DataInterfaceConfig data) {
		try {
			Class<?> cls = Class.forName(data.getClassName());
			Constructor<?> ctr = cls.getConstructor(Map.class);
			return (Data) ctr.newInstance(data.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void start() {
		//��Ϸ��ʼ��
		this.gameService.startGame();
		//�������ܵ��
		this.panelGame.setEnabled(false);
		//������Ϸ�߳�
		this.gameThread = new MainThread();
		//��ʼ��Ϸ�߳�
		gameThread.start();
		//ˢ�»���
		this.panelGame.repaint();
	}
	
	private class MainThread extends Thread {
		@Override
		public void run() {
			while(dto.isStart()) {
				try {
					//�����̵߳ȴ�ʱ��
					Thread.sleep(dto.getSleepTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(dto.isPause()) {
					continue;
				}
				gameService.mainAction();
				panelGame.repaint();
			}
			afterLose();
		}
	}
	/**
	 * ��Ϸʧ�ܺ�Ĳ���
	 */
	public void afterLose() {
		//��ʾ���յ÷ִ���
		this.frameSavePoint.setVisible(true);
		
		this.frameSavePoint.showPoint(this.dto.getNowPoint()+"");
		//��ť���Ե��
		this.panelGame.setEnabled(true);
	}
	/**
	 * �������
	 */
	public void savePoint(String name) {
		//������Ҷ���
		Player player = new Player(name, this.dto.getNowPoint());
		//�����ݱ��浽���ݿ�
		this.dataA.saveData(player);
		//�����ݱ��浽���ش���
		this.dataB.saveData(player);
		//���¶�ȡ�����Ϣ
		//ȡ�����ݿ�����
		this.dto.setDbRecord(dataA.loadData());
		//ȡ�����ش�������
		this.dto.setLocalRecord(dataB.loadData());
		//ˢ�½���
		this.panelGame.repaint();
	}
	/**
	 * ��ý���
	 */
	public void request() {
		this.panelGame.requestFocus();
	}
}
