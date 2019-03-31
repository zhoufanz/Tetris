package service;


public interface GameService {
	/**
	 * 控制器按键为上
	 */
	public boolean keyUp();
	/**
	 * 控制器按键为下
	 */
	public boolean keyDown();
	/**
	 * 控制器按键为左
	 */
	public boolean keyLeft();
	/**
	 * 控制器按键为右
	 */
	public boolean keyRight();
	/**
	 * 三角
	 */
	public boolean keyFunUp();
	/**
	 * 大叉
	 */
	public boolean keyFunDown();
	/**
	 * 方块
	 */
	public boolean keyFunLeft();
	/**
	 * 圆圈
	 */
	public boolean keyFunRight();
	/**
	 * 开始游戏
	 */
	public void startGame();
	/**
	 * 游戏线程主行为
	 */
	public void mainAction();
}
