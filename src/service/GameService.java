package service;


public interface GameService {
	/**
	 * ����������Ϊ��
	 */
	public boolean keyUp();
	/**
	 * ����������Ϊ��
	 */
	public boolean keyDown();
	/**
	 * ����������Ϊ��
	 */
	public boolean keyLeft();
	/**
	 * ����������Ϊ��
	 */
	public boolean keyRight();
	/**
	 * ����
	 */
	public boolean keyFunUp();
	/**
	 * ���
	 */
	public boolean keyFunDown();
	/**
	 * ����
	 */
	public boolean keyFunLeft();
	/**
	 * ԲȦ
	 */
	public boolean keyFunRight();
	/**
	 * ��ʼ��Ϸ
	 */
	public void startGame();
	/**
	 * ��Ϸ�߳�����Ϊ
	 */
	public void mainAction();
}
