package com.yourcompany.game.tennis;

public class TimeViolation extends TennisViolation {

	public TimeViolation(TennisPlayer violatingContestant) {
		super("Naruszenie limitu czasu (Time Violation)", new PointPenalty(), violatingContestant);
	}
}
