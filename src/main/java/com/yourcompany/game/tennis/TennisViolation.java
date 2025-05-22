package com.yourcompany.game.tennis;

import com.yourcompany.game.Violation;
import com.yourcompany.game.Penalty;

public abstract class TennisViolation implements Violation<TennisPlayer, TennisResult> {

	protected String description;
	protected Penalty<TennisPlayer, TennisResult> penalty;
	protected TennisPlayer violatingContestant;


	public TennisViolation(String description, Penalty<TennisPlayer, TennisResult> penalty, TennisPlayer violatingContestant) {
		this.description = description;
		this.penalty = penalty;
		this.violatingContestant = violatingContestant;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Penalty<TennisPlayer, TennisResult> getPenalty() {
		return penalty;
	}

	@Override
	public TennisPlayer getViolatingContestant() {
		return violatingContestant;
	}
}
