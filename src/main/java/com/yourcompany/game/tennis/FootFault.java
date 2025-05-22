package com.yourcompany.game.tennis;

public class FootFault extends TennisViolation {

	public FootFault(TennisPlayer violatingContestant) {
		super("Naruszenie stopy (Foot Fault)", new PointPenalty(), violatingContestant);
	}

}
