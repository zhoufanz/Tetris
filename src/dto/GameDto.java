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
	 * ��Ϸ���������
	 */
	public final static int MAX_X = GameConfig.getSystemConfig().getMax_X()+1; 
	/**
	 * ��Ϸ�������߶�
	 */
	public final static int MAX_Y = GameConfig.getSystemConfig().getMax_Y()+1;
	/**
	 * ���ݿ��¼
	 */
	private List<Player> dbRecord;
	/**
	 * ���ؼ�¼
	 */
	private List<Player> localRecord;
	/**
	 * ��Ϸ��ͼ����
	 */
	private boolean[][] gameMap;
	/**
	 * ��Ϸ����
	 */
	private GameAct gameAct;
	/**
	 * ��һ������
	 */
	private int next;
	/**
	 * ���ڵĵȼ�
	 */
	private int nowlevel;
	/**
	 * ���ڵķ���
	 */
	private int nowPoint;
	/**
	 * ��ȥ������
	 */
	private int nowRemoveLine;
	/**
	 * ��Ϸ�Ƿ�ʼ
	 */
	private boolean isStart;
	/**
	 * �Ƿ���ʾ��Ϸ��Ӱ
	 */
	private boolean isShowShadow;
	/**
	 * �Ƿ���ͣ
	 */
	private boolean isPause;
	/**
	 * �߳�˯��ʱ��
	 */
	private long sleepTime;
	/**
	 * ���캯��
	 */
	public GameDto() {
		//��ʼ������
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
	 * ����+���
	 * @param players
	 * @return �ɴ�С�����СΪ5��List
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
	 * ����Ϊ��ͣ
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
