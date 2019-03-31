package dto;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
	
	/**
	 * �����
	 */
	private String name;
	/**
	 * ��ҷ���
	 */
	private int point;
	
	public Player(String name, int point) {
		this.name = name;
		this.point = point;
	}

	public String getName() {
		return name;
	}

	public int getPoint() {
		return point;
	}

	@Override
	public int compareTo(Player player) {
		return player.point-this.point;
	}
	
}
