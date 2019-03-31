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
	 * 控制器按键为上
	 */
	public boolean keyUp() {
		this.dto.getGameAct().round(this.dto.getGameMap());
		return true;
	}
	/**
	 * 控制器按键为下
	 */
	public boolean keyDown() {
		if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
			return false;
		}
		//获得游戏地图
		boolean[][] map = this.dto.getGameMap();
		//获得方块对象
		Point[] points = this.dto.getGameAct().getActPoints();
		//将方块堆积到地图数组
		for(int i=0; i<points.length; i++) {
			map[points[i].x][points[i].y] = true;
		}
		//消行并返回消行数
		int rmCount = rmCount();
		//算分,升级
		this.levelUp(rmCount);
		//刷新一个新方块
		this.dto.getGameAct().init(this.dto.getNext());
		//随机生成下一个
		this.dto.setNext(new Random().nextInt(GameAct.MAX_TYPE));
		//游戏是否失败
		if(this.isLose()) {
			this.dto.setStart(false);
		}
		return true;
	}
	/**
	 * 游戏是否失败
	 */
	private boolean isLose() {
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		for (Point point : actPoints) {
			if(this.dto.getGameMap()[point.x][point.y]) {
				//将方块堆积到地图数组
				for(int i=0; i<actPoints.length; i++) {
					this.dto.getGameMap()[actPoints[i].x][actPoints[i].y] = true;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 算分,升级
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
	 * 消行并返回消行数
	 * @return
	 */
	private int rmCount() {
		boolean[][] gameMap = this.dto.getGameMap();
		//消行数
		int rmCount = 0;
		for(int y=0; y<GameDto.MAX_Y; y++) {
			//判断该行是否能被消去
			if(isCanRemove(y, gameMap)) {
				//消行
				removeLine(y, gameMap);
				//消行数加一
				rmCount++;
			}
		}
		//设置消行数
		this.dto.setNowRemovePoint(this.dto.getNowRemoveLine()+rmCount);
		//设置游戏地图
		this.dto.setGameMap(gameMap);
		return rmCount;
	}
	/**
	 * 消行
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
	 * 该行是否能被消去
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
	 * 控制器按键为左
	 */
	public boolean keyLeft() {
		this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		return true;
	}
	/**
	 * 控制器按键为右
	 */
	public boolean keyRight() {
		this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		return true;
	}
	/**
	 * 作弊键
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
	 * 快速下落
	 */
	@Override
	public boolean keyFunDown() {
		while(!this.keyDown());
		return true;
	}

	/**
	 * 暂停
	 */
	@Override
	public boolean keyFunLeft() {
		this.dto.changePause();
		return true;
	}
	/**
	 * 是否显示阴影
	 */
	@Override
	public boolean keyFunRight() {
		this.dto.changeShowShadow();
		return true;
	}
	/**
	 * 开始游戏
	 */
	@Override
	public void startGame() {
		this.dto.setGameAct(new GameAct(new Random().nextInt(GameAct.MAX_TYPE)));
		this.dto.setNext(new Random().nextInt(GameAct.MAX_TYPE));
		this.dto.setStart(true);
		this.dto.doInit();
	}
	/**
	 * 游戏线程调用的主方法
	 */
	@Override
	public void mainAction() {
		this.keyDown();
	}
}
