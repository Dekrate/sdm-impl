package com.yourcompany.game.tennis;

public class CodeViolation extends TennisViolation {

	public CodeViolation(TennisPlayer violatingContestant) {
		super("Naruszenie kodeksu postępowania (Code Violation)", new GamePenalty(), violatingContestant);
	}

}
