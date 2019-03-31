package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

public class DataTest implements Data {

	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("xiaoming", 1780));
		players.add(new Player("xiaogang", 2670));
		players.add(new Player("xiaoqiang", 3700));
		players.add(new Player("xiaoling", 1030));
		players.add(new Player("xiaomei", 500));
		return players;
	}

	@Override
	public void saveData(Player players) {
		
	}

}
