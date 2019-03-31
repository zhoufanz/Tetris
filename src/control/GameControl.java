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
 * 接受玩家键盘事件
 * 控制画面
 * 控制游戏逻辑
 * @author xy
 */
public class GameControl {
	/**
	 *游戏界面
	 */
	private JPanelGame panelGame;
	/**
	 * 游戏逻辑控制
	 */
	private GameService gameService;
	/**
	 * 数据库
	 */
	private Data dataA;
	/**
	 * 本地磁盘
	 */
	private Data dataB;
	/**
	 * 数据源对象
	 */
	private GameDto dto;
	/**
	 * 游戏配置窗口
	 */
	private FrameConfig frameConfig;
	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionList;
	/**
	 * 保存分数窗口
	 */
	private JFrameSavePoint frameSavePoint;
	/**
	 * 游戏线程
	 */
	private Thread gameThread = null;
	
	public GameControl() {
		//创建数据源
		this.dto = new GameDto();
		//创建游戏逻辑块（连接游戏源）
		this.gameService = new GameTetrisService(dto);
		//创建数据库对象
		dataA = this.creatDataObject(GameConfig.getDataConfig().getDataA());
		//取出数据库数据
		this.dto.setDbRecord(dataA.loadData());
		//创建本地磁盘对象
		dataB = this.creatDataObject(GameConfig.getDataConfig().getDataB());
		//取出本地磁盘数据
		this.dto.setLocalRecord(dataB.loadData());
		//创建游戏面板
		this.panelGame = new JPanelGame(dto, this);
		//读取用户按键设置
		setControlConfig();
		//创建游戏配置窗口
		this.frameConfig = new FrameConfig(this);
		//创建保存游戏的窗口
		this.frameSavePoint = new JFrameSavePoint(this);
		//创建游戏窗口（安装游戏面板）
	    new JFrameGame(this.panelGame);
	}

	/**
	 * 根据用户按键，调用相应方法
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
	 * 显示用户设置窗口
	 */
	public void setShowUserConfigUI() {
		this.frameConfig.setVisible(true);
	}
	/**
	 * 子窗口到主窗口的切换
	 */
	public void setOver() {
		//重新读取用户按键设置
		this.setControlConfig();
		//主窗口重绘
		this.panelGame.repaint();
	}
	/**
	 * 读取用户按键设置
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
	 * 创建数据对象
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
		//游戏初始化
		this.gameService.startGame();
		//按键不能点击
		this.panelGame.setEnabled(false);
		//创建游戏线程
		this.gameThread = new MainThread();
		//开始游戏线程
		gameThread.start();
		//刷新画面
		this.panelGame.repaint();
	}
	
	private class MainThread extends Thread {
		@Override
		public void run() {
			while(dto.isStart()) {
				try {
					//计算线程等待时间
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
	 * 游戏失败后的操作
	 */
	public void afterLose() {
		//显示最终得分窗口
		this.frameSavePoint.setVisible(true);
		
		this.frameSavePoint.showPoint(this.dto.getNowPoint()+"");
		//按钮可以点击
		this.panelGame.setEnabled(true);
	}
	/**
	 * 保存分数
	 */
	public void savePoint(String name) {
		//创建玩家对象
		Player player = new Player(name, this.dto.getNowPoint());
		//将数据保存到数据库
		this.dataA.saveData(player);
		//将数据保存到本地磁盘
		this.dataB.saveData(player);
		//重新读取玩家信息
		//取出数据库数据
		this.dto.setDbRecord(dataA.loadData());
		//取出本地磁盘数据
		this.dto.setLocalRecord(dataB.loadData());
		//刷新界面
		this.panelGame.repaint();
	}
	/**
	 * 获得焦点
	 */
	public void request() {
		this.panelGame.requestFocus();
	}
}
