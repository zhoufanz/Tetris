package dao;

import java.util.List;

import dto.Player;

public interface Data {
	/**
	 * ��������ǰ5�����
	 * @return ��������ǰ5�����
	 */
	List<Player> loadData();
	/**
	 * ��������
	 * @param players ��Ҫ������������
	 */
	void saveData(Player players);
}
