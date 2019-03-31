package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ui.LayerData;

import dto.Player;

public class DataLocal implements Data {

	private final String path;
	
	public DataLocal(Map<String, String> param) {
		path = param.get("path");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> loadData() {
		ObjectInputStream ois = null;
		List<Player> players = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			players = (List<Player>)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player p) {
		//取出记录
		List<Player> players = this.loadData();
		//追加记录
		players.add(p);
		//如果记录数大于5，则取前5条记录，删除后面的记录
		if(players.size() > LayerData.MAX_ROW) {
			Collections.sort(players);
			while(players.size() > LayerData.MAX_ROW) {
				players.remove(players.size()-1);
			}
		}
		//存储记录
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(players);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
