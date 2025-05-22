package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchCommand;

public class TennisStopMatchCommand implements MatchCommand<TennisMatch> { // Implement MatchCommand for TennisMatch

	
	@Override
	public void execute(TennisMatch match) { // Signature updated to TennisMatch
		if (match != null) {
			match.endMatch(); // Delegate the action to the Receiver
		} else {
			System.err.println("Error executing EndMatchCommand: Match is null.");
		}
	}
}
