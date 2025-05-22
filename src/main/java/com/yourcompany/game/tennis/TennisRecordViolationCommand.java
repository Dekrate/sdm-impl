package com.yourcompany.game.tennis;

import com.yourcompany.game.*;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.tennis.TennisPlayer;
import com.yourcompany.game.tennis.TennisResult;

public class TennisRecordViolationCommand implements MatchCommand<TennisMatch> {

	private Violation<TennisPlayer, TennisResult> violation;

	
	public TennisRecordViolationCommand(Violation<TennisPlayer, TennisResult> violation) {
		this.violation = violation;
	}

	
	@Override
	public void execute(TennisMatch match) {
		if (match != null && violation != null) {
			match.recordViolation(violation);
		} else {
			System.err.println("Error executing RecordViolationCommand: Match or violation is null.");
		}
	}
}
