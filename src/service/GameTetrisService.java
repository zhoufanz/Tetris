package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;

import dto.GameDto;
import entity.GameAct;

public class GameTetrisService implements GameService {

	private GameDto dto;
	
	private final static int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	
	public GameTetrisService(GameDto dto) {
		this.dto = dto;
	}

	/**
	 * ����������Ϊ��
	 */
	public boolean keyUp() {
		this.dto.getGameAct().round(this.dto.getGameMap());
		return true;
	}
	/**
	 * ����������Ϊ��
	 */
	public boolean keyDown() {
		if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
			return false;
		}
		//�����Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		//��÷������
		Point[] points = this.dto.getGameAct().getActPoints();
		//������ѻ�����ͼ����
		for(int i=0; i<points.length; i++) {
			map[points[i].x][points[i].y] = true;
		}
		//���в�����������
		int rmCount = rmCount();
		//���,����
		this.levelUp(rmCount);
		//ˢ��һ���·���
		this.dto.getGameAct().init(this.dto.getNext());
		//���������һ��
		this.dto.setNext(new Random().nextInt(GameAct.MAX_TYPE));
		//��Ϸ�Ƿ�ʧ��
		if(this.isLose()) {
			this.dto.setStart(false);
		}
		return true;
	}
	/**
	 * ��Ϸ�Ƿ�ʧ��
	 */
	private boolean isLose() {
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		for (Point point : actPoints) {
			if(this.dto.getGameMap()[point.x][point.y]) {
				//������ѻ�����ͼ����
				for(int i=0; i<actPoints.length; i++) {
					this.dto.getGameMap()[actPoints[i].x][actPoints[i].y] = true;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * ���,����
	 * @param rmCount
	 */
	private void levelUp(int rmCount) {
		Map<Integer, Integer> scoreMap = GameConfig.getSystemConfig().getScoreMap();
		int rmLine = this.dto.getNowRemoveLine();
		int lv = rmLine/LEVEL_UP;
		this.dto.setNowlevel(lv);
		this.dto.setNowPoint(this.dto.getNowPoint()+scoreMap.get(rmCount));
	}
	/**
	 * ���в�����������
	 * @return
	 */
	private int rmCount() {
		boolean[][] gameMap = this.dto.getGameMap();
		//������
		int rmCount = 0;
		for(int y=0; y<GameDto.MAX_Y; y++) {
			//�жϸ����Ƿ��ܱ���ȥ
			if(isCanRemove(y, gameMap)) {
				//����
				removeLine(y, gameMap);
				//��������һ
				rmCount++;
			}
		}
		//����������
		this.dto.setNowRemovePoint(this.dto.getNowRemoveLine()+rmCount);
		//������Ϸ��ͼ
		this.dto.setGameMap(gameMap);
		return rmCount;
	}
	/**
	 * ����
	 * @param y
	 */
	private void removeLine(int removeLineNumber, boolean[][] gameMap) {
		for (int x=0; x<GameDto.MAX_X; x++) {
			for (int y=removeLineNumber; y>0; y--) {
				gameMap[x][y] = gameMap[x][y-1];
			}
			gameMap[x][0] = false;
		}
	}

	/**
	 * �����Ƿ��ܱ���ȥ
	 * @param y
	 * @param gameMap
	 * @return
	 */
	private boolean isCanRemove(int y, boolean[][] gameMap) {
		for(int x=0; x<GameDto.MAX_X; x++) {
			if(!gameMap[x][y]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ����������Ϊ��
	 */
	public boolean keyLeft() {
		this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		return true;
	}
	/**
	 * ����������Ϊ��
	 */
	public boolean keyRight() {
		this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		return true;
	}
	/**
	 * ���׼�
	 */
	@Override
	public boolean keyFunUp() {
		int point = this.dto.getNowPoint();
		int rmLine = this.dto.getNowRemoveLine();
		point += 10;
		rmLine++;
		this.dto.setNowPoint(point);
		this.dto.setNowRemovePoint(rmLine);
		if(rmLine%LEVEL_UP == 0) {
			int lv = this.dto.getNowlevel();
			lv++;
			this.dto.setNowlevel(lv);
		}
		return true;
	}
	/**
	 * ��������
	 */
	@Override
	public boolean keyFunDown() {
		while(!this.keyDown());
		return true;
	}

	/**
	 * ��ͣ
	 */
	@Override
	public boolean keyFunLeft() {
		this.dto.changePause();
		return true;
	}
	/**
	 * �Ƿ���ʾ��Ӱ
	 */
	@Override
	public boolean keyFunRight() {
		this.dto.changeShowShadow();
		return true;
	}
	/**
	 * ��ʼ��Ϸ
	 */
	@Override
	public void startGame() {
		this.dto.setGameAct(new GameAct(new Random().nextInt(GameAct.MAX_TYPE)));
		this.dto.setNext(new Random().nextInt(GameAct.MAX_TYPE));
		this.dto.setStart(true);
		this.dto.doInit();
	}
	/**
	 * ��Ϸ�̵߳��õ�������
	 */
	@Override
	public void mainAction() {
		this.keyDown();
	}
}
