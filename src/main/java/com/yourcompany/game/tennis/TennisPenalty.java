package com.yourcompany.game.tennis;

import com.yourcompany.game.Penalty;

public abstract class TennisPenalty implements Penalty<TennisPlayer, TennisResult> {

	protected String description;

	protected TennisPenalty(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Penalty: " + description;
	}
}
