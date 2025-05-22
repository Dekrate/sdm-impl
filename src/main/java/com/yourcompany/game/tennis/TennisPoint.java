package com.yourcompany.game.tennis;


public class TennisPoint {
	private int value; // 0, 15, 30, 40
	private boolean advantage; // Czy punkt to Advantage

	public TennisPoint() {
		this.value = 0;
		this.advantage = false;
	}


	public int getValue() {
		return value;
	}


	public boolean isAdvantage() {
		return advantage;
	}


	public void setAdvantage() {
		this.advantage = true;
		this.value = 40; // Advantage jest zazwyczaj po 40, ale to bardziej stan niż wartość numeryczna
	}


	public void reset() {
		this.value = 0;
		this.advantage = false;
	}


	public void resetAdvantage() {
		this.advantage = false;
		this.value = 40; // Po resetowaniu Advantage, punkt wraca do 40
	}


	public void increment() {
		if (value == 0) {
			value = 15;
		} else if (value == 15) {
			value = 30;
		} else if (value == 30) {
			value = 40;
		}


	}

	@Override
	public String toString() {
		if (advantage) {
			return "AD";
		}
		return String.valueOf(value);
	}
}
