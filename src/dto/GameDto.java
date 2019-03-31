package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import ui.LayerData;
import util.GameFunction;

import entity.GameAct;

public class GameDto {
	/**
	 * 游戏界面最大宽度
	 */
	public final static int MAX_X = GameConfig.getSystemConfig().getMax_X()+1; 
	/**
	 * 游戏界面最大高度
	 */
	public final static int MAX_Y = GameConfig.getSystemConfig().getMax_Y()+1;
	/**
	 * 数据库记录
	 */
	private List<Player> dbRecord;
	/**
	 * 本地记录
	 */
	private List<Player> localRecord;
	/**
	 * 游戏地图对象
	 */
	private boolean[][] gameMap;
	/**
	 * 游戏方块
	 */
	private GameAct gameAct;
	/**
	 * 下一个方块
	 */
	private int next;
	/**
	 * 现在的等级
	 */
	private int nowlevel;
	/**
	 * 现在的分数
	 */
	private int nowPoint;
	/**
	 * 消去的行数
	 */
	private int nowRemoveLine;
	/**
	 * 游戏是否开始
	 */
	private boolean isStart;
	/**
	 * 是否显示游戏阴影
	 */
	private boolean isShowShadow;
	/**
	 * 是否暂停
	 */
	private boolean isPause;
	/**
	 * 线程睡眠时间
	 */
	private long sleepTime;
	/**
	 * 构造函数
	 */
	public GameDto() {
		//初始化方块
		doInit();
	}
	
	public void doInit() {
		this.gameMap = new boolean[MAX_X][MAX_Y];
		this.nowlevel = 0;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowlevel);
		this.isPause = false;
	}
	
	public List<Player> getDbRecord() {
		return dbRecord;
	}
	public void setDbRecord(List<Player> dbRecord) {
		this.dbRecord = setSortFill(dbRecord);
	}
	public List<Player> getLocalRecord() {
		return localRecord;
	}
	public void setLocalRecord(List<Player> localRecord) {
		this.localRecord = setSortFill(localRecord);
	}
	public boolean[][] getGameMap() {
		return gameMap;
	}
	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}
	public GameAct getGameAct() {
		return gameAct;
	}
	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getNowlevel() {
		return nowlevel;
	}
	public void setNowlevel(int nowlevel) {
		this.nowlevel = nowlevel;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowlevel);
	}
	public int getNowPoint() {
		return nowPoint;
	}
	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}
	public int getNowRemoveLine() {
		return nowRemoveLine;
	}
	public void setNowRemovePoint(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}
	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
	public void changeShowShadow() {
		this.isShowShadow = !this.isShowShadow;
	}
	
	public boolean isShowShadow() {
		return this.isShowShadow;
	}
	
	public boolean isPause() {
		return isPause;
	}

	public void changePause() {
		this.isPause = !this.isPause;
	}
	
	/**
	 * 排序+填充
	 * @param players
	 * @return 由大到小排序大小为5的List
	 */
	private List<Player> setSortFill(List<Player> players) {
		if(players == null) {
			players = new ArrayList<Player>();
		}
		while(players.size() < LayerData.MAX_ROW) {
			players.add(new Player("No Data", -1));
		}
		Collections.sort(players);
		return players;
	}
	/**
	 * 设置为暂停
	 * @param b
	 */
	public void setPause(boolean b) {
		this.isPause = b;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
}
