package dao;

import java.util.List;

import dto.Player;

public interface Data {
	/**
	 * 分数排名前5的玩家
	 * @return 分数排名前5的玩家
	 */
	List<Player> loadData();
	/**
	 * 保存数据
	 * @param players 需要保存的玩家数据
	 */
	void saveData(Player players);
}
