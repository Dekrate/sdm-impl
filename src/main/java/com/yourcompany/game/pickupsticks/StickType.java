package com.yourcompany.game.pickupsticks;

public enum StickType {
	BLACK(25),
	BLUE(5),
	GREEN(2),
	RED(10),
	YELLOW(1);

	private final int points;

	StickType(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}
}
