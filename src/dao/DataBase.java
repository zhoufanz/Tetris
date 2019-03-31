package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ui.LayerData;

import dto.Player;

public class DataBase implements Data {
	/**
	 * 数据库URL
	 */
	private final String url;
	/**
	 * 数据库用户
	 */
	private final String user;
	/**
	 * 数据库密码
	 */
	private final String password;
	/**
	 * 取出前5条数据
	 */
	private static String LOAD_SQL = "select user_name, point from user_point where type_id=1 order by point desc limit ?";
	/**
	 * 插入一条数据
	 */
	private static String SAVE_SQL = "insert into user_point(user_name, point, type_id) values(?, ?, ?)";
	
	public DataBase(Map<String, String> param) {
		url = param.get("url");
		user = param.get("user");
		password = param.get("password");
		try {
			Class.forName(param.get("driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Player> loadData() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Player> players = new ArrayList<Player>();
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstm = conn.prepareStatement(LOAD_SQL);
			pstm.setInt(1, LayerData.MAX_ROW);
			rs = pstm.executeQuery();
			while(rs.next()) {
				players.add(new Player(rs.getString("user_name"), rs.getInt("point")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (pstm != null)
				try {
					pstm.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return players;
	}

	@Override
	public void saveData(Player players) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstm = conn.prepareStatement(SAVE_SQL);
			pstm.setString(1, players.getName());
			pstm.setInt(2, players.getPoint());
			pstm.setInt(3, 1);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstm != null)
				try {
					pstm.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

}
