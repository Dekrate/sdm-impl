package com.yourcompany.game.tennis;

import com.yourcompany.game.*;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.tennis.TennisPlayer; // Import TennisPlayer for Violation type
import com.yourcompany.game.tennis.TennisResult; // Import TennisResult for Violation type

public class TennisRecordViolationCommand implements MatchCommand<TennisMatch> { // Implement MatchCommand for TennisMatch

	private Violation<TennisPlayer, TennisResult> violation; // The violation that occurred

	
	public TennisRecordViolationCommand(Violation<TennisPlayer, TennisResult> violation) {
		this.violation = violation;
	}

	
	@Override
	public void execute(TennisMatch match) { // Signature updated to TennisMatch
		if (match != null && violation != null) {
			match.recordViolation(violation); // Delegate the action to the Receiver
		} else {
			System.err.println("Error executing RecordViolationCommand: Match or violation is null.");
		}
	}
}
